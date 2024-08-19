package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ConfirmSignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

import java.util.ArrayList;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.CUSTOMER_03_IDENTITY_PROVIDER;

@Component
public class AuthenticationGatewayImpl implements AuthenticationGateway {

    @Value("${aws.cognito.clientid}")
    private String identityProviderClientId;

    @Value("${aws.cognito.clientSecretKey}")
    private String identityProviderClientSecretKey;

    private final CognitoIdentityProviderClient identityProviderClient;

    private static final Logger logger = LogManager.getLogger(AuthenticationGatewayImpl.class);

    public AuthenticationGatewayImpl(CognitoIdentityProviderClient identityProviderClient) {
        this.identityProviderClient = identityProviderClient;
    }


    @Override
    public Boolean createUserAuthentication(String userName,
                                            String password,
                                            String email) throws IdentityProviderRegistrationException {

        String emailField = "email";
        var attributeType = AttributeType.builder()
                .name(emailField)
                .value(email)
                .build();

        var attrs = new ArrayList<AttributeType>();
        attrs.add(attributeType);

        try {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(attrs)
                    .username(userName)
                    .clientId(identityProviderClientId)
                    .password(password)
                    .build();

            identityProviderClient.signUp(signUpRequest);

            logger.info(LoggingPattern.IDENTITY_PROVIDER_USER_CREATED,
                    userName);


            return true;

        } catch (CognitoIdentityProviderException e) {
            throw new IdentityProviderRegistrationException(CUSTOMER_03_IDENTITY_PROVIDER, e.getMessage());
        }
    }

    public Boolean confirmSignUp(String userName, String code) throws IdentityProviderRegistrationException {
        try {
            ConfirmSignUpRequest signUpRequest = ConfirmSignUpRequest.builder()
                    .clientId(identityProviderClientId)
                    .confirmationCode(code)
                    .username(userName)
                    .build();

            identityProviderClient.confirmSignUp(signUpRequest);

            logger.info(LoggingPattern.IDENTITY_PROVIDER_USER_CONFIRMED,
                    userName);

            return true;

        } catch (CognitoIdentityProviderException e) {
            throw new IdentityProviderRegistrationException(CUSTOMER_03_IDENTITY_PROVIDER, e.getMessage());
        }
    }
}
