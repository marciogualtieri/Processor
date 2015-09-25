package com.akqa.booking.exceptions;

import com.google.common.base.Throwables;

public class BookingException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_FORMAT = "%s\n%s";

    public BookingException(String message, Exception exception) {
        super(buildMessage(message, exception));
    }

    private static String buildMessage(String message, Exception exception) {
        if (exception != null) {
            message = String.format(MESSAGE_FORMAT, message, Throwables.getStackTraceAsString(exception));
        }
        return message;
    }
}