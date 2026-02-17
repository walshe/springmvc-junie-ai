package walshe.juniemvc.juniemvc.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T19:14:23+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto.CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.id( customer.getId() );
        customerDto.version( customer.getVersion() );
        customerDto.name( customer.getName() );
        customerDto.email( customer.getEmail() );
        customerDto.phoneNumber( customer.getPhoneNumber() );
        customerDto.addressLine1( customer.getAddressLine1() );
        customerDto.addressLine2( customer.getAddressLine2() );
        customerDto.city( customer.getCity() );
        customerDto.state( customer.getState() );
        customerDto.postalCode( customer.getPostalCode() );
        customerDto.createdDate( customer.getCreatedDate() );
        customerDto.updateDate( customer.getUpdateDate() );

        return customerDto.build();
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.name( dto.getName() );
        customer.email( dto.getEmail() );
        customer.phoneNumber( dto.getPhoneNumber() );
        customer.addressLine1( dto.getAddressLine1() );
        customer.addressLine2( dto.getAddressLine2() );
        customer.city( dto.getCity() );
        customer.state( dto.getState() );
        customer.postalCode( dto.getPostalCode() );

        return customer.build();
    }

    @Override
    public Customer createCustomerCommandToCustomer(CreateCustomerCommand command) {
        if ( command == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.name( command.name() );
        customer.email( command.email() );
        customer.phoneNumber( command.phoneNumber() );
        customer.addressLine1( command.addressLine1() );
        customer.addressLine2( command.addressLine2() );
        customer.city( command.city() );
        customer.state( command.state() );
        customer.postalCode( command.postalCode() );

        return customer.build();
    }

    @Override
    public void updateCustomerFromCommand(UpdateCustomerCommand command, Customer customer) {
        if ( command == null ) {
            return;
        }

        customer.setName( command.name() );
        customer.setEmail( command.email() );
        customer.setPhoneNumber( command.phoneNumber() );
        customer.setAddressLine1( command.addressLine1() );
        customer.setAddressLine2( command.addressLine2() );
        customer.setCity( command.city() );
        customer.setState( command.state() );
        customer.setPostalCode( command.postalCode() );
    }
}
