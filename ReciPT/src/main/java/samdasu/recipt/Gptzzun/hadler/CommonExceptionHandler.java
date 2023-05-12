package samdasu.recipt.Gptzzun.hadler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import samdasu.recipt.Gptzzun.controller.dto.ResponseModel;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseModel<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("exception", e);
        return new ResponseModel<>(500, "error", e.getMessage());
    }

}