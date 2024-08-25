package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.CustomerNotificationException;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.springframework.messaging.MessageHeaders;

public interface OrquestrationGateway {

    void listenToCustomerNotification(MessageHeaders headers, CustomQueueMessage<NotifyCustomerCommand> message) throws CustomerNotificationException;

    void sendResponse(String orderId,
                      Long customerId,
                      String paymentId,
                      OrquestrationStepEnum orquestrationStepEnum,
                      Boolean stepSuccessful) throws CustomerNotificationException;
}
