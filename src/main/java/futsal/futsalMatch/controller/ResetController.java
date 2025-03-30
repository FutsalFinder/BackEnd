package futsal.futsalMatch.controller;

import futsal.futsalMatch.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResetController {

    private final LoggingInterceptor loggingInterceptor;

    @GetMapping("/reset")
    public String reset() {
        loggingInterceptor.reset();
        return "reset";
    }
}
