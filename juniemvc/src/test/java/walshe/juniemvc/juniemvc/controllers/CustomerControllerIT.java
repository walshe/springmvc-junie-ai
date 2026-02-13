package walshe.juniemvc.juniemvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testListCustomers() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/customers", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testCreateCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "IT Customer",
                "it@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        ResponseEntity<CustomerDto> response = restTemplate.postForEntity("/api/v1/customers", command, CustomerDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("IT Customer");
    }

    @Test
    void testUpdateCustomer() {
        CreateCustomerCommand createCommand = new CreateCustomerCommand(
                "Before Update",
                "it@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );
        ResponseEntity<CustomerDto> createResponse = restTemplate.postForEntity("/api/v1/customers", createCommand, CustomerDto.class);
        Integer id = createResponse.getBody().getId();

        UpdateCustomerCommand updateCommand = new UpdateCustomerCommand(
                "After Update",
                "it@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        HttpEntity<UpdateCustomerCommand> requestEntity = new HttpEntity<>(updateCommand);
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/customers/" + id, HttpMethod.PUT, requestEntity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity("/api/v1/customers/" + id, CustomerDto.class);
        assertThat(getResponse.getBody().getName()).isEqualTo("After Update");
    }

    @Test
    void testDeleteCustomer() {
        CreateCustomerCommand createCommand = new CreateCustomerCommand(
                "Delete Me",
                "it@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );
        ResponseEntity<CustomerDto> createResponse = restTemplate.postForEntity("/api/v1/customers", createCommand, CustomerDto.class);
        Integer id = createResponse.getBody().getId();

        restTemplate.delete("/api/v1/customers/" + id);

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity("/api/v1/customers/" + id, CustomerDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
