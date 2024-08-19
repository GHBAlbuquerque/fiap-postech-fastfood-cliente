package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.core.entity.OrquestrationStepEnum;

public interface NotificationGateway {

    void notifyCustomer(Long customerId, OrquestrationStepEnum step, OrquestrationGateway orquestrationGateway);
}
