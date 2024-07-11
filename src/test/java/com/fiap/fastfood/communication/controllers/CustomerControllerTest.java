package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.ConfirmSignUpRequest;
import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import com.fiap.fastfood.common.dto.response.GetCustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;

    private final String CPF_TEST = String.valueOf(new SecureRandom().nextInt());

    @Test
    public void givenCustomerToRegisterThenRespondWithStatusCreated() {
        final var registerCustomerRequest = new RegisterCustomerRequest("name", LocalDate.now(), CPF_TEST, "email@email.com", "password");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(registerCustomerRequest)
                .when()
                .post("/customers")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);
    }

    @Test
    public void givenCpfThenRespondWithCustomer() {
        final var getCustomerResponse = new GetCustomerResponse()
                .setId(1L)
                .setName("name")
                .setCpf(CPF_TEST)
                .setEmail("FIAPauth123_")
                .setBirthday(LocalDate.now())
                .setCreationTimestamp(LocalDateTime.now())
                .setUpdateTimestamp(LocalDateTime.now());

        final var path = "/customers?cpf=" + CPF_TEST;

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get(path)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(JSON);
    }

    @Test
    public void givenIdThenRespondWithCustomer() {
        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/customers/1")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

    @Test
    public void givenConfirmSignUpRequestThenRespondWithSuccess() {
        final var confirmSignUpRequest = new ConfirmSignUpRequest("74952165060", "code");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(confirmSignUpRequest)
                .when()
                .post("/customers/confirmation")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(JSON);
    }
}