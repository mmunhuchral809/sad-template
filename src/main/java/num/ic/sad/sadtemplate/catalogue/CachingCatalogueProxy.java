package num.ic.sad.sadtemplate.catalogue;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Proxy: Catalogue-тай ижил интерфэйстэй, давтан асуултыг санах ойноос хариулна
public final class CachingCatalogueProxy implements Catalogue {
    private final Catalogue target;
    private final Map<String, Optional<String>> cache = new ConcurrentHashMap<>();

    public CachingCatalogueProxy(Catalogue target) {
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public Optional<String> findDescription(String sku) {
        if (sku == null) {
            return Optional.empty();
        }
        return cache.computeIfAbsent(sku, target::findDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CachingCatalogueProxy that)) {
            return false;
        }
        return target.equals(that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }
}
