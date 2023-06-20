package vasilkov.labbpls2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;


@ResponseStatus(HttpStatus.CONFLICT)
public class MyPSQLException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MyPSQLException(String msg) {
        super(msg);
    }

}