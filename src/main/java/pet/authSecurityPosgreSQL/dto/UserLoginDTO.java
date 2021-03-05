package pet.authSecurityPosgreSQL.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;

    private String password;
}
