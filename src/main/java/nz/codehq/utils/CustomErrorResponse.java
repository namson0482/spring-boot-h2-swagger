package nz.codehq.utils;

import org.springframework.http.HttpStatus;

public class CustomErrorResponse {
    private String statusCode;
    private String errorCode;
    private String message;

    public CustomErrorResponse(String errorCode, String message) {
        super();
        this.statusCode = String.valueOf(HttpStatus.BAD_REQUEST.value());
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomErrorResponse() {
        super();
        this.statusCode = null;
        this.errorCode = null;
        this.message = null;
    }

    public CustomErrorResponse(String statusCode, String errorCode, String message) {
        super();
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
