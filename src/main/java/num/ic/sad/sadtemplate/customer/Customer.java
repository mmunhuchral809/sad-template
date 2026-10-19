package num.ic.sad.sadtemplate.customer;

import java.util.Optional;

// Манай системийн өөрийн Customer (domain)
public record Customer(String id, String fullName, String email,
                       Optional<String> address, CustomerTier tier) {
}
