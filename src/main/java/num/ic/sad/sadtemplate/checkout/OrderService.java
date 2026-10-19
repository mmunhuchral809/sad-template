package num.ic.sad.sadtemplate.checkout;

import java.math.BigDecimal;

public interface OrderService {
    // шинэ захиалгын дугаарыг буцаана
    String createOrder(String customerId, String sku, int quantity, BigDecimal totalAmount);
}
