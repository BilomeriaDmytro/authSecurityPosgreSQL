package pet.authSecurityPosgreSQL.dto;

import lombok.Data;
import pet.authSecurityPosgreSQL.service.customValidation.email.EmailValid;
import pet.authSecurityPosgreSQL.service.customValidation.password.PasswordMatches;

import javax.validation.constraints.Size;

@Data @PasswordMatches
public class UserDTO {

    @Size(min=5, max=15)
    private String username;

    @Size(min=2, max=15)
    private String firstName;

    @Size(min=2, max=15)
    private String lastName;

    @EmailValid
    private String email;

    @Size(min=4, max=20)
    private String password;

    @Size(min=4, max=20)
    private String matchingPassword;
}
