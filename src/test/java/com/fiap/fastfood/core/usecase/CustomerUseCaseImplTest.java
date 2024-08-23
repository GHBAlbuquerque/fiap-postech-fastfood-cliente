package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.CustomerDeactivationException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseImplTest {

    @Mock
    private CustomerGateway customerGatewayMock;

    @Mock
    private AuthenticationGateway authenticationcustomerGatewayMock;

    @InjectMocks
    private CustomerUseCaseImpl useCase;

    @Test
    void registerCustomerTest() {
        final var customerMock = Mockito.mock(Customer.class);

        when(customerGatewayMock.saveCustomer(customerMock))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.registerCustomer(customerMock, customerGatewayMock, authenticationcustomerGatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void registerCustomerErrorTest() {
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);
        final var authenticationcustomerGatewayMock = Mockito.mock(AuthenticationGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        when(customerGatewayMock.getCustomerByCpf(any()))
                .thenReturn(customerMock);

        Assertions.assertThrows(
                AlreadyRegisteredException.class,
                () -> useCase.registerCustomer(customerMock, customerGatewayMock, authenticationcustomerGatewayMock)
        );
    }

    @Test
    void getCustomerByCpfTest() {
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        when(customerGatewayMock.getCustomerByCpf(anyString()))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.getCustomerByCpf("cpf", customerGatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void getCustomerByCpfErrorTest() {
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);

        when(customerGatewayMock.getCustomerByCpf(anyString()))
                .thenReturn(null);

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> useCase.getCustomerByCpf("cpf", customerGatewayMock)
        );
    }

    @Test
    void getCustomerByIdTest() {
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        when(customerGatewayMock.getCustomerById(anyLong()))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.getCustomerById(1L, customerGatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void getCustomerByIdErrorTest() {
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);

        when(customerGatewayMock.getCustomerById(anyLong()))
                .thenReturn(null);

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> useCase.getCustomerById(1L, customerGatewayMock)
        );
    }


    @Test
    void confirmCustomerSignUpTest() throws IdentityProviderRegistrationException {
        final var authenticationcustomerGatewayMock = Mockito.mock(AuthenticationGateway.class);

        when(authenticationcustomerGatewayMock.confirmSignUp(anyString(), anyString()))
                .thenReturn(Boolean.TRUE);

        Assertions.assertDoesNotThrow(
                () -> useCase.confirmCustomerSignUp("cpf", "code", authenticationcustomerGatewayMock)
        );
    }

    @Test
    void testDeactivateCustomer_Success() throws CustomerDeactivationException {
        // Arrange
        final var customerId = 1L;
        final var customer = new Customer();
        customer.setId(customerId);
        customer.setIsActive(Boolean.TRUE);

        when(customerGatewayMock.getCustomerById(customerId)).thenReturn(customer);

        // Act
        final var result = useCase.deactivateCustomer(customerId, customerGatewayMock);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void testDeactivateCustomer_Failure() {
        // Arrange
        final var customerId = 1L;

        when(customerGatewayMock.getCustomerById(customerId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        final var exception = assertThrows(CustomerDeactivationException.class, () ->
                useCase.deactivateCustomer(customerId, customerGatewayMock)
        );

        Assertions.assertEquals("Error when trying to deactivate customer. Please contact the admin.", exception.getMessage());
    }
}
