package vasilkov.labbpls2.repository;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vasilkov.labbpls2.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @NonNull Optional<Order> findById(@NonNull Integer integer);

    Optional<List<Order>> findAllByUserEmail(String currentEmail);

    Order findOrderByStatusIsNull();

}
