package pet.authSecurityPosgreSQL.service.exceptionHandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.BadParamException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.RegistrationFailureException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(),ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RegistrationFailureException.class)
    protected ResponseEntity<Object> handleInvalidEmail(RegistrationFailureException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getFieldErrors(), ex.getGlobalErrors(), ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(),ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BadParamException.class)
    protected ResponseEntity<Object> handleBadParam(BadParamException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(),ex);
        return buildResponseEntity(apiError);
    }

}