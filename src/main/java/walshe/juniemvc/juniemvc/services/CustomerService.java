package walshe.juniemvc.juniemvc.services;

import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> listCustomers();
    Optional<CustomerDto> getCustomerById(Integer id);
    CustomerDto createCustomer(CreateCustomerCommand command);
    Optional<CustomerDto> updateCustomer(Integer id, UpdateCustomerCommand command);
    boolean deleteCustomer(Integer id);
}
