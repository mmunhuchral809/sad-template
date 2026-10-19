package num.ic.sad.sadtemplate.customer;

import java.util.List;
import java.util.Optional;

// Манай системийн хүлээж авдаг интерфэйс
public interface CustomerStore {
    Optional<Customer> findById(String id);

    List<Customer> findAll();
}
