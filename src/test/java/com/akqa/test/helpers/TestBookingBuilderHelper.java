package com.akqa.test.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.akqa.test.constants.TestCommonConstants;
import com.google.common.collect.Lists;

public class TestBookingBuilderHelper {

    public static BookingRequest bookingRequestBuilder(String employeeId,
            DateTime submissionTime, DateTime meetingStartTime,
            int meetingDuration) {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmployeeId(employeeId);
        bookingRequest.setSubmissionTime(submissionTime);
        bookingRequest.setMeetingStartTime(meetingStartTime);
        bookingRequest.setMeetingDuration(meetingDuration);
        return bookingRequest;
    }

    public static BookingRequest bookingRequestBuilder() {
        BookingRequest bookingRequest = bookingRequestBuilder(
                TestCommonConstants.BOOKING_REQUEST_EMPLOYEE_ID_EMP002,
                TestCommonConstants.BOOKING_REQUEST_SUBMISSION_DATE_AND_TIME_EMP002,
                TestCommonConstants.BOOKING_REQUEST_MEETING_START_DATE_AND_TIME_EMP002,
                TestCommonConstants.BOOKING_REQUEST_MEETING_DURATION_IN_HOURS_EMP002);
        return bookingRequest;
    }

    public static List<BookingRequest> bookingRequestsBuilder() {
        BookingRequest bookingRequestEMP001 = bookingRequestBuilder(
                TestCommonConstants.BOOKING_REQUEST_EMPLOYEE_ID_EMP001,
                TestCommonConstants.BOOKING_REQUEST_SUBMISSION_DATE_AND_TIME_EMP001,
                TestCommonConstants.BOOKING_REQUEST_MEETING_START_DATE_AND_TIME_EMP001,
                TestCommonConstants.BOOKING_REQUEST_MEETING_DURATION_IN_HOURS_EMP001);
        BookingRequest bookingRequestEMP002 = bookingRequestBuilder(
                TestCommonConstants.BOOKING_REQUEST_EMPLOYEE_ID_EMP002,
                TestCommonConstants.BOOKING_REQUEST_SUBMISSION_DATE_AND_TIME_EMP002,
                TestCommonConstants.BOOKING_REQUEST_MEETING_START_DATE_AND_TIME_EMP002,
                TestCommonConstants.BOOKING_REQUEST_MEETING_DURATION_IN_HOURS_EMP002);
        return Lists.newArrayList(bookingRequestEMP001, bookingRequestEMP002);
    }

    public static OfficeHours officeHoursBuilder() {
        OfficeHours officeHours = new OfficeHours();
        officeHours.setStartTime(TestCommonConstants.OFFICE_HOURS_START_TIME);
        officeHours.setEndTime(TestCommonConstants.OFFICE_HOURS_END_TIME);
        return officeHours;
    }

    public static Map<LocalDate, List<Booking>> bookingCalendarBuilder() {
        Map<LocalDate, List<Booking>> bookingCalendar = new HashMap<>();
        bookingCalendar.put(TestCommonConstants.CALENDAR_BOOKING_ENTRY_DATE,
                Lists.newArrayList(bookingBuilder()));
        return bookingCalendar;
    }

    public static Booking bookingBuilder() {
        Booking booking = new Booking();
        booking.setEmployeeId(TestCommonConstants.CALENDAR_BOOKING_ENTRY_EMPLOYEE_ID_EMP002);
        booking.setMeetingStartTime(TestCommonConstants.CALENDAR_BOOKING_ENTRY_START_TIME_EMP002);
        booking.setMeetingEndTime(TestCommonConstants.CALENDAR_BOOKING_ENTRY_END_TIME_EMP002);
        return booking;
    }
}
