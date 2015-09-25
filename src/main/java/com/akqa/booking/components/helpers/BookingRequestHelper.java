package com.akqa.booking.components.helpers;

import java.util.List;
import java.util.regex.Matcher;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.akqa.booking.constants.BookingConstants;
import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.booking.messages.BookingMessages;

@Component
public class BookingRequestHelper {

    public BookingRequest parseBookingRequestText(
            List<String> bookingRequestAsText) throws BookingException {
        BookingRequest bookingRequest = null;
        try {
            bookingRequest = bookingRequestBuilder(bookingRequestAsText);
        } catch (Exception e) {
            String message = String
                    .format(BookingMessages.BOOKING_REQUEST_PARSING_EXCEPTION_MESSAGE_FORMAT,
                            bookingRequestAsText);
            throw new BookingException(message, e);
        }
        return bookingRequest;
    }

    public OfficeHours parseOfficeHoursText(String officeHoursAsText)
            throws BookingException {
        OfficeHours officeHours = null;
        Matcher matcher = BookingConstants.OFFICE_HOURS_PATTERN
                .matcher(officeHoursAsText);
        try {
            matcher.find();
            officeHours = officeHoursBuilder(matcher);
        } catch (Exception e) {
            String message = String
                    .format(BookingMessages.OFFICE_HOURS_PARSING_EXCEPTION_MESSAGE_FORMAT,
                            officeHoursAsText);
            throw new BookingException(message, e);
        }
        return officeHours;
    }

    public Booking createBookingFromBookingRequest(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        LocalTime meetingStartTime = bookingRequest.getMeetingStartTime()
                .toLocalTime();
        LocalTime meetingEndTime = meetingStartTime.plusHours(bookingRequest
                .getMeetingDuration());
        booking.setEmployeeId(bookingRequest.getEmployeeId());
        booking.setMeetingStartTime(meetingStartTime);
        booking.setMeetingEndTime(meetingEndTime);
        return booking;
    }

    private BookingRequest bookingRequestBuilder(
            List<String> bookingRequestAsText) throws BookingException {
        BookingRequest bookingRequest = new BookingRequest();
        updateBookingRequestSubmissionData(bookingRequest,
                bookingRequestAsText
                        .get(BookingConstants.BOOKING_SUBMISSION_LINE_INDEX));
        updateBookingRequestMeetingData(bookingRequest,
                bookingRequestAsText
                        .get(BookingConstants.BOOKING_MEETING_LINE_INDEX));
        return bookingRequest;
    }

    private void updateBookingRequestSubmissionData(
            BookingRequest bookingRequest, String BookingRequestSubmissionLine) {
        Matcher matcher = BookingConstants.BOOKING_RECORD_LINE_PATTERN
                .matcher(BookingRequestSubmissionLine);
        matcher.find();
        bookingRequest.setEmployeeId(parseEmployeeId(matcher));
        bookingRequest
                .setSubmissionTime(parseSubmissionDateAndTimeString(matcher));
    }

    private void updateBookingRequestMeetingData(BookingRequest bookingRequest,
            String BookingRequestMeetingLine) {
        Matcher matcher = BookingConstants.BOOKING_RECORD_LINE_PATTERN
                .matcher(BookingRequestMeetingLine);
        matcher.find();
        bookingRequest
                .setMeetingStartTime(parseMeetingStartDateAndTimeString(matcher));
        bookingRequest.setMeetingDuration(parseMeetingDuration(matcher));
    }

    private OfficeHours officeHoursBuilder(Matcher matcher) {
        OfficeHours officeHours = new OfficeHours();
        officeHours.setStartTime(parseOfficeHoursStartString(matcher));
        officeHours.setEndTime(parseOfficeHoursEndString(matcher));
        return officeHours;
    }

    private LocalTime parseOfficeHoursStartString(Matcher matcher) {
        String officeHoursStartDateAndTime = matcher
                .group(BookingConstants.OFFICE_HOURS_START_GROUP_INDEX);
        return parseTimeString(officeHoursStartDateAndTime,
                BookingConstants.OFFICE_HOURS_DATE_FORMAT);
    }

    private LocalTime parseOfficeHoursEndString(Matcher matcher) {
        String officeHoursEndDateAndTime = matcher
                .group(BookingConstants.OFFICE_HOURS_END_GROUP_INDEX);
        return parseTimeString(officeHoursEndDateAndTime,
                BookingConstants.OFFICE_HOURS_DATE_FORMAT);
    }

    private int parseMeetingDuration(Matcher matcher) {
        String meetingDuration = matcher
                .group(BookingConstants.MEETING_DURATION_DATE_AND_TIME_REGEX_GROUP_INDEX);
        return Integer.parseInt(meetingDuration);
    }

    private String parseEmployeeId(Matcher matcher) {
        String employeeId = matcher
                .group(BookingConstants.EMPLOYEE_ID_REGEX_GROUP_INDEX);
        return employeeId;
    }

    private DateTime parseDateAndTimeString(String dateAndTimeAsString,
            String datePattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat
                .forPattern(datePattern);
        DateTime dateTime = dateTimeFormatter
                .parseDateTime(dateAndTimeAsString);
        return dateTime;
    }

    private LocalTime parseTimeString(String timeAsString, String timePattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat
                .forPattern(timePattern);
        LocalTime localTime = dateTimeFormatter.parseLocalTime(timeAsString);
        return localTime;
    }

    private DateTime parseSubmissionDateAndTimeString(Matcher matcher) {
        String submissionDateAndTime = matcher
                .group(BookingConstants.SUBMISSION_DATE_AND_TIME_REGEX_GROUP_INDEX);
        return parseDateAndTimeString(submissionDateAndTime,
                BookingConstants.SUBMISSION_DATE_FORMAT);
    }

    private DateTime parseMeetingStartDateAndTimeString(Matcher matcher) {
        String meetingStartDateAndTime = matcher
                .group(BookingConstants.MEETING_START_DATE_AND_TIME_REGEX_GROUP_INDEX);
        return parseDateAndTimeString(meetingStartDateAndTime,
                BookingConstants.MEETING_START_DATE_FORMAT);
    }
}
