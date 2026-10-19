package num.ic.sad.sadtemplate.checkout;

import java.time.Instant;
import java.util.Objects;

// Facade: контроллер зөвхөн энэ классыг дуудна.
// Нөөц, төлбөр, захиалгын дарааллыг энд зохицуулна. Бизнесийн дүрэм энд байхгүй.
public final class CheckoutFacade {
    private final InventoryService inventory;
    private final PaymentGateway payment;
    private final OrderService orders;

    public CheckoutFacade(InventoryService inventory, PaymentGateway payment, OrderService orders) {
        this.inventory = Objects.requireNonNull(inventory);
        this.payment = Objects.requireNonNull(payment);
        this.orders = Objects.requireNonNull(orders);
    }

    public Receipt checkout(CheckoutRequest req) {
        if (!inventory.isAvailable(req.sku(), req.quantity())) {
            throw new InsufficientStockException(req.sku(), req.quantity());
        }
        inventory.reserve(req.sku(), req.quantity());

        String transactionRef;
        try {
            transactionRef = payment.charge(req.customerId(), req.totalAmount(), req.paymentToken());
        } catch (RuntimeException ex) {
            inventory.release(req.sku(), req.quantity()); // төлбөр амжилтгүй бол нөөцийг буцаана
            throw ex;
        }

        String orderId = orders.createOrder(req.customerId(), req.sku(), req.quantity(), req.totalAmount());
        return new Receipt(orderId, req.customerId(), req.totalAmount(), transactionRef, Instant.now());
    }
}
