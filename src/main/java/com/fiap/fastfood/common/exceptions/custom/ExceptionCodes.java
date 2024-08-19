package com.fiap.fastfood.common.exceptions.custom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCodes {

    CUSTOMER_01_NOT_FOUND,
    CUSTOMER_02_ALREADY_REGISTERED,
    CUSTOMER_03_IDENTITY_PROVIDER,
    CUSTOMER_04_CUSTOMERID_UNMATCH,
    CUSTOMER_05_PRODUCT_PRICE_UNMATCH,
    CUSTOMER_06_MESSAGE_CREATION
}
