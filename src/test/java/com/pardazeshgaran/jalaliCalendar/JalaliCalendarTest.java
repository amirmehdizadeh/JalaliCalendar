package com.pardazeshgaran.jalaliCalendar;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Amir
 * Date: 6/27/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */

public class JalaliCalendarTest {

    @Test
    public void dayOfWeekTest() {

        Assert.assertEquals(JalaliCalendar.dayOfWeek(new YearMonthDate(2012, 6, 30)), 2);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(new YearMonthDate(2012, 7, 1)), 4);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(new YearMonthDate(2010, 7, 20)), 6);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(new YearMonthDate(2008, 3, 23)), 4);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(new YearMonthDate(2011, 10, 14)), 2);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1391, 3, 10))), 7);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1389, 4, 31))), 1);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1390, 11, 27))), 7);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1391, 1, 1))), 6);
        Assert.assertEquals(JalaliCalendar.dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1382, 7, 22))), 5);

    }

    @Test
    public void weekOfYearTest() {

        Assert.assertEquals(JalaliCalendar.weekOfYear(103, 1391), 16);
        Assert.assertEquals(JalaliCalendar.weekOfYear(229, 1391), 34);
        Assert.assertEquals(JalaliCalendar.weekOfYear(228, 1391), 33);
        Assert.assertEquals(JalaliCalendar.weekOfYear(100, 1389), 15);
        Assert.assertEquals(JalaliCalendar.weekOfYear(235, 1382), 35);

    }

    @Test
    public void weekOfMonthTest() {

        Assert.assertEquals(new JalaliCalendar(1390, 5, 6).get(Calendar.WEEK_OF_MONTH), 2);
        Assert.assertEquals(new JalaliCalendar(1388, 10, 10).get(Calendar.WEEK_OF_MONTH), 3);
        Assert.assertEquals(new JalaliCalendar(1386, 2, 19).get(Calendar.WEEK_OF_MONTH), 4);
        Assert.assertEquals(new JalaliCalendar(1386, 2, 18).get(Calendar.WEEK_OF_MONTH), 3);
        Assert.assertEquals(new JalaliCalendar(1391, 11, 30).get(Calendar.WEEK_OF_MONTH), 5);
        Assert.assertEquals(new JalaliCalendar(1391, 9, 30).get(Calendar.WEEK_OF_MONTH), 6);

    }

    @Test
    public void dayOfYear() {

        Assert.assertEquals(new JalaliCalendar(1391, 11, 30).get(Calendar.DAY_OF_YEAR), 366);
        Assert.assertEquals(new JalaliCalendar(1391, 10, 3).get(Calendar.DAY_OF_YEAR), 309);
        Assert.assertEquals(new JalaliCalendar(1391, 8, 15).get(Calendar.DAY_OF_YEAR), 261);
        Assert.assertEquals(new JalaliCalendar(1391, 5, 22).get(Calendar.DAY_OF_YEAR), 177);
        Assert.assertEquals(new JalaliCalendar(1391, 0, 21).get(Calendar.DAY_OF_YEAR), 21);

    }

    @Test
    public void dayOfWeekInMonth() {

        Assert.assertEquals(new JalaliCalendar(1391, 11, 30).get(Calendar.DAY_OF_WEEK_IN_MONTH), 5);
        Assert.assertEquals(new JalaliCalendar(1391, 10, 3).get(Calendar.DAY_OF_WEEK_IN_MONTH), 1);
        Assert.assertEquals(new JalaliCalendar(1391, 8, 15).get(Calendar.DAY_OF_WEEK_IN_MONTH), 3);
        Assert.assertEquals(new JalaliCalendar(1391, 5, 22).get(Calendar.DAY_OF_WEEK_IN_MONTH), 4);
        Assert.assertEquals(new JalaliCalendar(1391, 0, 21).get(Calendar.DAY_OF_WEEK_IN_MONTH), 3);

    }

    @Test
    public void jalaliToGregorian() {

        Assert.assertEquals(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1369, 11, 14)).toString(), "1991 2 5");
        Assert.assertEquals(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1388, 3, 20)).toString(), "2009 6 11");
        Assert.assertEquals(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1387, 7, 9)).toString(), "2008 9 30");
        Assert.assertEquals(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1386, 0, 5)).toString(), "2007 2 25");
        Assert.assertEquals(JalaliCalendar.jalaliToGregorian(new YearMonthDate(1385, 4, 18)).toString(), "2006 7 9");
    }

    @Test
    public void gregorianToJalali() {

        Assert.assertEquals(JalaliCalendar.gregorianToJalali(new YearMonthDate(1991, 2, 5)).toString(), "1369 11 14");
        Assert.assertEquals(JalaliCalendar.gregorianToJalali(new YearMonthDate(1994, 8, 24)).toString(), "1373 6 2");
        Assert.assertEquals(JalaliCalendar.gregorianToJalali(new YearMonthDate(1996, 5, 24)).toString(), "1375 3 4");
        Assert.assertEquals(JalaliCalendar.gregorianToJalali(new YearMonthDate(2001, 8, 20)).toString(), "1380 5 29");
        Assert.assertEquals(JalaliCalendar.gregorianToJalali(new YearMonthDate(2013, 2, 20)).toString(), "1391 11 30");

    }

    @Test
    public void addTest() {

        JalaliCalendar jalaliCalendar = new JalaliCalendar(1390, 0, 1, 15, 30, 30);
        jalaliCalendar.add(Calendar.YEAR, 2);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1392);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 0);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 1);
        jalaliCalendar.add(Calendar.MONTH, 20);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 1);
        jalaliCalendar.add(Calendar.DATE, 200);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1394);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 2);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 20);
        jalaliCalendar.add(Calendar.DAY_OF_YEAR, -200);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 1);
        jalaliCalendar.add(Calendar.DAY_OF_WEEK_IN_MONTH, 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 11);
        jalaliCalendar.add(Calendar.WEEK_OF_MONTH, -10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 1);
        jalaliCalendar.add(Calendar.DAY_OF_WEEK, 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 15);
        jalaliCalendar.add(Calendar.HOUR, 100);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 15);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 7);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 19);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MINUTE), 30);
        Assert.assertEquals(jalaliCalendar.get(Calendar.SECOND), 30);
        jalaliCalendar.add(Calendar.MINUTE, -1820);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1393);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DATE), 14);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 1);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 13);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MINUTE), 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.SECOND), 30);

    }

    @Test
    public void getMinGetMaxTest() {

        Assert.assertEquals(JalaliCalendar.getInstance().getMinimum(Calendar.MONTH), 0);
        Assert.assertEquals(new JalaliCalendar().getMaximum(Calendar.HOUR), 11);
        Assert.assertEquals(new JalaliCalendar().getMaximum(Calendar.MONTH), 11);

    }

    @Test
    public void rollTest() {

        JalaliCalendar jalaliCalendar = new JalaliCalendar(1391, 4, 3, 17, 5, 45);
        jalaliCalendar.roll(Calendar.YEAR, 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1401);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 4);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 3);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 17);
        Assert.assertEquals(jalaliCalendar.get(Calendar.WEEK_OF_YEAR), 19);
        jalaliCalendar = new JalaliCalendar(1391, 11, 30);
        jalaliCalendar.roll(Calendar.YEAR, 1);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1392);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 29);
        Assert.assertEquals(jalaliCalendar.get(Calendar.WEEK_OF_YEAR), 53);
        jalaliCalendar = new JalaliCalendar(1391, 11, 30);
        jalaliCalendar.roll(Calendar.YEAR, 4);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1395);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 30);
        jalaliCalendar = new JalaliCalendar(1391, 5, 20);
        jalaliCalendar.roll(Calendar.MONTH, 4);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1391);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 9);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 20);
        jalaliCalendar = new JalaliCalendar(1391, 2, 31);
        jalaliCalendar.roll(Calendar.MONTH, 18);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1391);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 8);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 30);
        jalaliCalendar = new JalaliCalendar(1391, 2, 31);
        jalaliCalendar.roll(Calendar.MONTH, 9);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1391);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 30);
        jalaliCalendar = new JalaliCalendar(1390, 2, 31);
        jalaliCalendar.roll(Calendar.MONTH, 9);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1390);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 29);
        jalaliCalendar = new JalaliCalendar(1390, 2, 12);
        jalaliCalendar.roll(Calendar.DAY_OF_MONTH, 9);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1390);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 2);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 21);
        jalaliCalendar.roll(Calendar.DAY_OF_MONTH, 52);
        Assert.assertEquals(jalaliCalendar.get(Calendar.YEAR), 1390);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MONTH), 2);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 11);
        jalaliCalendar = new JalaliCalendar(1391, 3, 19, 22, 12, 30);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), 1);
        jalaliCalendar.roll(Calendar.AM_PM, 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), 0);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 19);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MINUTE), 12);
        Assert.assertEquals(jalaliCalendar.get(Calendar.SECOND), 30);
        jalaliCalendar.roll(Calendar.AM_PM, 111);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), 1);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 22);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 10);
        Assert.assertEquals(jalaliCalendar.get(Calendar.DAY_OF_MONTH), 19);
        Assert.assertEquals(jalaliCalendar.get(Calendar.MINUTE), 12);
        Assert.assertEquals(jalaliCalendar.get(Calendar.SECOND), 30);
        jalaliCalendar = new JalaliCalendar(1391, 3, 19, 22, 12, 30);
        jalaliCalendar.roll(Calendar.HOUR, 29);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 3);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 15);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), Calendar.PM);
        jalaliCalendar = new JalaliCalendar(1391, 3, 19, 5, 12, 30);
        jalaliCalendar.roll(Calendar.HOUR, 7);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 0);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 0);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), Calendar.AM);
        jalaliCalendar = new JalaliCalendar(1391, 3, 19, 22, 12, 30);
        jalaliCalendar.roll(Calendar.HOUR_OF_DAY, 29);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 3);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 3);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), Calendar.AM);
        jalaliCalendar = new JalaliCalendar(1391, 3, 19, 22, 12, 30);
        jalaliCalendar.roll(Calendar.HOUR_OF_DAY, 13);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.HOUR_OF_DAY), 11);
        Assert.assertEquals(jalaliCalendar.get(Calendar.AM_PM), Calendar.AM);


    }

    @Test
    public void after_beforeTest() {

        Calendar calendar = new JalaliCalendar(1389, 9, 12);
        Calendar calendar1 = new JalaliCalendar(1389, 9, 13);
        Assert.assertEquals(calendar.after(calendar1), false);
        Assert.assertEquals(calendar1.after(calendar), true);
        Assert.assertEquals(calendar.before(calendar1), true);
        Assert.assertEquals(calendar1.before(calendar), false);
        calendar.set(1300, 2, 30);
        calendar1.set(1467, 3, 4);
        Assert.assertEquals(calendar.after(calendar1), false);
        Assert.assertEquals(calendar1.after(calendar), true);
        Assert.assertEquals(calendar.before(calendar1), true);
        Assert.assertEquals(calendar1.before(calendar), false);

    }

}
