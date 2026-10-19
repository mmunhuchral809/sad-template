package num.ic.sad.sadtemplate.checkout;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String sku, int quantity) {
        super("Insufficient stock for " + sku + " (requested " + quantity + ")");
    }
}
