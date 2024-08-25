package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import com.fiap.fastfood.common.dto.response.GetCustomerResponse;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.orm.CustomerORM;

import java.time.LocalDateTime;

public class CustomerBuilder {

    public CustomerBuilder() {
    }

    public static Customer fromRequestToDomain(RegisterCustomerRequest request) {
        return (Customer) new Customer()
                .setName(request.getName())
                .setBirthday(request.getBirthday())
                .setCpf(request.getCpf())
                .setCreationTimestamp(LocalDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setContactNumber(request.getContactNumber());
    }

    public static GetCustomerResponse fromDomainToResponse(Customer customer) {
        return new GetCustomerResponse()
                .setId(customer.getId())
                .setName(customer.getName())
                .setBirthday(customer.getBirthday())
                .setCpf(customer.getCpf())
                .setEmail(customer.getEmail())
                .setCreationTimestamp(customer.getCreationTimestamp())
                .setUpdateTimestamp(customer.getUpdateTimestamp())
                .setIsActive(customer.getIsActive())
                .setContactNumber(customer.getContactNumber());
    }

    public static Customer fromOrmToDomain(CustomerORM orm) {
        return new Customer(orm.getName(),
                orm.getBirthday(),
                orm.getCpf(),
                orm.getEmail(),
                orm.getPassword(),
                orm.getContactNumber(),
                orm.getCreationTimestamp(),
                orm.getUpdateTimestamp(),
                orm.getId(),
                orm.getIsActive()
        );
    }

    public static CustomerORM fromDomainToOrm(Customer customer) {
        return new CustomerORM()
                .setId(customer.getId())
                .setName(customer.getName())
                .setBirthday(customer.getBirthday())
                .setCpf(customer.getCpf())
                .setEmail(customer.getEmail())
                .setPassword(customer.getPassword())
                .setContactNumber(customer.getContactNumber())
                .setCreationTimestamp(customer.getCreationTimestamp())
                .setUpdateTimestamp(customer.getUpdateTimestamp())
                .setIsActive(customer.getIsActive());
    }
}
