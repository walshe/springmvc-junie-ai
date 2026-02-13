package walshe.juniemvc.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import walshe.juniemvc.juniemvc.entities.Customer;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;
import walshe.juniemvc.juniemvc.repositories.CustomerRepository;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CustomerControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Test Customer")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        mockMvc.perform(get("/api/v1/customers/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(jsonPath("$.name", is(saved.getName())));
    }

    @Test
    void testCreateCustomer() throws Exception {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "New Customer",
                "test@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        mockMvc.perform(post("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/customers/")));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Old Name")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                "Updated Name",
                "test@example.com",
                "1234567890",
                "123 Main St",
                "Apt 1",
                "City",
                "State",
                "12345"
        );

        mockMvc.perform(put("/api/v1/customers/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer saved = customerRepository.save(Customer.builder()
                .name("Delete Me")
                .addressLine1("123 Main St")
                .city("City")
                .state("State")
                .postalCode("12345")
                .build());

        mockMvc.perform(delete("/api/v1/customers/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
