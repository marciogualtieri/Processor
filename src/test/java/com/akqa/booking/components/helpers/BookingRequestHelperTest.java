package com.akqa.booking.components.helpers;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;

import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.test.constants.TestCommonConstants;
import com.akqa.test.helpers.TestBookingBuilderHelper;

public class BookingRequestHelperTest {

    private final BookingRequest expectedBookingRequest = TestBookingBuilderHelper
            .bookingRequestBuilder();
    private final OfficeHours expectedOfficeHours = TestBookingBuilderHelper
            .officeHoursBuilder();
    private final BookingRequestHelper bookingRequestHelper = new BookingRequestHelper();

    @After
    public void afterTest() throws IOException {
        FileUtils.fileDelete(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Test
    public void whenIParseAValidBookingRequestString_thenSuccess()
            throws BookingException {
        BookingRequest bookingRequest = bookingRequestHelper
                .parseBookingRequestText(TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP002);
        assertThat(bookingRequest, equalTo(expectedBookingRequest));
    }

    @Test
    public void whenIParseABookingRequestStringAndInvalidSubmissionDateAndTime_thenException()
            throws BookingException {
        when(bookingRequestHelper)
                .parseBookingRequestText(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_SUBMISSION_DATE_AND_TIME);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_DATE_AND_TIME_ERROR_MESSAGE);
    }

    @Test
    public void whenIParseABookingRequestStringAndInvalidMeetingDateAndTime_thenException()
            throws BookingException {
        when(bookingRequestHelper)
                .parseBookingRequestText(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_WITH_INVALID_MEETING_DATE_AND_TIME);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_DATE_AND_TIME_ERROR_MESSAGE);
    }

    @Test
    public void whenIParseABookingRequestStringAndInvalidMeetingDuration_thenException()
            throws BookingException {
        when(bookingRequestHelper)
                .parseBookingRequestText(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_MEETING_DURATION);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_MEETING_DURATION_ERROR_MESSAGE);
    }

    @Test
    public void whenIParseValidOfficeHours_thenSuccess()
            throws BookingException {
        OfficeHours officeHours = bookingRequestHelper
                .parseOfficeHoursText(TestCommonConstants.OFFICE_HOURS_AS_STRING);
        assertThat(officeHours, equalTo(expectedOfficeHours));
    }

    @Test
    public void whenIParseOfficeHoursStringAndInvalidOfficeHoursStart_thenException()
            throws BookingException {
        when(bookingRequestHelper)
                .parseOfficeHoursText(
                        TestCommonConstants.OFFICE_HOURS_AS_STRING_WITH_INVALID_START_TIME);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.OFFICE_HOURS_AS_STRING_WITH_INVALID_TIME_ERROR_MESSAGE);
    }

    @Test
    public void whenIParseOfficeHoursStringAndInvalidOfficeHoursEnd_thenException()
            throws BookingException {
        when(bookingRequestHelper)
                .parseOfficeHoursText(
                        TestCommonConstants.OFFICE_HOURS_AS_STRING_WITH_INVALID_END_TIME);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.OFFICE_HOURS_AS_STRING_WITH_INVALID_TIME_ERROR_MESSAGE);
    }

    @Test
    public void whenICreateABookingFromABookingRequest_thenSuccess() {
        BookingRequest bookingRequest = TestBookingBuilderHelper
                .bookingRequestBuilder();
        Booking booking = bookingRequestHelper
                .buildBookingFromRequest(bookingRequest);
        Booking expectedBooking = TestBookingBuilderHelper.bookingBuilder();
        assertThat(booking, equalTo(expectedBooking));
    }

}
