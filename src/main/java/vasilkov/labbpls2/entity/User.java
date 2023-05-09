package vasilkov.labbpls2.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}