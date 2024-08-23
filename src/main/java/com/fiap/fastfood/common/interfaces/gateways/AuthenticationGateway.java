package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.IdentityProviderException;

public interface AuthenticationGateway {

    Boolean createUserAuthentication(String userName,
                                     String password,
                                     String email) throws IdentityProviderException;


    Boolean confirmSignUp(String userName,
                          String code) throws IdentityProviderException;

    Boolean deleteUser(String username,
                              String password) throws IdentityProviderException;
}
