package nz.codehq.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessage {

    public static ResponseEntity<Object> responseError(CustomException customException){
        if(String.valueOf(HttpStatus.GATEWAY_TIMEOUT.value()).equals(customException.getStatusCode())) {
            CustomErrorResponse customerInfoModel = new CustomErrorResponse(customException.getStatusCode(), customException.getErrorCode(), customException.getMessage());
            return new ResponseEntity<>(customerInfoModel, HttpStatus.GATEWAY_TIMEOUT);
        } else {
            CustomErrorResponse customerInfoModel = new CustomErrorResponse(customException.getErrorCode(), customException.getMessage());
            return new ResponseEntity<>(customerInfoModel, HttpStatus.BAD_REQUEST);
        }
    }
}
