package com.example.utills;

import lombok.Generated;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@Generated
@UtilityClass
public class CommonPatterns {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

}
