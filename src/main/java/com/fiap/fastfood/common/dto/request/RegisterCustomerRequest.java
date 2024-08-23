package com.fiap.fastfood.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterCustomerRequest {

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank
    @Size(min = 11, message = "Telefone (com DDD) deve ter 11 caracteres")
    private String contactNumber;
}
