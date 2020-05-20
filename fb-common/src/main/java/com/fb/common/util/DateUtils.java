package com.fb.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtils {
    public static final DateTimeFormatter dateTimeFormatterMin = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter dateTimeFormatterSed = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final ZoneId zoneId = ZoneId.systemDefault();

    /**
     * 字符转日期
     * @param dateTime
     * @param dateTimeFormatter
     * @return
     */
    public static Date getDateFromLocalDateTime(String dateTime, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 日期转字符
     * @param dateTime
     * @param formatter
     * @return
     */
    public static String getDateFromLocalDateTime(Date dateTime, DateTimeFormatter formatter) {
        if (dateTime != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dateTime.toInstant(), zoneId);
            String date = formatter.format(localDateTime);
            return date;
        }
        return "";
    }

}
