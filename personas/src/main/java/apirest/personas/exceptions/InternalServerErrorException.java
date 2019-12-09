package apirest.personas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 5422874789058269535L;

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
