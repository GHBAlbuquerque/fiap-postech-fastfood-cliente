package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.CustomerRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.NotificationGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@ExtendWith(MockitoExtension.class)
class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void customerGatewayTest() {
        final var mock = Mockito.mock(CustomerRepository.class);

        final var result = declaration.customerGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void authenticationGatewayTest() {
        final var mock = Mockito.mock(CognitoIdentityProviderClient.class);

        final var result = declaration.authenticationGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void orquestrationGatewayTest() {
        final var mock = Mockito.mock(NotificationGateway.class);
        final var mockSender = Mockito.mock(MessageSender.class);

        final var result = declaration.orquestrationGateway(mock, mockSender);

        Assertions.assertNotNull(result);
    }

    @Test
    void notificationGatewayTest() {
        final var result = declaration.notificationGateway();

        Assertions.assertNotNull(result);
    }
}
