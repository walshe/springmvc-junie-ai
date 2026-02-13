package walshe.juniemvc.juniemvc.mappers;

import org.junit.jupiter.api.Test;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    CustomerMapper customerMapper = new CustomerMapperImpl();

    @Test
    void testCustomerToCustomerDto() {
        Customer customer = Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build();

        CustomerDto dto = customerMapper.customerToCustomerDto(customer);

        assertThat(dto.getName()).isEqualTo(customer.getName());
        assertThat(dto.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void testCustomerDtoToCustomer() {
        CustomerDto dto = CustomerDto.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .addressLine1("456 Elm St")
                .city("Town")
                .state("State")
                .postalCode("54321")
                .build();

        Customer customer = customerMapper.customerDtoToCustomer(dto);

        assertThat(customer.getName()).isEqualTo(dto.getName());
        assertThat(customer.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    void testCreateCustomerCommandToCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "New Customer",
                "new@example.com",
                "123456789",
                "789 Oak St",
                null,
                "New City",
                "NY",
                "10001"
        );

        Customer customer = customerMapper.createCustomerCommandToCustomer(command);

        assertThat(customer.getName()).isEqualTo(command.name());
        assertThat(customer.getAddressLine1()).isEqualTo(command.addressLine1());
    }

    @Test
    void testUpdateCustomerFromCommand() {
        Customer customer = Customer.builder()
                .name("Old Name")
                .addressLine1("Old Address")
                .build();

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                "New Name",
                "new@example.com",
                "123456789",
                "New Address",
                null,
                "New City",
                "New State",
                "00000"
        );

        customerMapper.updateCustomerFromCommand(command, customer);

        assertThat(customer.getName()).isEqualTo("New Name");
        assertThat(customer.getAddressLine1()).isEqualTo("New Address");
    }
}
