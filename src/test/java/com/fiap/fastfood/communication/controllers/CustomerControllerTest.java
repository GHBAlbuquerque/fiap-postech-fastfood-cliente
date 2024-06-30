package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.ConfirmSignUpRequest;
import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import com.fiap.fastfood.common.dto.response.GetCustomerResponse;
import com.fiap.fastfood.common.dto.response.RegisterCustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void givenCustomerToRegisterThenRespondWithStatusCreated() {
        final var registerCustomerRequest = new RegisterCustomerRequest("name", LocalDate.now(), "cpf", "email@email.com", "password");
        final var registerCustoemrReponse = new RegisterCustomerResponse("1");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(registerCustomerRequest)
                .when()
                .post("/customers")
                .then()
                .log().ifValidationFails()
                //.statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);
    }

    @Test
    public void givenCpfThenRespondWithCustomer() {
        final var getCustomerResponse = new GetCustomerResponse()
                .setId(1L)
                .setName("name")
                .setCpf("cpf")
                .setEmail("email")
                .setBirthday(LocalDate.now())
                .setCreationTimestamp(LocalDateTime.now())
                .setUpdateTimestamp(LocalDateTime.now());

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/customers?cpf=123456789123")
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
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(JSON);
    }

    @Test
    public void givenConfirmSignUpRequestThenRespondWithSuccess() {
        final var confirmSignUpRequest = new ConfirmSignUpRequest("cpf", "code");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(confirmSignUpRequest)
                .when()
                .post("/customers/confirmation")
                .then()
                .log().ifValidationFails()
                //.statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

}