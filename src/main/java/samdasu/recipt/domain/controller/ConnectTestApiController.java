package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ConnectTestApiController {
    @AllArgsConstructor
    @Data
    public class TestDto {
        String name;
        int age;
        String favorite;
    }

    @Data
    @AllArgsConstructor
    static class TestResult<T> {
        private T data;
    }

    @GetMapping("/test/connect")
    public TestResult testConnect() {
        String name = "son";
        int age = 25;
        String favorite = "IU";
        return new TestResult(new TestDto(name, age, favorite));
    }
}
