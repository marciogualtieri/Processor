package com.akqa.booking.components.helpers;

import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookingProcessorHelper {

    private final BookingRequestHelper bookingRequestHelper;

    @Autowired
    public BookingProcessorHelper(BookingRequestHelper bookingRequestHelper) {
        this.bookingRequestHelper = bookingRequestHelper;
    }

    public Map<LocalDate, List<Booking>> createBookingCalendarFromBookingRequests(
            List<BookingRequest> bookingRequests, OfficeHours officeHours) {
        Map<LocalDate, List<Booking>> bookingCalendar = new HashMap<>();
        sortBookingRequests(bookingRequests);
        for (BookingRequest bookingRequest : bookingRequests) {
            processBookingRequestAndAddEntryToCalendar(bookingRequest, officeHours, bookingCalendar);
        }
        return bookingCalendar;
    }

    private void processBookingRequestAndAddEntryToCalendar(BookingRequest bookingRequest,
                                                            OfficeHours officeHours,
                                                            Map<LocalDate, List<Booking>> bookingCalendar) {
        LocalDate meetingDate = bookingRequest.getMeetingStartTime()
                .toLocalDate();
        Booking booking = bookingRequestHelper
                .buildBookingFromRequest(bookingRequest);
        if (bookingCalendar.containsKey(meetingDate)) {
            if (isNotOverlapped(booking, bookingCalendar.get(meetingDate))
                    && isNotOutOfOfficeHours(booking, officeHours)) {
                bookingCalendar.get(meetingDate).add(booking);
            }
        } else {
            if (isNotOutOfOfficeHours(booking, officeHours)) {
                List<Booking> newBookings = Lists.newArrayList(booking);
                bookingCalendar.put(meetingDate, newBookings);
            }
        }
    }

    private void sortBookingRequests(List<BookingRequest> bookingRequests) {
        if (!CollectionUtils.isEmpty(bookingRequests)) {
            Collections.sort(bookingRequests, new Comparator<BookingRequest>() {
                @Override
                public int compare(final BookingRequest bookingRequest,
                                   final BookingRequest anotherBookingRequest) {
                    return bookingRequest.getSubmissionTime().compareTo(
                            anotherBookingRequest.getSubmissionTime());
                }
            });
        }
    }

    private boolean isNotOverlapped(Booking booking, List<Booking> bookingList) {
        for (Booking bookingInList : bookingList) {
            if (isOverlapped(booking, bookingInList)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverlapped(Booking booking, Booking anotherBooking) {
        Booking earlyBooking;
        Booking lateBooking;
        if (booking.getMeetingStartTime().isBefore(
                anotherBooking.getMeetingStartTime())) {
            earlyBooking = booking;
            lateBooking = anotherBooking;
        } else {
            earlyBooking = anotherBooking;
            lateBooking = booking;
        }
        return lateBooking.getMeetingStartTime().isBefore(
                earlyBooking.getMeetingEndTime());
    }

    private boolean isNotOutOfOfficeHours(Booking booking,
                                          OfficeHours officeHours) {
        return !(booking.getMeetingStartTime().isBefore(
                officeHours.getStartTime()) || booking.getMeetingEndTime()
                .isAfter(officeHours.getEndTime()));
    }
}
