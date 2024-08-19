package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[RESPONSE] SagaId: {} | Command received from {}. ";
    public static final String COMMAND_END_LOG = "[RESPONSE] SagaId: {} | Command successfully received from {}.";
    public static final String COMMAND_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving command from {}. | Error Message: {} | Message: {}";

    public static final String RESPONSE_INIT_LOG = "[COMMAND] SagaId: {} | Sending response to {}...";
    public static final String RESPONSE_END_LOG = "[COMMAND] SagaId: {} | Response Succesfully sent to {}.";
    public static final String RESPONSE_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending response to {}. | Error Message: {} | Message: {}";

    public static final String NOTIFICATION_INIT_LOG = "[COMMAND] SagaId: {} | Sending {} notification to customer with id {} ...";
    public static final String NOTIFICATION_END_LOG = "[COMMAND] SagaId: {} | Notification sent.";
    public static final String NOTIFICATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending notification. | Error Message: {} ";

    public static final String IDENTITY_PROVIDER_USER_CREATED = "Identity Provider user succesfully created for username {}";
    public static final String IDENTITY_PROVIDER_USER_CONFIRMED = "Identity Provider user succesfully confirmed for username {}";
}
