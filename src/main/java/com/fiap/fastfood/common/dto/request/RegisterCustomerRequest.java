package com.fiap.fastfood.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterCustomerRequest {

    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotBlank
    private String cpf;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
