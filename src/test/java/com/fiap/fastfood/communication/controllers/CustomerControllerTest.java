package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.ConfirmSignUpRequest;
import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import org.junit.jupiter.api.Order;
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

    private static final String CPF_TEST = String.valueOf(Math.abs(new SecureRandom().nextLong(10_000_000_000L, 100_000_000_000L)));

    private static String id;

    @Test
    @Order(1)
    void givenCustomerToRegisterThenRespondWithStatusCreated() {
        final var registerCustomerRequest = new RegisterCustomerRequest("name", LocalDate.now(), CPF_TEST, "email@email.com", "password", "11864537659");

        final var response = given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(registerCustomerRequest)
                .when()
                .post("/customers")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);

        id = response.extract().body().jsonPath().get("id");
        System.out.println(id);
    }

    @Test
    @Order(2)
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
    @Order(3)
    void givenIdThenRespondWithCustomer() {
        final var path = "/customers/" + id;

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
    @Order(4)
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

    @Test
    @Order(5)
    void givenCustomerToDeactivateThenRespondWithStatusOK() {
        final var path = "/customers/" + id;

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .delete(path)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value());
    }
}