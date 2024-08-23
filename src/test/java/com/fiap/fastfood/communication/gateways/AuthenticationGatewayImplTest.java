package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.IdentityProviderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ConfirmSignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthenticationGatewayImplTest {

    @Mock
    private CognitoIdentityProviderClient identityProviderClient;

    @InjectMocks
    private AuthenticationGatewayImpl authenticationGateway;

    @Test
    void createUserAuthenticationTest() {
        Mockito.when(identityProviderClient.signUp(any(SignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGateway.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void createUserAuthenticationErrorTest() {
        Mockito.when(identityProviderClient.signUp(any(SignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGateway.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void confirmSignUpTest() {
        Mockito.when(identityProviderClient.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGateway.confirmSignUp("username", "code")
        );
    }

    @Test
    void confirmSignUpErrorTest() {
        Mockito.when(identityProviderClient.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGateway.confirmSignUp("username", "code")
        );
    }
}
