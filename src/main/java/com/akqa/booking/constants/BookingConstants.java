package com.akqa.booking.constants;

import java.util.regex.Pattern;

public class BookingConstants {
    public static final String BOOKING_RECORD_LINE_REGEX = "(\\S+ ?\\S*) (\\S+)";
    public static final Pattern BOOKING_RECORD_LINE_PATTERN = Pattern
            .compile(BOOKING_RECORD_LINE_REGEX);
    public static final String OFFICE_HOURS_AS_STRING_REGEX = "(\\S+) (\\S+)";
    public static final Pattern OFFICE_HOURS_PATTERN = Pattern
            .compile(OFFICE_HOURS_AS_STRING_REGEX);
    public static final String SUBMISSION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MEETING_START_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final int SUBMISSION_DATE_AND_TIME_REGEX_GROUP_INDEX = 1;
    public static final int EMPLOYEE_ID_REGEX_GROUP_INDEX = 2;
    public static final int MEETING_START_DATE_AND_TIME_REGEX_GROUP_INDEX = 1;
    public static final int MEETING_DURATION_DATE_AND_TIME_REGEX_GROUP_INDEX = 2;
    public static final int OFFICE_HOURS_START_GROUP_INDEX = 1;
    public static final int OFFICE_HOURS_END_GROUP_INDEX = 2;
    public static final String OFFICE_HOURS_DATE_FORMAT = "HHmm";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final int OFFICE_HOURS_LINE_INDEX = 0;
    public static final int BOOKING_SUBMISSION_LINE_INDEX = 0;
    public static final int BOOKING_MEETING_LINE_INDEX = 1;
    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator");
    public static final boolean IS_APPEND_TO_FILE = true;
    public static final String OUTPUT_DATE_FORMAT = "HH:mm";

    private BookingConstants() {
    }
}