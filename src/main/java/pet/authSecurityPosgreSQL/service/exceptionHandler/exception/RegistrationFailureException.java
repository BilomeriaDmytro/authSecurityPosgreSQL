package pet.authSecurityPosgreSQL.service.exceptionHandler.exception;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import pet.authSecurityPosgreSQL.service.exceptionHandler.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RegistrationFailureException extends Exception{

    private List<FieldError> fieldErrors;

    private List<String> globalErrors;

    public RegistrationFailureException(String message) {
        super(message);
    }
    public RegistrationFailureException(String message, List<FieldError> fieldErrors, List<ObjectError> globalErrors ) {
        super(message);
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
