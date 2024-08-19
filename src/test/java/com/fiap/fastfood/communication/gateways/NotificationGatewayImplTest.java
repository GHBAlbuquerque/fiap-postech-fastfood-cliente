package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.CustomerNotificationException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fiap.fastfood.core.entity.OrquestrationStepEnum.NOTIFY_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationGatewayImplTest {

    @Mock
    private OrquestrationGateway orquestrationGateway;

    @InjectMocks
    private NotificationGatewayImpl notificationGateway;

    @Test
    void testNotifyCustomerSuccess() throws Exception {
        // Act
        notificationGateway.notifyCustomer(1L, NOTIFY_CUSTOMER, orquestrationGateway);

        // Verify
        verify(orquestrationGateway, times(1)).sendResponse(
                null,
                1L,
                null,
                NOTIFY_CUSTOMER,
                true
        );
    }

    @Test
    void testNotifyCustomerException() throws Exception {
        // Setup
        TransactionInformationStorage.putReceiveCount("1");
        doThrow(new RuntimeException("Simulated exception")).when(orquestrationGateway).sendResponse(
                null,
                1L,
                null,
                NOTIFY_CUSTOMER,
                true
        );

        // Act & Assert
        CustomerNotificationException thrown = assertThrows(
                CustomerNotificationException.class,
                () -> notificationGateway.notifyCustomer(1L, NOTIFY_CUSTOMER, orquestrationGateway),
                "Expected notifyCustomer() to throw, but it didn't"
        );

        assertEquals(ExceptionCodes.CUSTOMER_05_NOTIFICATION_FAILED, thrown.getCode());
        assertTrue(thrown.getMessage().contains("Error creating payment. Error: Simulated exception"));
    }
}

