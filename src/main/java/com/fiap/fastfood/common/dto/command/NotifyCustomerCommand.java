package com.fiap.fastfood.common.dto.command;

import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NotifyCustomerCommand {

    private String orderId;
    private Long customerId;
    private String paymentId;
    private OrquestrationStepEnum step;
}
