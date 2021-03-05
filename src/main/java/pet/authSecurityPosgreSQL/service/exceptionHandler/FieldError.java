package pet.authSecurityPosgreSQL.service.exceptionHandler;

import lombok.Data;

@Data
public class FieldError {

    private String field;

    private String message;

    public FieldError(org.springframework.validation.FieldError error){
        field = error.getField();
        message = error.getDefaultMessage();
    }
}
