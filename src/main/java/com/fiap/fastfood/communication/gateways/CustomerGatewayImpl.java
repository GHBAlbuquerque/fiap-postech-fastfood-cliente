package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.CustomerBuilder;
import com.fiap.fastfood.common.interfaces.datasources.CustomerRepository;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerGatewayImpl implements CustomerGateway {

    private final CustomerRepository repository;

    public CustomerGatewayImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        final var orm = CustomerBuilder.fromDomainToOrm(customer);
        final var result = repository.save(orm);
        return CustomerBuilder.fromOrmToDomain(result);
    }

    @Override
    public Customer getCustomerByCpf(String cpf) {
        final var result = repository.findByCpf(cpf);
        if (result != null) return CustomerBuilder.fromOrmToDomain(result);

        return null;
    }
}
