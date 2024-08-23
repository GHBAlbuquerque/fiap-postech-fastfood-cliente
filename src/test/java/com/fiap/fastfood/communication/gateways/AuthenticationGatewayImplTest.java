package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.IdentityProviderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.CUSTOMER_03_IDENTITY_PROVIDER;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationGatewayImplTest {

    @Mock
    private CognitoIdentityProviderClient identityProviderClientMock;

    @InjectMocks
    private AuthenticationGatewayImpl authenticationGatewayMock;

    @Test
    void createUserAuthenticationTest() {
        when(identityProviderClientMock.signUp(any(SignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGatewayMock.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void createUserAuthenticationErrorTest() {
        when(identityProviderClientMock.signUp(any(SignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGatewayMock.createUserAuthentication("username", "password", "email")
        );
    }

    @Test
    void confirmSignUpTest() {
        when(identityProviderClientMock.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenReturn(null);

        Assertions.assertDoesNotThrow(
                () -> authenticationGatewayMock.confirmSignUp("username", "code")
        );
    }

    @Test
    void confirmSignUpErrorTest() {
        when(identityProviderClientMock.confirmSignUp(any(ConfirmSignUpRequest.class)))
                .thenThrow(CognitoIdentityProviderException.class);

        Assertions.assertThrows(
                IdentityProviderException.class,
                () -> authenticationGatewayMock.confirmSignUp("username", "code")
        );
    }

    @Test
    void testDeleteUser_Success() throws IdentityProviderException {
        final var username = "testUser";
        final var password = "testPass";

        // Mock the getAuthToken method
        final var initiateAuth = mock(InitiateAuthResponse.class);
        final var authResult = mock(AuthenticationResultType.class);

        when(identityProviderClientMock.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(initiateAuth);
        when(initiateAuth.authenticationResult()).thenReturn(authResult);
        when(authResult.accessToken()).thenReturn("token");

        // Test the method
        final var result = authenticationGatewayMock.deleteUser(username, password);

        // Verify the behavior
        assertTrue(result);
    }

    @Test
    void testDeleteUser_Failure_InvalidAuth() throws IdentityProviderException {
        final var username = "testUser";
        final var password = "testPass";

        // Mock an exception during authentication
        when(identityProviderClientMock.initiateAuth(any(InitiateAuthRequest.class)))
                .thenThrow(CognitoIdentityProviderException.builder().message("Invalid credentials").build());

        // Test the method and expect an exception
        final var exception = assertThrows(IdentityProviderException.class, () -> {
            authenticationGatewayMock.deleteUser(username, password);
        });

        // Verify the behavior
        assertEquals(CUSTOMER_03_IDENTITY_PROVIDER, exception.getCode());
    }
}
