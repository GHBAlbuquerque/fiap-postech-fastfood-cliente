package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.usecase.CustomerUseCase;
import com.fiap.fastfood.core.entity.Customer;

public class CustomerUseCaseImpl implements CustomerUseCase {

    @Override
    public Customer registerCustomer(Customer customer,
                                     CustomerGateway customerGateway,
                                     AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderRegistrationException {

        final var cpfInUse = validateCpfInUse(customer.getCpf(), customerGateway);
        final var validationResult = Customer.validate(customer, cpfInUse);

        if (!validationResult.getIsValid()) {
            throw new AlreadyRegisteredException(
                    "CUSTOMER-01",
                    "Couldn't complete registration for customer.",
                    validationResult.getErrors()
            );
        }

        authenticationGateway.createUserAuthentication(customer.getCpf(),
                customer.getPassword(),
                customer.getEmail());

        return customerGateway.saveCustomer(customer);
    }

    @Override
    public Customer getCustomerByCpf(String cpf, CustomerGateway customerGateway)
            throws EntityNotFoundException {
        final var client = customerGateway.getCustomerByCpf(cpf);

        if (client == null) {
            throw new EntityNotFoundException(
                    "CUSTOMER-02",
                    "Customer not found."
            );
        }

        return client;
    }

    @Override
    public Customer getCustomerById(Long id, CustomerGateway customerGateway)
            throws EntityNotFoundException {
        final var client = customerGateway.getCustomerById(id);

        if (client == null) {
            throw new EntityNotFoundException(
                    "CUSTOMER-02",
                    "Client not found."
            );
        }

        return client;
    }

    @Override
    public Boolean validateCpfInUse(String cpf, CustomerGateway customerGateway) {
        final var clientUsingCpf = customerGateway.getCustomerByCpf(cpf);

        return clientUsingCpf != null;
    }

    @Override
    public Boolean confirmCustomerSignUp(String cpf, String code, AuthenticationGateway authenticationGateway)
            throws IdentityProviderRegistrationException {
        return authenticationGateway.confirmSignUp(cpf, code);
    }
}
