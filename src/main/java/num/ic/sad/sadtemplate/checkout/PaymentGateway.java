package num.ic.sad.sadtemplate.checkout;

import java.math.BigDecimal;

public interface PaymentGateway {
    // амжилттай бол гүйлгээний дугаарыг буцаана
    String charge(String customerId, BigDecimal amount, String paymentToken);
}
