package num.ic.sad.sadtemplate.catalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CachingCatalogueProxyTest {

    @Test
    void secondLookupHitsCacheSoRealCatalogueIsQueriedOnce() {
        DatabaseCatalogue db = new DatabaseCatalogue();
        CachingCatalogueProxy proxy = new CachingCatalogueProxy(db);

        assertEquals("Keyboard", proxy.findDescription("SKU-1").orElseThrow());
        assertEquals("Keyboard", proxy.findDescription("SKU-1").orElseThrow());

        assertEquals(1, db.getQueryCount(), "Жинхэнэ каталог зөвхөн 1 удаа асуугдах ёстой");
    }

    @Test
    void proxiesWrappingTheSameSubjectAreEqual() {
        DatabaseCatalogue db = new DatabaseCatalogue();
        assertEquals(new CachingCatalogueProxy(db), new CachingCatalogueProxy(db));
        assertEquals(new CachingCatalogueProxy(db).hashCode(), new CachingCatalogueProxy(db).hashCode());
    }

    @Test
    void nullSkuReturnsEmptyWithoutQueryingTheDatabase() {
        DatabaseCatalogue db = new DatabaseCatalogue();
        CachingCatalogueProxy proxy = new CachingCatalogueProxy(db);

        assertTrue(proxy.findDescription(null).isEmpty());
        assertEquals(0, db.getQueryCount());
    }
}
