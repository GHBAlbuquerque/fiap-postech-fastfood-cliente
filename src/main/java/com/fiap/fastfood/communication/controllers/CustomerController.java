package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.CustomerBuilder;
import com.fiap.fastfood.common.dto.request.ConfirmSignUpRequest;
import com.fiap.fastfood.common.dto.request.RegisterCustomerRequest;
import com.fiap.fastfood.common.dto.response.GetCustomerResponse;
import com.fiap.fastfood.common.dto.response.RegisterCustomerResponse;
import com.fiap.fastfood.common.exceptions.custom.AlreadyRegisteredException;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.IdentityProviderRegistrationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.AuthenticationGateway;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.usecase.CustomerUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final AuthenticationGateway authenticationGateway;
    private final CustomerGateway customerGateway;
    private final CustomerUseCase useCase;

    public CustomerController(AuthenticationGateway authenticationGateway, CustomerGateway customerGateway, CustomerUseCase customerUseCase) {
        this.authenticationGateway = authenticationGateway;
        this.customerGateway = customerGateway;
        this.useCase = customerUseCase;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(
            @RequestBody RegisterCustomerRequest request
    ) throws AlreadyRegisteredException, IdentityProviderRegistrationException {

        final var customerReq = CustomerBuilder.fromRequestToDomain(request);
        final var customer = useCase.registerCustomer(customerReq, customerGateway, authenticationGateway);
        final var customerId = customer.getId().toString();

        final var uri = URI.create(customerId);

        return ResponseEntity.created(uri).body(new RegisterCustomerResponse(customerId));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetCustomerResponse> getCustomerByCpf(@RequestParam(required = true) String cpf)
            throws EntityNotFoundException {

        final var customer = useCase.getCustomerByCpf(cpf, customerGateway);
        final var customerResponse = CustomerBuilder.fromDomainToResponse(customer);

        return ResponseEntity.ok(customerResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(value = "/confirmation", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> confirmSignUp(@RequestBody(required = true) ConfirmSignUpRequest confirmSignUpRequest)
            throws IdentityProviderRegistrationException {

        final var response = useCase.confirmCustomerSignUp(confirmSignUpRequest.getCpf(),
                confirmSignUpRequest.getCode(),
                authenticationGateway);

        return ResponseEntity.ok(response);
    }
}
