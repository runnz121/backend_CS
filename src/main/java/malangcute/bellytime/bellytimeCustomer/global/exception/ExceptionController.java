package malangcute.bellytime.bellytimeCustomer.global.exception;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.exception.dto.ReturnNullResult;
import org.quartz.ExecuteInJTATransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    protected ResponseEntity handleException(Exception e) {
        log.error("Got Null Point Excpetion : ", e);
        return null;
    }
}
