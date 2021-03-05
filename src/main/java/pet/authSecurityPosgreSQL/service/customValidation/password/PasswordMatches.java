package pet.authSecurityPosgreSQL.service.customValidation.password;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(value= RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Passwords don't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
