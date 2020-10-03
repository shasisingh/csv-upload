package com.example.shashi.playcsv;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ResponseEntity<ErrorResponse> error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE) );
        String message = errorAttributes != null ? errorAttributes.getOrDefault("message", "").toString() : "";
        HttpStatus httpStatus = getStatus(request);
        ErrorResponse errorResponse = new ErrorResponse(ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), httpStatus.value(), httpStatus.getReasonPhrase(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    public final static class ErrorResponse {
        private final String timestamp;
        private final int status;
        private final String error;
        private final String message;


        private ErrorResponse(String timestamp, int status, String error, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
