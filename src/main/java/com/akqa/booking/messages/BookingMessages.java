package com.akqa.booking.messages;

public class BookingMessages {
    public static final String BOOKING_REQUEST_PARSING_EXCEPTION_MESSAGE_FORMAT = "Error parsing booking request [%s]";
    public static final String OFFICE_HOURS_PARSING_EXCEPTION_MESSAGE_FORMAT = "Error parsing office hours [%s]";
    public static final String BOOKING_BATCH_FILE_LOADING_EXCEPTION_MESSAGE_FORMAT = "Error loading booking request batch file [%s]";
    public static final String BOOKING_OUTPUT_FILE_EXCEPTION_MESSAGE_FORMAT = "Error creating output file [%s]";

    private BookingMessages() {
    }
}
