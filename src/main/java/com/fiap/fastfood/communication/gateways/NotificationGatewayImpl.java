package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.gateways.NotificationGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationGatewayImpl implements NotificationGateway {

    private static final Logger logger = LogManager.getLogger(NotificationGatewayImpl.class);

    @Override
    public void notifyCustomer(Long customerId, OrquestrationStepEnum step, OrquestrationGateway orquestrationGateway) {
        //TODO
        /*
        logger.info(
                LoggingPattern.PAYMENT_CREATION_INIT_LOG,
                TransactionInformationStorage.getSagaId()
        );

        try {

            final var savedPayment = paymentGateway.save(payment);

            orquestrationGateway.sendResponse(
                    TransactionInformationStorage.getOrderId(),
                    Long.valueOf(TransactionInformationStorage.getCustomerId()),
                    savedPayment.getId(),
                    OrquestrationStepEnum.CREATE_PAYMENT,
                    Boolean.TRUE
            );

            logger.info(
                    LoggingPattern.PAYMENT_CREATION_END_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            return savedPayment;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.PAYMENT_CREATION_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {
                orquestrationGateway.sendResponse(
                        TransactionInformationStorage.getOrderId(),
                        Long.valueOf(TransactionInformationStorage.getCustomerId()),
                        null,
                        OrquestrationStepEnum.CREATE_PAYMENT,
                        Boolean.FALSE
                );
            }

            throw new PaymentCreationException(
                    ExceptionCodes.PAYMENT_02_PAYMENT_CREATION,
                    String.format("Error creating payment. Error: %s", ex.getMessage())
            );
        }
         */
    }

}
