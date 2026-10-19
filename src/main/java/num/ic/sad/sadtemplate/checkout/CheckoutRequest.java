package num.ic.sad.sadtemplate.checkout;

import java.math.BigDecimal;

public record CheckoutRequest(String customerId, String sku, int quantity,
                              BigDecimal totalAmount, String paymentToken) {
}
