package walshe.juniemvc.juniemvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;
import walshe.juniemvc.juniemvc.services.CustomerService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Validated
class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    List<CustomerDto> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping("/{customerId}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerCommand command) {
        CustomerDto created = customerService.createCustomer(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{customerId}")
    ResponseEntity<Void> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody UpdateCustomerCommand command) {
        return customerService.updateCustomer(customerId, command)
                .map(dto -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{customerId}")
    ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        if (customerService.deleteCustomer(customerId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
