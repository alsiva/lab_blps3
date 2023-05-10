package vasilkov.labbpls2.api;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GrantRequest {

    @NotNull
    Long id;

    @NotNull
    Boolean finalStatus;

    String message;

}