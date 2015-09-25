package com.akqa.booking.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.joda.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Booking {
    private String employeeId;
    private LocalTime meetingStartTime;
    private LocalTime meetingEndTime;
}