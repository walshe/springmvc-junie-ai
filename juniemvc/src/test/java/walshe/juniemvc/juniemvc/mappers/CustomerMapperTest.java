package walshe.juniemvc.juniemvc.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerMapperTest {

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void customerToCustomerDto() {
        Customer customer = Customer.builder()
                .name("Test Customer")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build();

        CustomerDto dto = customerMapper.customerToCustomerDto(customer);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(customer.getName());
        assertThat(dto.getAddressLine1()).isEqualTo(customer.getAddressLine1());
    }

    @Test
    void customerDtoToCustomer() {
        CustomerDto dto = CustomerDto.builder()
                .name("Test Customer")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build();

        Customer customer = customerMapper.customerDtoToCustomer(dto);

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(dto.getName());
        assertThat(customer.getAddressLine1()).isEqualTo(dto.getAddressLine1());
    }

    @Test
    void createCustomerCommandToCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "Test Customer",
                "test@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        Customer customer = customerMapper.createCustomerCommandToCustomer(command);

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(command.name());
        assertThat(customer.getEmail()).isEqualTo(command.email());
        assertThat(customer.getPhoneNumber()).isEqualTo(command.phoneNumber());
        assertThat(customer.getAddressLine1()).isEqualTo(command.addressLine1());
        assertThat(customer.getAddressLine2()).isEqualTo(command.addressLine2());
        assertThat(customer.getCity()).isEqualTo(command.city());
        assertThat(customer.getState()).isEqualTo(command.state());
        assertThat(customer.getPostalCode()).isEqualTo(command.postalCode());
    }

    @Test
    void updateCustomerFromCommand() {
        Customer customer = Customer.builder()
                .name("Old Name")
                .build();

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                "New Name",
                "test@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        customerMapper.updateCustomerFromCommand(command, customer);

        assertThat(customer.getName()).isEqualTo(command.name());
        assertThat(customer.getEmail()).isEqualTo(command.email());
        assertThat(customer.getAddressLine1()).isEqualTo(command.addressLine1());
    }
}
