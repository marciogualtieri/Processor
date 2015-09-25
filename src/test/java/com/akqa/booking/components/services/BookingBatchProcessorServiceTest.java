package com.akqa.booking.components.services;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
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
import com.akqa.booking.components.helpers.BookingProcessorHelper;
import com.akqa.booking.components.helpers.BookingRequestHelper;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.test.constants.TestCommonConstants;

public class BookingBatchProcessorServiceTest {

    private BookingRequestHelper bookingRequestHelper = new BookingRequestHelper();
    private BookingCalendarHelper bookingCalendarHelper = new BookingCalendarHelper();
    private BookingProcessorHelper bookingProcessorHelper = new BookingProcessorHelper(
            bookingRequestHelper);
    private final BookingBatchProcessorService bookingBatchProcessor = new BookingBatchProcessorService(
            bookingRequestHelper, bookingCalendarHelper, bookingProcessorHelper);

    @After
    public void afterTest() throws IOException {
        FileUtils.fileDelete(TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Test
    public void whenIProcessBatchFile_thenSuccess() throws BookingException,
            Exception {
        bookingBatchProcessor.processBatchFile(TestCommonConstants.INPUT_FILE_NAME_AND_PATH,
                TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
        File outputFile = new File(TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
        File expectedOutputFile = new File(
                TestCommonConstants.EXPECTED_PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
        assertThat(outputFile, is(not(nullValue())));
        assertFileEquals(expectedOutputFile, outputFile);
    }

    @Test
    public void whenIProcessBatchFileAndInputFileDoesNotExist_thenException()
            throws BookingException, Exception {
        when(bookingBatchProcessor).processBatchFile(
                TestCommonConstants.NON_EXISTENT_FILE_NAME_AND_PATH,
                TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
        then(caughtException()).isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_FILE_ERROR_MESSAGE);
    }

    @Test
    public void whenIProcessBatchFileAndOutputDirectoryDoesNotExist_thenException()
            throws BookingException, Exception {
        when(bookingBatchProcessor).processBatchFile(
                TestCommonConstants.INPUT_FILE_NAME_AND_PATH,
                TestCommonConstants.NON_EXISTENT_DIRECTORY);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_DIRECTORY_ERROR_MESSAGE);
    }

}
