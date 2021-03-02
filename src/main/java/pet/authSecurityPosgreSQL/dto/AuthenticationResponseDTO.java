package pet.authSecurityPosgreSQL.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {

    private String username;

    private String token;

    public AuthenticationResponseDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
