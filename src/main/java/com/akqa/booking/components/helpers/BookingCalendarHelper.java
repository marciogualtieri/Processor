package com.akqa.booking.components.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.akqa.booking.constants.BookingConstants;
import com.akqa.booking.entities.Booking;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.booking.messages.BookingMessages;

@Component
public class BookingCalendarHelper {

    public void createOutputFileFromBookingCalendar(
            Map<LocalDate, List<Booking>> bookingCalendar,
            String outputFileNameAndPath) throws BookingException {
        File outputFile = null;
        String message = String.format(
                BookingMessages.BOOKING_OUTPUT_FILE_EXCEPTION_MESSAGE_FORMAT,
                outputFileNameAndPath);
        if (!CollectionUtils.isEmpty(bookingCalendar)) {
            try {
                outputFile = new File(outputFileNameAndPath);
                File outputDirectory = outputFile.getParentFile();
                if (outputDirectory != null
                        && !(outputDirectory.exists() && outputDirectory
                                .isDirectory())) {
                    throw new BookingException(message, new IOException());
                }
                List<LocalDate> calendarDates = new ArrayList<LocalDate>(
                        bookingCalendar.keySet());
                Collections.sort(calendarDates);
                for (LocalDate calendarDate : calendarDates) {
                    writeCalendarEntryToFile(outputFile, calendarDate,
                            bookingCalendar.get(calendarDate));
                }
            } catch (IOException e) {
                throw new BookingException(message, e);
            }
        }
    }

    private void writeCalendarEntryToFile(File outputFile,
            LocalDate calendarDate, List<Booking> bookings) throws IOException {
        FileUtils.writeStringToFile(outputFile, calendarDate.toString()
                + BookingConstants.LINE_SEPARATOR,
                BookingConstants.IS_APPEND_TO_FILE);
        sortBookings(bookings);
        for (Booking booking : bookings) {
            FileUtils.writeStringToFile(outputFile,
                    buildBookingEntryString(booking),
                    BookingConstants.IS_APPEND_TO_FILE);
        }
    }

    private void sortBookings(List<Booking> bookings) {
        if (!bookings.isEmpty()) {
            Collections.sort(bookings, new Comparator<Booking>() {
                @Override
                public int compare(final Booking booking,
                        final Booking anotherBooking) {
                    return booking.getMeetingStartTime().compareTo(
                            anotherBooking.getMeetingStartTime());
                }
            });
        }
    }

    private String buildBookingEntryString(Booking booking) {
        return String.format(
                "%s %s %s%s",
                booking.getMeetingStartTime().toString(
                        BookingConstants.OUTPUT_DATE_FORMAT),
                booking.getMeetingEndTime().toString(
                        BookingConstants.OUTPUT_DATE_FORMAT), booking
                        .getEmployeeId(), BookingConstants.LINE_SEPARATOR);
    }

}
