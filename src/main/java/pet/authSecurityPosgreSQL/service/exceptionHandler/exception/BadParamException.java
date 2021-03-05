package pet.authSecurityPosgreSQL.service.exceptionHandler.exception;

import java.util.List;

public class BadParamException extends Exception{

    public BadParamException(List<String> params) {

        super("Wrong parameters values: " + params.stream().map(param -> param + " "));
    }

    public BadParamException(String param) {

        super("Wrong parameter value: " + param);
    }
}
