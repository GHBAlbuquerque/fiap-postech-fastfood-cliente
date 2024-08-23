package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.*;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.usecase.CustomerUseCase;
import com.fiap.fastfood.core.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class CustomerUseCaseImpl implements CustomerUseCase {

    private static final Logger logger = LogManager.getLogger(CustomerUseCaseImpl.class);

    @Override
    public Customer registerCustomer(Customer customer,
                                     CustomerGateway customerGateway,
                                     AuthenticationGateway authenticationGateway)
            throws AlreadyRegisteredException, IdentityProviderRegistrationException {

        final var cpfInUse = validateCpfInUse(customer.getCpf(), customerGateway);
        final var validationResult = Customer.validate(customer, cpfInUse);

        if (Boolean.FALSE.equals(validationResult.getIsValid())) {
            throw new AlreadyRegisteredException(
                    ExceptionCodes.CUSTOMER_02_ALREADY_REGISTERED,
                    "Couldn't complete registration for customer.",
                    validationResult.getErrors()
            );
        }

        authenticationGateway.createUserAuthentication(customer.getCpf(),
                customer.getPassword(),
                customer.getEmail());

        customer.setIsActive(Boolean.TRUE);
        return customerGateway.saveCustomer(customer);
    }

    @Override
    public Customer getCustomerByCpf(String cpf, CustomerGateway customerGateway)
            throws EntityNotFoundException {
        final var customer = customerGateway.getCustomerByCpf(cpf);

        if (customer == null) {
            throw new EntityNotFoundException(
                    ExceptionCodes.CUSTOMER_01_NOT_FOUND,
                    "Customer not found."
            );
        }

        return customer;
    }

    @Override
    public Customer getCustomerById(Long id, CustomerGateway customerGateway)
            throws EntityNotFoundException {
        final var customer = customerGateway.getCustomerById(id);

        if (customer == null) {
            throw new EntityNotFoundException(
                    ExceptionCodes.CUSTOMER_01_NOT_FOUND,
                    "Client not found."
            );
        }

        return customer;
    }

    @Override
    public Boolean validateCpfInUse(String cpf, CustomerGateway customerGateway) {
        final var customerUsingCpf = customerGateway.getCustomerByCpf(cpf);

        return customerUsingCpf != null;
    }

    @Override
    public Boolean confirmCustomerSignUp(String cpf, String code, AuthenticationGateway authenticationGateway)
            throws IdentityProviderRegistrationException {
        return authenticationGateway.confirmSignUp(cpf, code);
    }

    @Override
    public Boolean deactivateCustomer(Long id, CustomerGateway customerGateway) throws CustomerDeactivationException {
        try {
            logger.info("Iniciating customer deactivation...");

            final var customer = getCustomerById(id, customerGateway);

            customer.setIsActive(Boolean.FALSE);
            customer.setName(null);
            customer.setContactNumber(null);
            customer.setCpf(null);
            customer.setUpdateTimestamp(LocalDateTime.now());

            logger.info("Name, Contact Number and CPF will be forever erased.");

            customerGateway.saveCustomer(customer);

            logger.info("Customer successfully deactivated; PII removed from database.");

            return Boolean.TRUE;
        } catch (Exception ex) {
            logger.error(ex.getMessage());

            throw new CustomerDeactivationException(
                    ExceptionCodes.CUSTOMER_07_CUSTOMER_DEACTIVATION,
                    "Error when trying to deactivate customer. Please contact the admin."
            );
        }
    }
}
