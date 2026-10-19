package num.ic.sad.sadtemplate.catalogue;

import java.util.Optional;

public interface Catalogue {
    Optional<String> findDescription(String sku);
}
