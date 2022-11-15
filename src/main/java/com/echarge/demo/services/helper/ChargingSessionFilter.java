package com.echarge.demo.services.helper;

import lombok.Data;

import java.util.Date;

import static com.echarge.demo.utils.DateUtility.getDateIfValid;


@Data
public class ChargingSessionFilter {
    private static final Date MIN_DATE = new Date(Long.MIN_VALUE);
    private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
    private Date dateFrom = MIN_DATE;
    private Date dateTo = null;
    private String order;

    public ChargingSessionFilter(String thatDateFrom, String thatDateTo) throws IllegalAccessException {
        if (thatDateFrom != null)
            this.dateFrom = getDateIfValid(thatDateFrom);
        if (thatDateTo != null)
            this.dateTo = getDateIfValid(thatDateTo);
        if (this.dateTo != null && this.dateTo.compareTo(this.dateFrom) < 0)
            throw new IllegalAccessException("Provided dates are not valid, dateTo > dateFrom, please correct filter parameters");
        order = "desc";
    }
}