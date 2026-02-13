package walshe.juniemvc.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(target = "beerOrders", ignore = true)
    Customer customerDtoToCustomer(CustomerDto dto);

    @Mapping(target = "beerOrders", ignore = true)
    Customer createCustomerCommandToCustomer(CreateCustomerCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrders", ignore = true)
    void updateCustomerFromCommand(UpdateCustomerCommand command, @org.mapstruct.MappingTarget Customer customer);
}
