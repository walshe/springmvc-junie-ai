package walshe.juniemvc.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import walshe.juniemvc.juniemvc.entities.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = Customer.builder()
                .name("Test Customer")
                .addressLine1("123 Test St")
                .city("Test City")
                .state("TS")
                .postalCode("12345")
                .build();

        Customer saved = customerRepository.save(customer);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Customer");
    }

    @Test
    void testFindById() {
        Customer customer = Customer.builder()
                .name("Find Me")
                .addressLine1("123 Test St")
                .city("Test City")
                .state("TS")
                .postalCode("12345")
                .build();
        Customer saved = customerRepository.save(customer);

        Customer found = customerRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Find Me");
    }
}
