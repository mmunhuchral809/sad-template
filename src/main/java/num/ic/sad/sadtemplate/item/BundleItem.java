package num.ic.sad.sadtemplate.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// Composite: доторх бараа эсвэл өөр багцыг агуулна, хямдралтай
public final class BundleItem implements OrderItemComponent {
    private final String bundleName;
    private final BigDecimal discountRate; // жишээ нь 0.10 = 10%
    private final List<OrderItemComponent> children = new ArrayList<>();

    public BundleItem(String bundleName, BigDecimal discountRate) {
        this.bundleName = bundleName;
        this.discountRate = discountRate;
    }

    public BundleItem add(OrderItemComponent child) {
        children.add(child);
        return this;
    }

    @Override
    public BigDecimal price() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItemComponent child : children) {
            sum = sum.add(child.price());
        }
        return sum.subtract(sum.multiply(discountRate)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String render(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(indentLevel)).append("+ ").append(bundleName)
                .append(" = $").append(price());
        for (OrderItemComponent child : children) {
            sb.append("\n").append(child.render(indentLevel + 2));
        }
        return sb.toString();
    }
}
