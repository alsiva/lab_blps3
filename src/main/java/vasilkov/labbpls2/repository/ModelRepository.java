
package vasilkov.labbpls2.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov.labbpls2.entity.Model;

import java.util.Optional;


@Repository
@Hidden
public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findModelByName(String name);
}