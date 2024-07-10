package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.CustomerRepository;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.orm.CustomerORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CustomerGatewayImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerGatewayImpl customerGateway;

    @Test
    void saveCustomerTest() {
        final var customer = createCustomer();
        final var customerORM = createCustomerORM();

        Mockito.when(repository.save(any()))
                .thenReturn(customerORM);

        final var result = customerGateway.saveCustomer(customer);

        Assertions.assertNotNull(result);

    }

    @Test
    void getCustomerByCpfTest() {
        final var customerORM = createCustomerORM();

        Mockito.when(repository.findByCpf(anyString()))
                .thenReturn(Optional.ofNullable(customerORM));

        final var result = customerGateway.getCustomerByCpf("cpf");

        Assertions.assertNotNull(result);
    }

    @Test
    void getCustomerByIdTest() {
        final var customerORM = createCustomerORM();

        Mockito.when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(customerORM));

        final var result = customerGateway.getCustomerById(1L);

        Assertions.assertNotNull(result);
    }

    private Customer createCustomer() {
        return (Customer) new Customer()
                .setId(1L)
                .setName("Jane Doe")
                .setBirthday(LocalDate.of(1990, 10, 01))
                .setCpf("123456789123")
                .setEmail("kahog25052@rencr.com")
                .setPassword("SENHA_TESTE_1");
    }

    private CustomerORM createCustomerORM() {
        return new CustomerORM()
                .setId(1L)
                .setName("Jane Doe")
                .setBirthday(LocalDate.of(1990, 10, 01))
                .setCpf("123456789123")
                .setEmail("kahog25052@rencr.com")
                .setPassword("SENHA_TESTE_1");
    }
}
