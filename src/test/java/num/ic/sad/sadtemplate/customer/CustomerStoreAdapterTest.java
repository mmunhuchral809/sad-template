package num.ic.sad.sadtemplate.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerStoreAdapterTest {

    @Test
    void shouldReturnEmptyListWhenLegacyStoreIsEmpty() {
        CustomerStore store = new CustomerStoreAdapter(new LegacyCustomerStore(List.of()));
        assertTrue(store.findAll().isEmpty());
    }

    @Test
    void shouldReturnEmptyOptionalWhenRecordNotFound() {
        CustomerStore store = new CustomerStoreAdapter(new LegacyCustomerStore(List.of()));
        assertTrue(store.findById("MISSING").isEmpty());
    }

    @Test
    void shouldMapLegacyFieldsCorrectlyToDomainCustomer() {
        ExternalCustomer ext = new ExternalCustomer("C-1", " Bob ", "BOB@TEST.COM", "Main St", 1);
        CustomerStore store = new CustomerStoreAdapter(new LegacyCustomerStore(List.of(ext)));

        Customer c = store.findById("C-1").orElseThrow();

        assertEquals("Bob", c.fullName());
        assertEquals("bob@test.com", c.email());
        assertEquals(CustomerTier.VIP, c.tier());
    }
}
