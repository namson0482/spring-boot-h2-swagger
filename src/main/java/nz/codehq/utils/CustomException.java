package nz.codehq.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException  {
    private String statusCode;
    private String errorCode;
    private String message;
    public CustomException(String errorCode, String message) {
        super();
        this.statusCode = String.valueOf(HttpStatus.BAD_REQUEST.value());
        this.errorCode = errorCode;
        this.message = message;
    }
}
