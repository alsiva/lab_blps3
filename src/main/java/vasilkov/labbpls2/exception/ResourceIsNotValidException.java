package vasilkov.labbpls2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceIsNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceIsNotValidException(String msg) {
        super(msg);
    }

}