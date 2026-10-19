package num.ic.sad.sadtemplate.catalogue;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

// Үнэн бодит (удаан) каталог гэж үзнэ. Хэдэн удаа асуусныг тоолно.
public class DatabaseCatalogue implements Catalogue {
    private final Map<String, String> data = Map.of(
            "SKU-1", "Keyboard",
            "SKU-A", "Mouse");
    private final AtomicInteger queryCount = new AtomicInteger(0);

    @Override
    public Optional<String> findDescription(String sku) {
        queryCount.incrementAndGet();
        if (sku == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.get(sku));
    }

    public int getQueryCount() {
        return queryCount.get();
    }
}
