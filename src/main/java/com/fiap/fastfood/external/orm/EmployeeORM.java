package com.fiap.fastfood.external.orm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
public class EmployeeORM {

    @Id
    private String id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;
    private String password;
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp;

}
