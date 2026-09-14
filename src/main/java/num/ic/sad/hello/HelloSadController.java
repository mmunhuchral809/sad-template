package num.ic.sad.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSadController {
    @GetMapping("/hello-sad")
    public String hello() {
        return "Hello, SAD!";
    }
}
