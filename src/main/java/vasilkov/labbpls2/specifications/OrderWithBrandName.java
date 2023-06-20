package vasilkov.labbpls2.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import vasilkov.labbpls2.entity.Order;

public class OrderWithBrandName implements Specification<Order> {

    private final String brand;

    public OrderWithBrandName(String brand) {
        this.brand = brand;
    }

    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (brand == null) {
            return cb.isTrue(cb.literal(true)); // always true = no filtering
        }

        return cb.equal(root.get("brand").get("name"), this.brand);
    }

}