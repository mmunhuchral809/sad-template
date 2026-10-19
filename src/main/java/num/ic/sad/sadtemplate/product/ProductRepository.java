package num.ic.sad.sadtemplate.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findBySku(String sku);

    void save(Product product);
}
