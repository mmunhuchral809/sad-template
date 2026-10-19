package num.ic.sad.sadtemplate.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderItemCompositeTest {

    private final SingleItem mouse = new SingleItem("S1", "Mouse", new BigDecimal("20.00"), 1);
    private final SingleItem pad = new SingleItem("S2", "Pad", new BigDecimal("10.00"), 1);
    private final SingleItem desk = new SingleItem("S3", "Desk", new BigDecimal("100.00"), 1);

    // BigDecimal-ийн scale-ээс хамаарахгүйгээр утгыг харьцуулна
    private void assertPrice(String expected, OrderItemComponent item) {
        assertEquals(0, new BigDecimal(expected).compareTo(item.price()),
                "Хүлээсэн " + expected + ", гарсан " + item.price());
    }

    @Test
    void leafPriceIsUnitPriceTimesQuantity() {
        assertPrice("20.00", mouse);
    }

    @Test
    void bundleAppliesDiscountToChildrenTotal() {
        BundleItem pack = new BundleItem("Pack", new BigDecimal("0.10")).add(mouse).add(pad);
        assertPrice("27.00", pack); // 30 - 10%
    }

    @Test
    void bundleCanContainAnotherBundle() {
        BundleItem pack = new BundleItem("Pack", new BigDecimal("0.10")).add(mouse).add(pad);
        BundleItem rig = new BundleItem("Rig", new BigDecimal("0.00")).add(pack).add(desk);
        assertPrice("127.00", rig);
    }

    @Test
    void mixedCartOfBundlesAndSingleItems() {
        BundleItem pack = new BundleItem("Pack", new BigDecimal("0.10")).add(mouse).add(pad);
        BundleItem rig = new BundleItem("Rig", new BigDecimal("0.00")).add(pack).add(desk);
        BundleItem cart = new BundleItem("Cart", BigDecimal.ZERO).add(rig).add(mouse);

        assertPrice("147.00", cart);
        assertTrue(cart.render(0).contains("Mouse"), "render нь доторх барааг оруулсан байх ёстой");
    }
}
