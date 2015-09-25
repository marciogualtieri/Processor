package com.akqa.booking.components.helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.akqa.booking.entities.Booking;
import com.akqa.booking.entities.BookingRequest;
import com.akqa.booking.entities.OfficeHours;
import com.akqa.test.helpers.TestBookingBuilderHelper;

public class BookingProcessorHelperTest {

    private BookingRequestHelper bookingRequestHelper = new BookingRequestHelper();
    private BookingProcessorHelper bookingProcessorHelper = new BookingProcessorHelper(
            bookingRequestHelper);

    @Test
    public void whenIProcessBookingRequestsAndCreateCalendar_thenSuccess()
            throws Exception {
        OfficeHours officeHours = TestBookingBuilderHelper.officeHoursBuilder();
        List<BookingRequest> bookingRequests = TestBookingBuilderHelper
                .bookingRequestsBuilder();
        Map<LocalDate, List<Booking>> bookingCalendar = bookingProcessorHelper
                .processBookingRequestsAndCreateCalendar(bookingRequests,
                        officeHours);
        Map<LocalDate, List<Booking>> expectedBookingCalendar = TestBookingBuilderHelper
                .bookingCalendarBuilder();
        assertThat(bookingCalendar.entrySet(),
                equalTo(expectedBookingCalendar.entrySet()));
    }
}
