package com.echarge.demo.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Date;

import static java.lang.String.format;

public class DateUtility {
    private DateUtility() {
    }

    private static final String[] VALID_DATE_FORMATS = new String[]{
            "uuuu/MM/dd", "uuuu/MM/dd HH:mm", "uuuu-MM-dd", "uuuu-MM-dd HH:mm"
    };

    private static DateTimeFormatter formatter;

    public static Date getDateIfValid(String date) throws IllegalArgumentException {
        for (String pattern : VALID_DATE_FORMATS) {
            try {
                formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                date = date.contains(":") ? date : date + " 00:00";

                // Assume entered values were passed after being transformed to UTC
                ZonedDateTime dateWithTime = LocalDateTime.parse(date, formatter).atZone(ZoneId.of("UTC"));
                return Date.from(dateWithTime.toInstant());
            } catch (Exception e) {
                // Try more
            }
        }
        throw new IllegalArgumentException(format("Date is not valid, valid formats are : %s ", Arrays.toString(VALID_DATE_FORMATS)));
    }
}