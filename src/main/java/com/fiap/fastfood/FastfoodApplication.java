package com.fiap.fastfood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
@OpenAPIDefinition(info = @Info(title = "Fast Food FIAP", description = "Microsserviço de gerenciamento de Clientes realizado para a Pós-Graduação de Arquitetura de Sistemas da FIAP", version = "v1"))
public class FastfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastfoodApplication.class, args);
    }

}
