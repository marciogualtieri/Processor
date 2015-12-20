package com.akqa.booking.components.services;

import com.akqa.booking.components.helpers.BookingCalendarHelper;
import com.akqa.booking.components.helpers.BookingProcessorHelper;
import com.akqa.booking.components.helpers.BookingRequestHelper;
import com.akqa.booking.constants.BookingConstants;
import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.booking.messages.BookingMessages;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Process booking requests as text and outputs a booking calendar.
 */
@Component
public class BookingBatchProcessorService {

    private static final int BOOKING_REQUEST_NUMBER_OF_LINES = 2;

    private final BookingRequestHelper bookingRequestHelper;
    private final BookingCalendarHelper bookingCalendarHelper;
    private final BookingProcessorHelper bookingProcessorHelper;

    @Autowired
    public BookingBatchProcessorService(
            BookingRequestHelper bookingRequestHelper,
            BookingCalendarHelper bookingCalendarHelper,
            BookingProcessorHelper bookingProcessorHelper) {
        this.bookingRequestHelper = bookingRequestHelper;
        this.bookingCalendarHelper = bookingCalendarHelper;
        this.bookingProcessorHelper = bookingProcessorHelper;
    }

    public void processBatchFile(String bookingRequestBatchFileNameAndPath,
                                 String outputFileNameAndPath) throws BookingException {
        List<String> lines = readLinesFromFile(bookingRequestBatchFileNameAndPath);
        OfficeHours officeHours = bookingRequestHelper
                .parseOfficeHoursText(lines
                        .get(BookingConstants.OFFICE_HOURS_LINE_INDEX));
        lines.remove(BookingConstants.OFFICE_HOURS_LINE_INDEX);
        List<BookingRequest> bookingRequests = buildBookingRequestsFromFileLines(lines);
        Map<LocalDate, List<Booking>> bookingCalendar = bookingProcessorHelper
                .createBookingCalendarFromBookingRequests(bookingRequests,
                        officeHours);
        bookingCalendarHelper.createOutputFileFromBookingCalendar(
                bookingCalendar, outputFileNameAndPath);
    }

    private List<String> readLinesFromFile(
            String bookingRequestBatchFileNameAndPath) throws BookingException {
        String message = String
                .format(BookingMessages.BOOKING_BATCH_FILE_LOADING_EXCEPTION_MESSAGE_FORMAT,
                        bookingRequestBatchFileNameAndPath);
        File file = new File(bookingRequestBatchFileNameAndPath);
        List<String> lines;
        if (!file.exists()) {
            throw new BookingException(message, new IOException());
        }
        try {
            lines = FileUtils
                    .readLines(file, BookingConstants.DEFAULT_ENCODING);
        } catch (IOException e) {
            throw new BookingException(message, e);
        }
        return lines;
    }

    private List<BookingRequest> buildBookingRequestsFromFileLines(List<String> fileLines)
            throws BookingException {
        List<List<String>> bookingRequestsAsText = Lists.partition(fileLines,
                BOOKING_REQUEST_NUMBER_OF_LINES);
        List<BookingRequest> bookingRequests = new ArrayList<>();
        for (List<String> bookingRequestAsText : bookingRequestsAsText) {
            BookingRequest bookingRequest = bookingRequestHelper
                    .parseBookingRequestText(bookingRequestAsText);
            bookingRequests.add(bookingRequest);
        }
        return bookingRequests;
    }

}
