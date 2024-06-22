package com.fiap.fastfood.external.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Customer")
public class CustomerORM {

    @MongoId
    private String id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;
    private String password;
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp;

}
