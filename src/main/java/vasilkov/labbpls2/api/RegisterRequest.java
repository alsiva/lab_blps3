package vasilkov.labbpls2.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "firstname may not be blank")
    private String firstname;
    @NotBlank(message = "lastname may not be blank")
    private String lastname;
    @NotBlank(message = "email may not be blank")
    private String email;
    @NotBlank(message = "password may not be blank")
    private String password;

}
