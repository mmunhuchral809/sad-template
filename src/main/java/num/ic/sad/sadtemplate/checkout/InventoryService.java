package num.ic.sad.sadtemplate.checkout;

public interface InventoryService {
    boolean isAvailable(String sku, int quantity);

    void reserve(String sku, int quantity);

    void release(String sku, int quantity);
}
