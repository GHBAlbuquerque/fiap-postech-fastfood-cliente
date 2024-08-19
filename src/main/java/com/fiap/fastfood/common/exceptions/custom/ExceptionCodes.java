package com.fiap.fastfood.common.exceptions.custom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCodes {

    CUSTOMER_01_NOT_FOUND,
    CUSTOMER_02_ALREADY_REGISTERED,
    CUSTOMER_03_IDENTITY_PROVIDER,
    CUSTOMER_04_CUSTOMER_RESPONSE,
    CUSTOMER_05_NOTIFICATION_FAILED,
    CUSTOMER_06_MESSAGE_CREATION
}
