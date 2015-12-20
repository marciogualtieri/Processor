package com.akqa.booking.components.helpers;

import com.akqa.booking.constants.BookingConstants;
import com.akqa.booking.entities.Booking;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.booking.messages.BookingMessages;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class BookingCalendarHelper {

    public void createOutputFileFromBookingCalendar(
            Map<LocalDate, List<Booking>> bookingCalendar,
            String outputFileNameAndPath) throws BookingException {
        if (!CollectionUtils.isEmpty(bookingCalendar)) {
            String message = String.format(
                    BookingMessages.BOOKING_OUTPUT_FILE_WRITING_EXCEPTION_MESSAGE_FORMAT,
                    outputFileNameAndPath);
            try {
                File outputFile = getOutputFile(outputFileNameAndPath);
                List<LocalDate> calendarDates = new ArrayList<>(
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

    private File getOutputFile(String outputFileNameAndPath) throws BookingException {
        File outputFile = new File(outputFileNameAndPath);
        File outputDirectory = outputFile.getParentFile();
        validateOutputDirectory(outputDirectory);
        return outputFile;
    }

    private void validateOutputDirectory(File outputDirectory) throws BookingException {
        if (outputDirectory == null
                || !outputDirectory.exists() || !outputDirectory
                .isDirectory()) {
            String message = String.format(
                    BookingMessages.BOOKING_OUTPUT_DIRECTORY_EXCEPTION_MESSAGE_FORMAT,
                    outputDirectory == null ? null : outputDirectory.getAbsoluteFile());
            throw new BookingException(message, new IOException());
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
