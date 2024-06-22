package com.fiap.fastfood.common.interfaces.datasources;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fiap.fastfood.external.orm.CustomerORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerORM, Long> {

    CustomerORM findByCpf(String cpf);
}
Ø