package num.ic.sad.sadtemplate.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LoggingProductRepositoryDecoratorTest {

    @Test
    void shouldPreserveTargetBehaviorAndTrackInvocations() {
        ProductRepository repo = new InMemoryProductRepository();
        LoggingProductRepositoryDecorator decorated = new LoggingProductRepositoryDecorator(repo);

        decorated.save(new Product("SKU-1", "Keyboard", new BigDecimal("99.00")));
        Optional<Product> found = decorated.findBySku("SKU-1");

        assertTrue(found.isPresent());
        assertEquals("Keyboard", found.get().name());
        assertEquals(2, decorated.getInvocationCount(), "save() + findBySku() = 2 дуудалт");
    }

    @Test
    void shouldReturnEmptyForUnknownSkuAndStillCountTheCall() {
        LoggingProductRepositoryDecorator decorated =
                new LoggingProductRepositoryDecorator(new InMemoryProductRepository());

        assertTrue(decorated.findBySku("NOPE").isEmpty());
        assertEquals(1, decorated.getInvocationCount());
    }
}
