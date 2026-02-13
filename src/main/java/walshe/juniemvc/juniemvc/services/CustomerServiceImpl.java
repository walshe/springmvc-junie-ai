package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.mappers.CustomerMapper;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;
import walshe.juniemvc.juniemvc.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDto> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CreateCustomerCommand command) {
        Customer customer = customerMapper.createCustomerCommandToCustomer(command);
        Customer saved = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(saved);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> updateCustomer(Integer id, UpdateCustomerCommand command) {
        return customerRepository.findById(id)
                .map(found -> {
                    customerMapper.updateCustomerFromCommand(command, found);
                    return customerMapper.customerToCustomerDto(customerRepository.save(found));
                });
    }

    @Override
    @Transactional
    public boolean deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
