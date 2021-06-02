package com.excilys.formation.java.cdb.mappers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Mapper class for date.
 * @author Laetitia Tureau
 */
public class DateMapper {

    /**
     * Convert a date into a Timestamp.
     * @throws ParseException when time format not valid.
     * @param scanner A Scanner to retrieve the date
     * @return the timestamp resulting
     */
    public static Timestamp nextTimestamp(Scanner scanner) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = new StringBuilder().append(scanner.nextLine()).append(" 00:00:00").toString();
        Timestamp ti;
        if (time.toLowerCase().equals("null") || time.equals("")) {
            ti = null;
        } else {
            java.util.Date date = dateFormat.parse(time);
            ti = new Timestamp(date.getTime());
        }
        return ti;
    }
}
