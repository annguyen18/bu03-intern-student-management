package com.viettel.intern.ultis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.util.StringUtils;

public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_EXPORT_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter DATE_FORMAT_PATTERN = DateTimeFormatter
        .ofPattern(DATE_FORMAT);

    /**
     * Convert date to string
     *
     * @param localDate
     * @return
     */
    public static String convertDateToString(LocalDate localDate) {
        try {
            Date date = Date
                .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            return df.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param date
     * @param format
     * @return <pre>the value date followed by format</pre>
     */
    public static String convertDateToString(Date date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.now().format(formatter);
    }

    public static LocalDate getDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDate getDate(String date, String format) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime getDateTime(String date, String format) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, formatter);
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMAT_PATTERN);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        try {
            Date date = Date
                .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateFormat df = new SimpleDateFormat(DATE_EXPORT_FORMAT);
            return df.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert date to string with {@code format}
     *
     * @param date   T extends {@link TemporalAccessor}
     * @param format String
     * @return String
     */
    public static <T extends TemporalAccessor> String convertToString(T date, String format) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
            return df.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert date to full string dd/MM/yyyy hh24:mi:ss
     *
     * @param date T extends {@link TemporalAccessor}
     * @return String
     */

    public static <T extends TemporalAccessor> String convertToFullString(T date) {
        return convertToString(date, DATE_TIME_FORMAT);
    }

    public static String getCurrentDateFullString() {
        return convertDateToString(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
    }


    /**
     * Convert date to string dd/MM/yyyy hh24:mi:ss
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
            df.format(date);
            Calendar calendar = df.getCalendar();
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date newDate = calendar.getTime();
            return df.format(newDate);
        } catch (Exception e) {
            return "";
        }
    }
}
