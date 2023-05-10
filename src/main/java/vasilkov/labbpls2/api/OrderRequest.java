package vasilkov.labbpls2.api;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {

    @NotBlank(message = "description не должен быть пустым!")
    private final String description;

    @NotNull(message = "color не должен быть пустым!")
    private final String color;

    @NotNull(message = "material не должен быть пустым!")
    private final String material;

    @NotNull(message = "number_of_pieces_in_a_package не должен быть пустым!")
    private final Integer number_of_pieces_in_a_package;

    @NotNull(message = "country_of_origin не должен быть пустым!")
    private final String country_of_origin;

    @NotBlank(message = "Brand не должен быть пустым!")
    private final String brandName;

    @NotBlank(message = "Model не должен быть пустым!")
    private final String modelName;

    private final Integer guarantee_period;

}
