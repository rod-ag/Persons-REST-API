package apirest.personas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Malformed HTTP request")
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 3880060927255073950L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
