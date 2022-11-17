package com.echarge.demo;

import com.echarge.demo.utils.DateUtility;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.echarge.demo.utils.DateUtility.getDateIfValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilityTest {

    @Test
    void dateInFormatOtherThanKnownIsNotAccepted() {
        assertThrows(IllegalArgumentException.class, () -> DateUtility.getDateIfValid("2000-14-01 14:30"));
    }

    @Test
    void invalidDateIsNotAccepted() {
        assertThrows(IllegalArgumentException.class, () -> DateUtility.getDateIfValid("2000-02-30 14:30"));
    }

    @Test
    void wrongInputIsNotAccepted() {
        assertThrows(IllegalArgumentException.class, () -> DateUtility.getDateIfValid("sdh"));
    }

    @Test
    void datesConvertedCorrectlyFromAllFormats() {
        Date date1 = getDateIfValid("2000/09/20");
        Date date2 = getDateIfValid("2000/09/20 00:00");
        Date date3 = getDateIfValid("2000-09-20");
        Date date4 = getDateIfValid("2000-09-20 00:00");

        Date[] dates = new Date[]{date1, date2, date3, date4};
        Set<Date> uniqueDate = Arrays.stream(dates).collect(Collectors.toSet());
        assertEquals(1, uniqueDate.size());
    }
}
