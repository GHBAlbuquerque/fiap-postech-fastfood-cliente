package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void CustomerGatewayTest() {
        final var mock = Mockito.mock(CustomerRepository.class);

        final var result = declaration.customerGateway(mock);

        Assertions.assertNotNull(result);
    }
}
