package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.core.entity.Customer;

public interface CustomerGateway {

    Customer getCustomerByCpf(String cpf);

    Customer saveCustomer(Customer customer);

}
