package com.akqa.test.constants;

import com.akqa.booking.messages.BookingMessages;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;

public class TestCommonConstants {
    public static final String BOOKING_REQUEST_EMPLOYEE_ID_EMP001 = "EMP001";
    public static final DateTime BOOKING_REQUEST_SUBMISSION_DATE_AND_TIME_EMP001 = new DateTime(
            2011, 3, 17, 10, 17, 6, 0);
    public static final DateTime BOOKING_REQUEST_MEETING_START_DATE_AND_TIME_EMP001 = new DateTime(
            2011, 3, 21, 9, 0, 0, 0);
    public static final int BOOKING_REQUEST_MEETING_DURATION_IN_HOURS_EMP001 = 2;
    private static final String BOOKING_REQUEST_SUBMISSION_LINE_EMP001 = "2011-03-17 10:17:06 EMP001";
    private static final String BOOKING_REQUEST_MEETING_LINE_EMP001 = "2011-03-21 09:00 2";
    public static final List<String> BOOKING_REQUEST_AS_TEXT_EMP001 = Lists
            .newArrayList(BOOKING_REQUEST_SUBMISSION_LINE_EMP001,
                    BOOKING_REQUEST_MEETING_LINE_EMP001);
    public static final List<String> BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_SUBMISSION_DATE_AND_TIME = Lists
            .newArrayList("invalid-date invalid-time EMP001",
                    BOOKING_REQUEST_MEETING_LINE_EMP001);
    public static final String BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_DATE_AND_TIME_ERROR_MESSAGE = "IllegalArgumentException: Invalid format: \"invalid-date invalid-time\"";
    public static final List<String> BOOKING_REQUEST_AS_TEXT_WITH_INVALID_MEETING_DATE_AND_TIME = Lists
            .newArrayList(BOOKING_REQUEST_SUBMISSION_LINE_EMP001,
                    "invalid-date invalid-time 2");
    public static final List<String> BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_MEETING_DURATION = Lists
            .newArrayList(BOOKING_REQUEST_SUBMISSION_LINE_EMP001,
                    "2011-03-21 09:00 not-a-number");
    public static final String BOOKING_REQUEST_AS_TEXT_EMP001_WITH_INVALID_MEETING_DURATION_ERROR_MESSAGE = "NumberFormatException: For input string: \"not-a-number\"";
    public static final String OFFICE_HOURS_AS_STRING = "0900 1730";
    public static final String OFFICE_HOURS_AS_STRING_WITH_INVALID_START_TIME = "invalid-time 1730";
    public static final String OFFICE_HOURS_AS_STRING_WITH_INVALID_END_TIME = "0900 invalid-time";
    public static final LocalTime OFFICE_HOURS_START_TIME = new LocalTime(9, 0);
    public static final LocalTime OFFICE_HOURS_END_TIME = new LocalTime(17, 30);
    public static final String OFFICE_HOURS_AS_STRING_WITH_INVALID_TIME_ERROR_MESSAGE = "IllegalArgumentException: Invalid format: \"invalid-time\"";
    public static final String INPUT_FILE_NAME_AND_PATH = "src/test/resources/input.txt";
    public static final String EXPECTED_PROCESSOR_OUTPUT_FILE_NAME_AND_PATH = "src/test/resources/expected_processor_output.txt";
    public static final String EXPECTED_HELPER_OUTPUT_FILE_NAME_AND_PATH = "src/test/resources/expected_helper_output.txt";
    public static final String PROCESSOR_OUTPUT_FILE_NAME_AND_PATH = "target/processor_output.txt";
    public static final String HELPER_OUTPUT_FILE_NAME_AND_PATH = "target/helper_output.txt";
    public static final String NON_EXISTENT_DIRECTORY = FilenameUtils
            .separatorsToSystem("/a/non/existent/path");
    public static final String FILE_NAME_AND_NON_EXISTENT_PATH = FilenameUtils
            .separatorsToSystem(NON_EXISTENT_DIRECTORY + "/file");
    public static final String NON_EXISTENT_DIRECTORY_ERROR_MESSAGE = String
            .format(BookingMessages.BOOKING_OUTPUT_DIRECTORY_EXCEPTION_MESSAGE_FORMAT, NON_EXISTENT_DIRECTORY);
    public static final String NON_EXISTENT_FILE_ERROR_MESSAGE = String.format(
            BookingMessages.BOOKING_BATCH_FILE_LOADING_EXCEPTION_MESSAGE_FORMAT, FILE_NAME_AND_NON_EXISTENT_PATH);
    public static final LocalDate CALENDAR_BOOKING_ENTRY_DATE = new LocalDate(
            2011, 3, 21);
    public static final LocalTime CALENDAR_BOOKING_ENTRY_START_TIME_EMP001 = new LocalTime(
            9, 0);
    public static final LocalTime CALENDAR_BOOKING_ENTRY_END_TIME_EMP001 = new LocalTime(
            11, 0);
    public static final String CALENDAR_BOOKING_ENTRY_EMPLOYEE_ID_EMP001 = BOOKING_REQUEST_EMPLOYEE_ID_EMP001;

    public static final String BOOKING_REQUEST_EMPLOYEE_ID_EMP002 = "EMP002";
    public static final DateTime BOOKING_REQUEST_SUBMISSION_DATE_AND_TIME_EMP002 = new DateTime(
            2011, 3, 16, 12, 34, 56, 0);
    public static final DateTime BOOKING_REQUEST_MEETING_START_DATE_AND_TIME_EMP002 = new DateTime(
            2011, 3, 21, 9, 0, 0, 0);
    public static final int BOOKING_REQUEST_MEETING_DURATION_IN_HOURS_EMP002 = 3;
    private static final String BOOKING_REQUEST_SUBMISSION_LINE_EMP002 = "2011-03-16 12:34:56 EMP002";
    private static final String BOOKING_REQUEST_MEETING_LINE_EMP002 = "2011-03-21 09:00 3";
    public static final List<String> BOOKING_REQUEST_AS_TEXT_EMP002 = Lists
            .newArrayList(BOOKING_REQUEST_SUBMISSION_LINE_EMP002,
                    BOOKING_REQUEST_MEETING_LINE_EMP002);
    public static final String CALENDAR_BOOKING_ENTRY_EMPLOYEE_ID_EMP002 = BOOKING_REQUEST_EMPLOYEE_ID_EMP002;
    public static final LocalTime CALENDAR_BOOKING_ENTRY_START_TIME_EMP002 = new LocalTime(
            9, 0);
    public static final LocalTime CALENDAR_BOOKING_ENTRY_END_TIME_EMP002 = new LocalTime(
            12, 0);

    private TestCommonConstants() {
    }
}