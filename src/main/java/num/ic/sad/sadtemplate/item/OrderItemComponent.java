package num.ic.sad.sadtemplate.item;

import java.math.BigDecimal;

// Нэг бараа болон багц барааг адилхан ашиглах нийтлэг интерфэйс
public interface OrderItemComponent {
    BigDecimal price();

    String render(int indentLevel);
}
