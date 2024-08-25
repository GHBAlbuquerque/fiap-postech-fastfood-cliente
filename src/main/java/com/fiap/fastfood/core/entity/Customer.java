package com.fiap.fastfood.core.entity;

import com.fiap.fastfood.common.exceptions.model.CustomError;
import com.fiap.fastfood.common.validation.ValidationResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
public class Customer extends Person {

    private Long id;
    private Boolean isActive;

    public static ValidationResult validate(Customer customer, Boolean cpfInUse) {
        final var validationResult = new ValidationResult();
        final var cpf = customer.getCpf();

        if (cpfInUse) {
            validationResult.getErrors().add(
                    new CustomError(
                            "CPF document number informed for registration is already in use.",
                            "cpf",
                            cpf));
        }

        if (!validationResult.getErrors().isEmpty())
            validationResult.setIsValid(false);

        return validationResult;
    }

    public Customer(String name, LocalDate birthday, String cpf, String email, String password, String contactNumber, LocalDateTime creationTimestamp, LocalDateTime updateTimestamp, Long id, Boolean isActive) {
        super(name, birthday, cpf, email, password, contactNumber, creationTimestamp, updateTimestamp);
        this.id = id;
        this.isActive = isActive;
    }

    public Customer() {
        super();
    }
}
