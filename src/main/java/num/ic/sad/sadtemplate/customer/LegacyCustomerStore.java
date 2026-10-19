package num.ic.sad.sadtemplate.customer;

import java.util.List;

// Хуучин систем (өөрчилж болохгүй гэж үзнэ)
public class LegacyCustomerStore {
    private final List<ExternalCustomer> records;

    public LegacyCustomerStore(List<ExternalCustomer> records) {
        this.records = records;
    }

    // олдохгүй бол null буцаадаг
    public ExternalCustomer fetchCustomerById(String id) {
        for (ExternalCustomer c : records) {
            if (c.cust_id_num().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public List<ExternalCustomer> fetchAllCustomers() {
        return records;
    }
}
