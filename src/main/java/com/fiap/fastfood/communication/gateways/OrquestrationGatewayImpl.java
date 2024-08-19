package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.CustomerNotificationException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.NotificationGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.logging.Constants;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;

import static com.fiap.fastfood.common.logging.Constants.*;
import static io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode.ON_SUCCESS;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final NotificationGateway notificationGateway;
    private final MessageSender messageSender;

    private static final Logger logger = LogManager.getLogger(OrquestrationGatewayImpl.class);

    public OrquestrationGatewayImpl(NotificationGateway notificationGateway, MessageSender messageSender) {
        this.notificationGateway = notificationGateway;
        this.messageSender = messageSender;
    }

    @Value("${aws.queue_resposta_criar_pedido.url}")
    private String queueResponseSaga;

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_notificar_cliente.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1", acknowledgementMode = ON_SUCCESS)
    public void listenToCustomerNotification(MessageHeaders headers, CustomQueueMessage<NotifyCustomerCommand> message) throws CustomerNotificationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            notificationGateway.notifyCustomer(
                    message.getBody().getCustomerId(),
                    message.getBody().getStep(),
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new CustomerNotificationException(ExceptionCodes.CUSTOMER_05_NOTIFICATION_FAILED, ex.getMessage());
        }
    }

    @Override
    public void sendResponse(String orderId, Long customerId, String paymentId, OrquestrationStepEnum orquestrationStepEnum, Boolean stepSuccessful) throws CustomerNotificationException {
        logger.info(
                LoggingPattern.RESPONSE_INIT_LOG,
                TransactionInformationStorage.getSagaId(),
                Constants.MS_SAGA
        );

        final var message = createResponseMessage(orderId, customerId, paymentId, orquestrationStepEnum, stepSuccessful);

        try {

            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueResponseSaga
            );

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getSagaId(),
                    Constants.MS_SAGA);

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    Constants.MS_SAGA,
                    ex.getMessage(),
                    message.toString());

            throw new CustomerNotificationException(ExceptionCodes.CUSTOMER_04_CUSTOMER_RESPONSE, ex.getMessage());

        }
    }

    private static CustomQueueMessage<CreateOrderResponse> createResponseMessage(String orderId, Long customerId, String paymentId, OrquestrationStepEnum orquestrationStepEnum, Boolean stepSuccessful) {
        final var headers = new CustomMessageHeaders(TransactionInformationStorage.getSagaId(), orderId, MESSAGE_TYPE_RESPONSE, MS_CUSTOMER);
        return new CustomQueueMessage<>(
                headers,
                new CreateOrderResponse(orderId,
                        customerId,
                        paymentId,
                        orquestrationStepEnum,
                        stepSuccessful)
        );
    }
}
