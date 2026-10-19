package num.ic.sad.sadtemplate.product;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

// Decorator: үндсэн репозиторийн үйлдлийг өөрчлөхгүй, зөвхөн дуудалтыг тоолно
public final class LoggingProductRepositoryDecorator implements ProductRepository {
    private final ProductRepository delegate;
    private final AtomicInteger invocationCount = new AtomicInteger(0);

    public LoggingProductRepositoryDecorator(ProductRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        invocationCount.incrementAndGet();
        return delegate.findBySku(sku);
    }

    @Override
    public void save(Product product) {
        invocationCount.incrementAndGet();
        delegate.save(product);
    }

    public int getInvocationCount() {
        return invocationCount.get();
    }
}
