package walshe.juniemvc.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walshe.juniemvc.juniemvc.entities.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer saved = customerRepository.save(Customer.builder()
                .name("New Customer")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testGetCustomer() {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Test Get")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        Customer fetched = customerRepository.findById(saved.getId()).orElseThrow();

        assertThat(fetched).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Test Get");
    }

    @Test
    void testUpdateCustomer() {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Old Name")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        saved.setName("New Name");
        Customer updated = customerRepository.save(saved);

        assertThat(updated.getName()).isEqualTo("New Name");
    }

    @Test
    void testDeleteCustomer() {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Delete Me")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        customerRepository.deleteById(saved.getId());

        assertThat(customerRepository.findById(saved.getId())).isEmpty();
    }
}
