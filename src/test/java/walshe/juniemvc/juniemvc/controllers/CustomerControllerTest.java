package walshe.juniemvc.juniemvc.controllers;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import walshe.juniemvc.juniemvc.models.CreateCustomerCommand;
import walshe.juniemvc.juniemvc.models.CustomerDto;
import walshe.juniemvc.juniemvc.models.UpdateCustomerCommand;
import walshe.juniemvc.juniemvc.services.CustomerService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    CustomerService customerService;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        customerService = mock(CustomerService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @Test
    void testListCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDto dto = CustomerDto.builder().id(1).name("Test").build();
        given(customerService.getCustomerById(1)).willReturn(Optional.of(dto));

        mockMvc.perform(get("/api/v1/customers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/customers/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCustomer() throws Exception {
        CreateCustomerCommand command = new CreateCustomerCommand("New", "new@test.com", "123", "Addr", null, "City", "ST", "123");
        CustomerDto saved = CustomerDto.builder().id(1).name("New").build();
        given(customerService.createCustomer(any())).willReturn(saved);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        UpdateCustomerCommand command = new UpdateCustomerCommand("Updated", "upd@test.com", "123", "Addr", null, "City", "ST", "123");
        CustomerDto updated = CustomerDto.builder().id(1).name("Updated").build();
        given(customerService.updateCustomer(eq(1), any())).willReturn(Optional.of(updated));

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        given(customerService.deleteCustomer(1)).willReturn(true);

        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNoContent());
    }
}
