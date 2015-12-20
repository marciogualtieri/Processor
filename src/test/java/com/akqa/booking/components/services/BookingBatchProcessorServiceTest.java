package com.akqa.booking.components.services;

import com.akqa.booking.components.helpers.BookingCalendarHelper;
import com.akqa.booking.components.helpers.BookingProcessorHelper;
import com.akqa.booking.components.helpers.BookingRequestHelper;
import com.akqa.booking.exceptions.BookingException;
import com.akqa.test.constants.TestCommonConstants;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.batch.test.AssertFile.assertFileEquals;

public class BookingBatchProcessorServiceTest {

    private final BookingRequestHelper bookingRequestHelper = new BookingRequestHelper();
    private final BookingCalendarHelper bookingCalendarHelper = new BookingCalendarHelper();
    private final BookingProcessorHelper bookingProcessorHelper = new BookingProcessorHelper(
            bookingRequestHelper);
    private final BookingBatchProcessorService bookingBatchProcessor = new BookingBatchProcessorService(
            bookingRequestHelper, bookingCalendarHelper, bookingProcessorHelper);

    @After
    public void afterTest() throws IOException {
        FileUtils.fileDelete(TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Test
    public void whenIProcessBatchFile_thenSuccess() throws Exception {
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
            throws BookingException {
        when(bookingBatchProcessor).processBatchFile(
                TestCommonConstants.FILE_NAME_AND_NON_EXISTENT_PATH,
                TestCommonConstants.PROCESSOR_OUTPUT_FILE_NAME_AND_PATH);
        then(caughtException()).isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_FILE_ERROR_MESSAGE);
    }

    @Test
    public void whenIProcessBatchFileAndOutputDirectoryDoesNotExist_thenException()
            throws BookingException {
        when(bookingBatchProcessor).processBatchFile(
                TestCommonConstants.INPUT_FILE_NAME_AND_PATH,
                TestCommonConstants.FILE_NAME_AND_NON_EXISTENT_PATH);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_DIRECTORY_ERROR_MESSAGE);
    }

}
