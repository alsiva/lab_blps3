package vasilkov.labbpls2.service;


import nu.xom.ParsingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasilkov.labbpls2.api.GrantRequest;
import vasilkov.labbpls2.api.MessageResponse;
import vasilkov.labbpls2.api.OrderRequest;
import vasilkov.labbpls2.entity.Model;
import vasilkov.labbpls2.entity.Order;
import vasilkov.labbpls2.entity.Product;
import vasilkov.labbpls2.entity.User;
import vasilkov.labbpls2.exception.ResourceIsNotValidException;
import vasilkov.labbpls2.exception.ResourceNotFoundException;
import vasilkov.labbpls2.repository.BrandRepository;
import vasilkov.labbpls2.repository.ModelRepository;
import vasilkov.labbpls2.repository.OrderRepository;
import vasilkov.labbpls2.repository.ProductRepository;
import vasilkov.labbpls2.specifications.OrderWithBrandName;
import vasilkov.labbpls2.specifications.OrderWithCityName;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class OrderService {
    final
    EmailService emailService;

    final
    UserService userService;

    final
    BrandRepository brandRepository;

    final
    ModelRepository modelRepository;

    final
    OrderRepository orderRepository;

    final
    ProductRepository productRepository;

    final
    JavaMailSenderImpl javaMailSender;

    public OrderService(EmailService emailService, UserService userService, BrandRepository brandRepository, ModelRepository modelRepository, OrderRepository orderRepository, ProductRepository productRepository, JavaMailSenderImpl javaMailSender) {
        this.emailService = emailService;
        this.userService = userService;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.javaMailSender = javaMailSender;

    }

    @Transactional
    public MessageResponse save(OrderRequest orderRequestModel) throws ParsingException, IOException {
        Order order = new Order();
        order.setDescription(orderRequestModel.getDescription());
        order.setColor(orderRequestModel.getColor());
        order.setMaterial(orderRequestModel.getMaterial());
        order.setCountry_of_origin(orderRequestModel.getCountry_of_origin());
        order.setNumber_of_pieces_in_a_package(orderRequestModel.getNumber_of_pieces_in_a_package());
        order.setGuarantee_period(orderRequestModel.getGuarantee_period());
        order.setBrand(brandRepository.findBrandByName(orderRequestModel.getBrandName())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Brand Not Found")));

        Model model = modelRepository.findModelByName(orderRequestModel.getModelName())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Model Not Found"));

        if (orderRequestModel.getBrandName().equals(model.getBrand().getName())) {
            order.setModel(model);
        } else {
            throw new ResourceNotFoundException("Error: This brand doesn't have this model");
        }

        User user = userService.getByEmail(String.valueOf((SecurityContextHolder.getContext().getAuthentication().getPrincipal())))
                .orElseThrow(() -> new ResourceNotFoundException("Error: User Not Found"));

        order.setUserEmail(user.getEmail());
        orderRepository.save(order);

        List<Order> orders = orderRepository.findAllByUserEmail(user.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Error: Order Not Found wtf?!"));
        int i = orders.indexOf(order);

        Optional<List<String>> adminEmail = XMLService.getAllAdminsEmail();

        for (String email : adminEmail.get()) {
            emailService.sendSimpleMessage(email,
                    "КОМУС - Новое объявление",
                    "Здравствуйте!\n" +
                            "Вам пришло новое объявление на модерацию!\n" +
                            "Id этого объявления: " + orders.get(i).getId() + "\n" +
                            "Спасибо!", javaMailSender);
        }
        return (new MessageResponse("order registered successfully!"));

    }

    @Transactional
    public MessageResponse findAndSave(Integer id) throws ParsingException, IOException {
        Product product = productRepository.findByArticle(id).orElseThrow(() -> new ResourceNotFoundException("Error: Product Not Found"));
        save(new OrderRequest(product.getDescription(), product.getColor(), product.getMaterial(),
                product.getNumber_of_pieces_in_a_package(), product.getCountry_of_origin(),
                product.getBrand().getName(), product.getModel().getName(), product.getGuarantee_period())
        );
        return (new MessageResponse("order registered successfully!"));
    }

    public List<Order> findAllOrdersBySpecification(Map<String, String> values) {
        if (values.get("page") == null || values.get("size") == null ||
                Integer.parseInt(values.get("page")) < 0 || Integer.parseInt(values.get("size")) <= 0)
            throw new ResourceIsNotValidException("Error: page and size are necessarily and must be positive");
        return orderRepository.findAll(Specification
                        .where(new OrderWithBrandName(values.get("brand")))
                        .and(new OrderWithCityName(values.get("city"))),
                PageRequest.of(Integer.parseInt(values.get("page")),
                        Integer.parseInt(values.get("size")))).toList();
    }

    public ResponseEntity<?> findOrdersList() {
        Optional<List<Order>> ordersList = orderRepository.findAllByUserEmail(
                String.valueOf((SecurityContextHolder.getContext().getAuthentication().getPrincipal())));

        if (ordersList.get().isEmpty()) return ResponseEntity.ok(new MessageResponse("You haven't any orders yet"));

        return ResponseEntity.ok(ordersList);
    }

    @Transactional
    public void grantOrderWithEmail(GrantRequest grantRequest) throws ParsingException, IOException {
        System.out.println(grantRequest.getId());
        Order order = orderRepository.findById(Math.toIntExact(grantRequest.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Error: Order Not Found"));
        order.setStatus(grantRequest.getFinalStatus());

        orderRepository.save(order);

        String emailMessage = order.getStatus() ? "прошла успешно!" : "не одобрена";

        String adminMessage = grantRequest.getMessage() == null || grantRequest.getMessage().trim().equals("")
                ? "" : "Комментарий модератора: " + grantRequest.getMessage();

        emailService.sendSimpleMessage(order.getUserEmail(),
                "КОМУС - Изменился статус объявления",
                "Здравствуйте, " + userService.getByEmail(order.getUserEmail()).get().getFirstName() + "!\n" +
                        "Ваша заявка прошла модерацию!\n" +
                        "Заявка на " + order.getDescription() + " " + emailMessage + "\n" +
                        adminMessage + "\n" +
                        "Спасибо!", javaMailSender);

    }

}