package com.excilys.formation.java.cdb.mappers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * Mapper class for date.
 * @author Laetitia Tureau
 */
public class DateMapper {

    /**
     * Convert a date into a Timestamp.
     * @throws ParseException when time format not valid.
     * @param line String to parse
     * @return the timestamp resulting
     */
    public static Timestamp nextTimestamp(String line) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ti = null;
        if (StringUtils.isNotBlank(line)) {
            String time = new StringBuilder().append(line).append(" 00:00:00").toString();
            if (time.toLowerCase().equals("null") || time.equals("")) {
                ti = null;
            } else {
                java.util.Date date = dateFormat.parse(time);
                ti = new Timestamp(date.getTime());
            }
        }
        return ti;
    }
}
