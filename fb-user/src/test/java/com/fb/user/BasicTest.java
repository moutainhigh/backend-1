package com.fb.user;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author: pangminpeng
 * @create: 2020-07-18 16:05
 */
public class BasicTest {

    @Test
    public void testLocalTime() {
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void testLocalDate() {
        LocalDate localDate = LocalDate.parse("1996-12-16", ofPattern("yyyy-MM-dd"));
        System.out.println(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        LocalDate localDate1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(850665600000L), ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format( LocalDateTime.ofInstant(Instant.ofEpochMilli(1595343015021L), ZoneId.systemDefault())));
        LocalDateTime localDateTime1 = LocalDateTime.parse("1996-12-16", ofPattern("yyyy-MM-dd"));
        System.out.println(localDateTime1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
