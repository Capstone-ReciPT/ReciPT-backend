package samdasu.recipt.api.gpt.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseModel<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseModel<T> success(T data) {
        return new ResponseModel<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ResponseModel<T> fail(T data) {
        return new ResponseModel<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", data);
    }

}