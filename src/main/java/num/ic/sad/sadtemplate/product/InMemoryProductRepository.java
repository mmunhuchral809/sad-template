package num.ic.sad.sadtemplate.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(products.get(sku));
    }

    @Override
    public void save(Product product) {
        products.put(product.sku(), product);
    }
}
