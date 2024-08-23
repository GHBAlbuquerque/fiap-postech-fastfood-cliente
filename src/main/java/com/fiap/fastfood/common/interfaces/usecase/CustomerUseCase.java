package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.CustomerDeactivationException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;

public interface CustomerUseCase {

    Customer getCustomerByCpf(String cpf, CustomerGateway customerGateway) throws EntityNotFoundException;

    Customer getCustomerById(Long id, CustomerGateway customerGateway) throws EntityNotFoundException;

    Customer registerCustomer(Customer customer, CustomerGateway customerGateway, AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderRegistrationException;

    Boolean validateCpfInUse(String cpf, CustomerGateway customerGateway);

    Boolean confirmCustomerSignUp(String cpf, String code, AuthenticationGateway authenticationGateway) throws IdentityProviderRegistrationException;

    Boolean deactivateCustomer(Long id, CustomerGateway customerGateway) throws EntityNotFoundException, CustomerDeactivationException;
}
