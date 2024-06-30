package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CustomerUseCaseImplTest {

    @InjectMocks
    CustomerUseCaseImpl useCase;

    @Test
    void registerCustomerTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);
        final var authenticationGatewayMock = Mockito.mock(AuthenticationGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        Mockito.when(gatewayMock.saveCustomer(customerMock))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.registerCustomer(customerMock, gatewayMock, authenticationGatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void registerCustomerErrorTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);
        final var authenticationGatewayMock = Mockito.mock(AuthenticationGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        Mockito.when(gatewayMock.getCustomerByCpf(any()))
                .thenReturn(customerMock);

        Assertions.assertThrows(
                AlreadyRegisteredException.class,
                () -> useCase.registerCustomer(customerMock, gatewayMock, authenticationGatewayMock)
        );
    }

    @Test
    void getCustomerByCpfTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        Mockito.when(gatewayMock.getCustomerByCpf(anyString()))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.getCustomerByCpf("cpf", gatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void getCustomerByCpfErrorTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);

        Mockito.when(gatewayMock.getCustomerByCpf(anyString()))
                .thenReturn(null);

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> useCase.getCustomerByCpf("cpf", gatewayMock)
        );
    }

    @Test
    void getCustomerByIdTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);
        final var customerMock = Mockito.mock(Customer.class);

        Mockito.when(gatewayMock.getCustomerById(anyLong()))
                .thenReturn(customerMock);

        final var result = Assertions.assertDoesNotThrow(
                () -> useCase.getCustomerById(1L, gatewayMock)
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void getCustomerByIdErrorTest() {
        final var gatewayMock = Mockito.mock(CustomerGateway.class);

        Mockito.when(gatewayMock.getCustomerById(anyLong()))
                .thenReturn(null);

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> useCase.getCustomerById(1L, gatewayMock)
        );
    }


    @Test
    void confirmCustomerSignUpTest() throws IdentityProviderRegistrationException {
        final var authenticationGatewayMock = Mockito.mock(AuthenticationGateway.class);

        Mockito.when(authenticationGatewayMock.confirmSignUp(anyString(), anyString()))
                .thenReturn(Boolean.TRUE);

        Assertions.assertDoesNotThrow(
                () -> useCase.confirmCustomerSignUp("cpf", "code", authenticationGatewayMock)
        );


    }
}
