package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.ConfirmSignUpRequest;
import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    private static final String CPF_TEST = String.valueOf(new SecureRandom().nextInt());

    @Test
    void givenCustomerToRegisterThenRespondWithStatusCreated() {
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
    void givenCpfThenRespondWithCustomer() {
        final var path = "/customers?cpf=" + CPF_TEST;

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get(path)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

    @Test
    void givenIdThenRespondWithCustomer() {
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
    void givenConfirmSignUpRequestThenRespondWithSuccess() {
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