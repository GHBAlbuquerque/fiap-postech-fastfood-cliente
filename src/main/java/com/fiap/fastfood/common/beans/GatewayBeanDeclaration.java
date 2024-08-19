package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.CustomerRepository;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.communication.gateways.AuthenticationGatewayImpl;
import com.fiap.fastfood.communication.gateways.CustomerGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public CustomerGateway customerGateway(CustomerRepository repository) {
        return new CustomerGatewayImpl(repository);
    }

    @Bean
    public AuthenticationGateway authenticationGateway(CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        return new AuthenticationGatewayImpl(cognitoIdentityProviderClient);
    }

    //TODO: COLOCAR NOVOS GATEWAYS
}
