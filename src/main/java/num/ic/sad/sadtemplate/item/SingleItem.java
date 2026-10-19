package num.ic.sad.sadtemplate.item;

import java.math.BigDecimal;

// Leaf: нэг төрлийн бараа
public final class SingleItem implements OrderItemComponent {
    private final String sku;
    private final String name;
    private final BigDecimal unitPrice;
    private final int quantity;

    public SingleItem(String sku, String name, BigDecimal unitPrice, int quantity) {
        this.sku = sku;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    @Override
    public BigDecimal price() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String render(int indentLevel) {
        return " ".repeat(indentLevel) + "- " + name + " (" + sku + ") x" + quantity + " = $" + price();
    }
}
