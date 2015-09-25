package com.akqa.booking.components.helpers;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.batch.test.AssertFile.assertFileEquals;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;

import com.akqa.booking.components.helpers.BookingCalendarHelper;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.test.constants.TestCommonConstants;
import com.akqa.test.helpers.TestBookingBuilderHelper;

public class BookingCalendarHelperTest {

    private final BookingCalendarHelper bookingCalendarHelper = new BookingCalendarHelper();

    @After
    public void afterTest() throws IOException {
        FileUtils.fileDelete(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendar_thenSuccess()
            throws Exception {
        bookingCalendarHelper.createOutputFileFromBookingCalendar(
                TestBookingBuilderHelper.bookingCalendarBuilder(),
                TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File outputFile = new File(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File expectedOutputFile = new File(
                TestCommonConstants.EXPECTED_HELPER_OUTPUT_FILE_NAME_AND_PATH);
        assertThat(outputFile, is(not(nullValue())));
        assertFileEquals(expectedOutputFile, outputFile);
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendarAndNullCalendar_thenSuccess()
            throws Exception {
        bookingCalendarHelper.createOutputFileFromBookingCalendar(null,
                TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File outputFile = new File(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        assertThat(outputFile.exists(), equalTo(false));
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendarAndOutputDirectoryDoesNotExist_thenException()
            throws Exception {
        when(bookingCalendarHelper).createOutputFileFromBookingCalendar(
                TestBookingBuilderHelper.bookingCalendarBuilder(),
                TestCommonConstants.NON_EXISTENT_DIRECTORY);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_DIRECTORY_ERROR_MESSAGE);
    }
}
