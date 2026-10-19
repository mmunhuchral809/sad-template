package num.ic.sad.sadtemplate.customer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Adapter: хуучин системийг манай CustomerStore интерфэйс рүү хөрвүүлнэ
public final class CustomerStoreAdapter implements CustomerStore {
    private final LegacyCustomerStore legacyStore;

    public CustomerStoreAdapter(LegacyCustomerStore legacyStore) {
        this.legacyStore = Objects.requireNonNull(legacyStore);
    }

    @Override
    public Optional<Customer> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        ExternalCustomer raw = legacyStore.fetchCustomerById(id);
        return raw == null ? Optional.empty() : Optional.of(mapToDomain(raw));
    }

    @Override
    public List<Customer> findAll() {
        List<ExternalCustomer> raw = legacyStore.fetchAllCustomers();
        return raw == null ? List.of() : raw.stream().map(this::mapToDomain).toList();
    }

    private Customer mapToDomain(ExternalCustomer ext) {
        Optional<String> address = (ext.addr_blob() == null || ext.addr_blob().isBlank())
                ? Optional.empty()
                : Optional.of(ext.addr_blob().trim());
        CustomerTier tier = ext.vip_flag() == 1 ? CustomerTier.VIP : CustomerTier.STANDARD;
        return new Customer(
                ext.cust_id_num(),
                ext.raw_full_name().trim(),
                ext.contact_email().trim().toLowerCase(),
                address,
                tier);
    }
}
