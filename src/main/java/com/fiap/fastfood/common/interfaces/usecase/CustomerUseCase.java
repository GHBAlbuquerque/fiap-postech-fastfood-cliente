package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.CustomerDeactivationException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;

public interface CustomerUseCase {

    Customer getCustomerByCpf(String cpf, CustomerGateway customerGateway) throws EntityNotFoundException;

    Customer getCustomerById(Long id, CustomerGateway customerGateway) throws EntityNotFoundException;

    Customer registerCustomer(Customer customer, CustomerGateway customerGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderException;

    Boolean validateCpfInUse(String cpf, CustomerGateway customerGateway);

    Boolean confirmCustomerSignUp(String cpf, String code, AuthenticationGateway authenticationGateway) throws IdentityProviderException;

    Boolean deactivateCustomer(Long id, String cpf, String password, CustomerGateway customerGateway, AuthenticationGateway authenticationGateway) throws EntityNotFoundException, CustomerDeactivationException;
}
