package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import com.fiap.fastfood.common.dto.response.GetCustomerResponse;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.orm.CustomerORM;

import java.time.LocalDateTime;

public class CustomerBuilder {

    public static Customer fromRequestToDomain(RegisterCustomerRequest request) {
        return (Customer) new Customer()
                .setName(request.getName())
                .setBirthday(request.getBirthday())
                .setCpf(request.getCpf())
                .setCreationTimestamp(LocalDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword());
    }

    public static GetCustomerResponse fromDomainToResponse(Customer customer) {
        return new GetCustomerResponse()
                .setId(customer.getId())
                .setName(customer.getName())
                .setBirthday(customer.getBirthday())
                .setCpf(customer.getCpf())
                .setEmail(customer.getEmail())
                .setCreationTimestamp(customer.getCreationTimestamp())
                .setUpdateTimestamp(customer.getUpdateTimestamp());
    }

    public static Customer fromOrmToDomain(CustomerORM orm) {
        return (Customer) new Customer()
                .setId(orm.getId())
                .setName(orm.getName())
                .setBirthday(orm.getBirthday())
                .setCpf(orm.getCpf())
                .setCreationTimestamp(LocalDateTime.now())
                .setEmail(orm.getEmail())
                .setPassword(orm.getPassword());
    }

    public static CustomerORM fromDomainToOrm(Customer customer) {
        return (CustomerORM) new CustomerORM()
                .setId(customer.getId())
                .setName(customer.getName())
                .setBirthday(customer.getBirthday())
                .setCpf(customer.getCpf())
                .setEmail(customer.getEmail())
                .setPassword(customer.getPassword())
                .setCreationTimestamp(customer.getCreationTimestamp())
                .setUpdateTimestamp(customer.getUpdateTimestamp());
    }
}
