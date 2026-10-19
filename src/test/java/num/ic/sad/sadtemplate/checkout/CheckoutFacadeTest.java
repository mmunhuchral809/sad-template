package num.ic.sad.sadtemplate.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class CheckoutFacadeTest {

    // тестэд зориулсан энгийн хуурамч (stub) нөөцийн сервис
    private static class StubInventory implements InventoryService {
        private final boolean available;
        boolean reserved = false;
        boolean released = false;

        StubInventory(boolean available) {
            this.available = available;
        }

        @Override
        public boolean isAvailable(String sku, int quantity) {
            return available;
        }

        @Override
        public void reserve(String sku, int quantity) {
            reserved = true;
        }

        @Override
        public void release(String sku, int quantity) {
            released = true;
        }
    }

    private final OrderService orders = (customerId, sku, quantity, total) -> "ORD-1";
    private final CheckoutRequest request =
            new CheckoutRequest("C-1", "SKU-A", 1, new BigDecimal("100.00"), "tok_123");

    @Test
    void shouldProducePopulatedReceiptOnHappyPath() {
        StubInventory inventory = new StubInventory(true);
        CheckoutFacade facade = new CheckoutFacade(inventory, (c, a, t) -> "TX-OK", orders);

        Receipt receipt = facade.checkout(request);

        assertNotNull(receipt.orderId());
        assertEquals("ORD-1", receipt.orderId());
        assertEquals("TX-OK", receipt.transactionReference());
        assertTrue(inventory.reserved);
    }

    @Test
    void shouldThrowAndNeverChargeWhenStockInsufficient() {
        AtomicBoolean paymentCalled = new AtomicBoolean(false);
        CheckoutFacade facade = new CheckoutFacade(new StubInventory(false), (c, a, t) -> {
            paymentCalled.set(true);
            return "TX-NEVER";
        }, orders);

        assertThrows(InsufficientStockException.class, () -> facade.checkout(request));
        assertFalse(paymentCalled.get(), "Нөөц байхгүй бол төлбөр хийгдэж болохгүй");
    }

    @Test
    void shouldReleaseStockWhenPaymentFails() {
        StubInventory inventory = new StubInventory(true);
        CheckoutFacade facade = new CheckoutFacade(inventory, (c, a, t) -> {
            throw new IllegalStateException("card declined");
        }, orders);

        assertThrows(IllegalStateException.class, () -> facade.checkout(request));
        assertTrue(inventory.released, "Төлбөр амжилтгүй бол нөөц буцаагдах ёстой");
    }
}
