package samdasu.recipt.api.gpt.hadler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import samdasu.recipt.api.gpt.controller.dto.ResponseModel;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseModel<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("exception", e);
        return new ResponseModel<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", e.getMessage());
    }

}