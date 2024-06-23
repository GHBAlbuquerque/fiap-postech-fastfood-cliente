package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.CustomerORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerORM, Long> {

    CustomerORM findByCpf(String cpf);
}