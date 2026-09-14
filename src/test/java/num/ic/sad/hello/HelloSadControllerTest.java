package num.ic.sad.hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloSadControllerTest {

    @Test
    void responds() {
        HelloSadController controller = new HelloSadController();
        assertEquals("Hello, SAD!", controller.hello());
    }
}
