package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.CustomerORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerORM, Long> {

    Optional<CustomerORM> findByCpf(String cpf);
}