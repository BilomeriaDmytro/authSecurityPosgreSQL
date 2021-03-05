package pet.authSecurityPosgreSQL.service.customValidation.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pet.authSecurityPosgreSQL.dto.UserDTO;

@Slf4j
public class PasswordMatchesValidator 
implements ConstraintValidator<PasswordMatches, UserDTO> {
  
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {       
  }


  @SneakyThrows
  @Override
  public boolean isValid(UserDTO userDto, ConstraintValidatorContext context){

      boolean matches = userDto.getPassword().equals(userDto.getMatchingPassword());
      if (matches){
          return true;
      }
      log.info("Passwords for user with username - '{}' don't match", userDto.getUsername());
      return false;
  }     
}
