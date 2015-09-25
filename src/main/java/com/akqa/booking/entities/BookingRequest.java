package com.akqa.booking.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.joda.time.DateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookingRequest {
    private String employeeId;
    private DateTime submissionTime;
    private DateTime meetingStartTime;
    private int meetingDuration;
}