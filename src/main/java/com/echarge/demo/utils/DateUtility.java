package com.echarge.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

public class DateUtility {
    private DateUtility() {
    }

    private static final String VALID_DATE_FORMATS = "yyyy/MM/dd; yyyy/MM/dd' 'HH:mm; yyyy-MM-dd; yyyy-MM-dd' 'HH:mm";
    private static final List<SimpleDateFormat> allowedPatterns = new ArrayList<SimpleDateFormat>(Arrays.asList(
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("yyyy/MM/dd' 'HH:mm"),
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy-MM-dd' 'HH:mm")
    ));

    public static Date getDateIfValid(String date) throws IllegalArgumentException {
        for (SimpleDateFormat pattern : allowedPatterns) {
            try {
                return new Date(pattern.parse(date).getTime());
            } catch (ParseException pe) {
                //Try more
            }
        }
        throw new IllegalArgumentException(format("Date is not valid, valid formats are : %s ", VALID_DATE_FORMATS));
    }
}