package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.CustomerNotificationException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.NotificationGateway;
import com.fiap.fastfood.common.logging.Constants;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestrationGatewayImplTest {

    @Mock
    private NotificationGateway notificationGateway;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private OrquestrationGatewayImpl orquestrationGatewayImpl;

    @Test
    void testListenToCustomerNotificationSuccess() throws Exception {
        // Setup
        final var command = new NotifyCustomerCommand("orderId", 1L, "paymentId", OrquestrationStepEnum.NOTIFY_CUSTOMER);
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "source"),
                command
        );

        MessageHeaders headers = new MessageHeaders(Map.of(Constants.HEADER_RECEIVE_COUNT, "0"));

        // Act
        orquestrationGatewayImpl.listenToCustomerNotification(headers, message);

        // Verify
        verify(notificationGateway, times(1)).notifyCustomer(
                eq(1L),
                eq(OrquestrationStepEnum.NOTIFY_CUSTOMER),
                eq(orquestrationGatewayImpl)
        );
    }

    @Test
    void testListenToCustomerNotificationException() throws Exception {
        // Setup
        final var command = new NotifyCustomerCommand("orderId", 1L, "paymentId", OrquestrationStepEnum.NOTIFY_CUSTOMER);
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "source"),
                command
        );

        MessageHeaders headers = new MessageHeaders(Map.of(Constants.HEADER_RECEIVE_COUNT, "0"));

        doThrow(new RuntimeException("Simulated exception")).when(notificationGateway).notifyCustomer(anyLong(), any(), any());

        // Act & Assert
        final var thrown = assertThrows(
                CustomerNotificationException.class,
                () -> orquestrationGatewayImpl.listenToCustomerNotification(headers, message),
                "Expected listenToCustomerNotification() to throw, but it didn't"
        );

        assertEquals(ExceptionCodes.CUSTOMER_05_NOTIFICATION_FAILED, thrown.getCode());
        assertTrue(thrown.getMessage().contains("Simulated exception"));
    }

    @Test
    void testSendResponseSuccess() throws Exception {
        // Setup
        TransactionInformationStorage.fill(new CustomMessageHeaders("sagaId", "orderId", "RESPONSE", "ms"));

        // Act
        orquestrationGatewayImpl.sendResponse("orderId", 1L, "paymentId", OrquestrationStepEnum.NOTIFY_CUSTOMER, Boolean.TRUE);

        // Verify
        verify(messageSender, times(1)).sendMessage(
                any(),
                anyString(),
                any()
        );
    }

    @Test
    void testSendResponseException() throws Exception {
        // Setup
        TransactionInformationStorage.fill(new CustomMessageHeaders("sagaId", "orderId", "RESPONSE", "ms"));

        doThrow(new RuntimeException("Simulated exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        // Act & Assert
        CustomerNotificationException thrown = assertThrows(
                CustomerNotificationException.class,
                () -> orquestrationGatewayImpl.sendResponse("orderId", 1L, "paymentId", OrquestrationStepEnum.NOTIFY_CUSTOMER, Boolean.TRUE),
                "Expected sendResponse() to throw, but it didn't"
        );

        assertEquals(ExceptionCodes.CUSTOMER_04_CUSTOMER_RESPONSE, thrown.getCode());
    }
}

