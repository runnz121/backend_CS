package malangcute.bellytime.bellytimeCustomer.global.exception;

import java.io.IOException;
import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.exception.dto.ReturnNullResult;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NoTokenException;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotValidTokenException;

import org.quartz.ExecuteInJTATransaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;


@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionController {

    private final SecurityProperties securityProperties;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity handleException(Exception e) {
        log.error("Got Null Point Exception : ", e);
        return null;
    }

    @ExceptionHandler({NotValidTokenException.class, NoCookieException.class, NoTokenException.class})
    protected ResponseEntity handleRefreshTokenException( Exception e) throws
        IOException {
        log.error("Not Valid RefreshToken Exception, Required Login : ", e);
        String url = UriComponentsBuilder.fromUriString(securityProperties.getCors().getFrontEndDomain()
            + "/memberPage").build().toUriString();
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
            .location(URI.create(url)).build();
    }
}
