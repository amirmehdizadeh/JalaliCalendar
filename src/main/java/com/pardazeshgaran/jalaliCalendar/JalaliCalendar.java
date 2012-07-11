package com.pardazeshgaran.jalalicalendar;

import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.Gregorian;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Amir
 * Date: 6/25/12
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */

public class JalaliCalendar extends Calendar {
    public static int gregorianDaysInMonth[] = {31, 28, 31, 30, 31,
            30, 31, 31, 30, 31, 30, 31};
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31, 31,
            30, 30, 30, 30, 30, 29};

    public final static int FARVARDIN = 0;
    public final static int ORDIBEHESHT = 1;
    public final static int KHORDAD = 2;
    public final static int TIR = 3;
    public final static int MORDAD = 4;
    public final static int SHAHRIVAR = 5;
    public final static int MEHR = 6;
    public final static int ABAN = 7;
    public final static int AZAR = 8;
    public final static int DEY = 9;
    public final static int BAHMAN = 10;
    public final static int ESFAND = 11;
    private transient BaseCalendar.Date gdate;
    private static final Gregorian gcal =
            CalendarSystem.getGregorianCalendar();
    private static TimeZone timeZone = TimeZone.getDefault();
    private static boolean isTimeSeted = false;
    private transient BaseCalendar baseCalendar;

    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60 * ONE_SECOND;
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;
    static final int BCE = 0;
    static final int CE = 1;
    public static final int AD = 1;
    GregorianCalendar cal;

    static final int MIN_VALUES[] = {
            BCE,        // ERA
            1,        // YEAR
            FARVARDIN,    // MONTH
            1,        // WEEK_OF_YEAR
            0,        // WEEK_OF_MONTH
            1,        // DAY_OF_MONTH
            1,        // DAY_OF_YEAR
            SATURDAY,        // DAY_OF_WEEK
            1,        // DAY_OF_WEEK_IN_MONTH
            AM,        // AM_PM
            0,        // HOUR
            0,        // HOUR_OF_DAY
            0,        // MINUTE
            0,        // SECOND
            0,        // MILLISECOND
            -13 * ONE_HOUR,    // ZONE_OFFSET (UNIX compatibility)
            0        // DST_OFFSET
    };

    static final int LEAST_MAX_VALUES[] = {
            CE,        // ERA
            292269054,    // YEAR
            ESFAND,    // MONTH
            52,        // WEEK_OF_YEAR
            4,        // WEEK_OF_MONTH
            28,        // DAY_OF_MONTH
            365,        // DAY_OF_YEAR
            FRIDAY,    // DAY_OF_WEEK
            4,        // DAY_OF_WEEK_IN
            PM,        // AM_PM
            11,        // HOUR
            23,        // HOUR_OF_DAY
            59,        // MINUTE
            59,        // SECOND
            999,        // MILLISECOND
            14 * ONE_HOUR,    // ZONE_OFFSET
            20 * ONE_MINUTE    // DST_OFFSET (historical least maximum)
    };

    static final int MAX_VALUES[] = {
            CE,        // ERA
            292278994,    // YEAR
            ESFAND,    // MONTH
            53,        // WEEK_OF_YEAR
            6,        // WEEK_OF_MONTH
            31,        // DAY_OF_MONTH
            366,        // DAY_OF_YEAR
            FRIDAY,    // DAY_OF_WEEK
            6,        // DAY_OF_WEEK_IN
            PM,        // AM_PM
            11,        // HOUR
            23,        // HOUR_OF_DAY
            59,        // MINUTE
            59,        // SECOND
            999,        // MILLISECOND
            14 * ONE_HOUR,    // ZONE_OFFSET
            2 * ONE_HOUR    // DST_OFFSET (double summer time)
    };


    public JalaliCalendar() {
        this(TimeZone.getDefault(), Locale.getDefault());
        //setZoneShared(true);
    }

    public JalaliCalendar(TimeZone zone) {
        this(zone, Locale.getDefault());
    }

    public JalaliCalendar(Locale aLocale) {
        this(TimeZone.getDefault(), aLocale);
        //setZoneShared(true);
    }

    public JalaliCalendar(TimeZone zone, Locale aLocale) {

        super(zone, aLocale);
        timeZone = zone;
        Calendar calendar = Calendar.getInstance(zone, aLocale);

        YearMonthDate yearMonthDate = new YearMonthDate(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE));
        yearMonthDate = gregorianToJalali(yearMonthDate);
        this.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate());
        complete();

    }

    public JalaliCalendar(int year, int month, int dayOfMonth) {
        this(year, month, dayOfMonth, 0, 0, 0, 0);
    }

    public JalaliCalendar(int year, int month, int dayOfMonth, int hourOfDay,
                          int minute) {
        this(year, month, dayOfMonth, hourOfDay, minute, 0, 0);
    }

    public JalaliCalendar(int year, int month, int dayOfMonth, int hourOfDay,
                          int minute, int second) {
        this(year, month, dayOfMonth, hourOfDay, minute, second, 0);
    }

    JalaliCalendar(int year, int month, int dayOfMonth,
                   int hourOfDay, int minute, int second, int millis) {
        super();


        this.set(YEAR, year);
        this.set(MONTH, month);
        this.set(DAY_OF_MONTH, dayOfMonth);

        // Set AM_PM and HOUR here to set their stamp values before
        // setting HOUR_OF_DAY (6178071).
        if (hourOfDay >= 12 && hourOfDay <= 23) {
            // If hourOfDay is a valid PM hour, set the correct PM values
            // so that it won't throw an exception in case it's set to
            // non-lenient later.
            this.set(AM_PM, PM);
            this.set(HOUR, hourOfDay - 12);
        } else {
            // The default value for AM_PM is AM.
            // We don't care any out of range value here for leniency.
            this.set(HOUR, hourOfDay);
            this.set(AM_PM, AM);
        }
        // The stamp values of AM_PM and HOUR must be COMPUTED. (6440854)
        //setFieldsComputed(HOUR_MASK|AM_PM_MASK);

        this.set(HOUR_OF_DAY, hourOfDay);
        this.set(MINUTE, minute);
        this.set(SECOND, second);
        // should be changed to set() when this constructor is made
        // public.
        this.set(MILLISECOND, millis);

        YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(year, month, dayOfMonth));
        Calendar cal = new GregorianCalendar(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), hourOfDay,
                minute, second);
        time = cal.getTimeInMillis();

        isTimeSeted = true;
    }


    public static YearMonthDate gregorianToJalali(YearMonthDate gregorian) {

        int jalaliYear;
        int jalaliMonth;
        int jalaliDay;

        int gregorianDayNo, jalaliDayNo;
        int jalaliNP;
        int i;

        gregorian.setYear(gregorian.getYear() - 1600);
        //gregorian.setMonth(gregorian.getMonth() - 1);
        gregorian.setDate(gregorian.getDate() - 1);

        gregorianDayNo = 365 * gregorian.getYear() + (int) Math.floor((gregorian.getYear() + 3) / 4)
                - (int) Math.floor((gregorian.getYear() + 99) / 100)
                + (int) Math.floor((gregorian.getYear() + 399) / 400);
        for (i = 0; i < gregorian.getMonth(); ++i) {
            gregorianDayNo += gregorianDaysInMonth[i];
        }

        if (gregorian.getMonth() > 1 && ((gregorian.getYear() % 4 == 0 && gregorian.getYear() % 100 != 0)
                || (gregorian.getYear() % 400 == 0))) {
            ++gregorianDayNo;
        }

        gregorianDayNo += gregorian.getDate();

        jalaliDayNo = gregorianDayNo - 79;

        jalaliNP = (int) Math.floor(jalaliDayNo / 12053);
        jalaliDayNo = jalaliDayNo % 12053;

        jalaliYear = 979 + 33 * jalaliNP + 4 * (int) (jalaliDayNo / 1461);
        jalaliDayNo = jalaliDayNo % 1461;

        if (jalaliDayNo >= 366) {
            jalaliYear += (int) Math.floor((jalaliDayNo - 1) / 365);
            jalaliDayNo = (jalaliDayNo - 1) % 365;
        }

        for (i = 0; i < 11 && jalaliDayNo >= jalaliDaysInMonth[i]; ++i) {
            jalaliDayNo -= jalaliDaysInMonth[i];
        }
        jalaliMonth = i;
        jalaliDay = jalaliDayNo + 1;

        return new YearMonthDate(jalaliYear, jalaliMonth, jalaliDay);
    }


    public static YearMonthDate jalaliToGregorian(YearMonthDate jalali) {

        int gregorianYear;
        int gregorianMonth;
        int gregorianDay;

        int gregorianDayNo, jalaliDayNo;
        int leap;

        int i;
        jalali.setYear(jalali.getYear() - 979);
        //jalali.setMonth(jalali.getMonth() - 1);
        jalali.setDate(jalali.getDate() - 1);

        jalaliDayNo = 365 * jalali.getYear() + (int) (jalali.getYear() / 33) * 8
                + (int) Math.floor(((jalali.getYear() % 33) + 3) / 4);
        for (i = 0; i < jalali.getMonth(); ++i) {
            jalaliDayNo += jalaliDaysInMonth[i];
        }

        jalaliDayNo += jalali.getDate();

        gregorianDayNo = jalaliDayNo + 79;

        gregorianYear = 1600 + 400 * (int) Math.floor(gregorianDayNo / 146097); /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        gregorianDayNo = gregorianDayNo % 146097;

        leap = 1;
        if (gregorianDayNo >= 36525) /* 36525 = 365*100 + 100/4 */ {
            gregorianDayNo--;
            gregorianYear += 100 * (int) Math.floor(gregorianDayNo / 36524); /* 36524 = 365*100 + 100/4 - 100/100 */
            gregorianDayNo = gregorianDayNo % 36524;

            if (gregorianDayNo >= 365) {
                gregorianDayNo++;
            } else {
                leap = 0;
            }
        }

        gregorianYear += 4 * (int) Math.floor(gregorianDayNo / 1461); /* 1461 = 365*4 + 4/4 */
        gregorianDayNo = gregorianDayNo % 1461;

        if (gregorianDayNo >= 366) {
            leap = 0;

            gregorianDayNo--;
            gregorianYear += (int) Math.floor(gregorianDayNo / 365);
            gregorianDayNo = gregorianDayNo % 365;
        }

        for (i = 0; gregorianDayNo >= gregorianDaysInMonth[i] + ((i == 1 && leap == 1) ? i : 0); i++) {
            gregorianDayNo -= gregorianDaysInMonth[i] + ((i == 1 && leap == 1) ? i : 0);
        }
        gregorianMonth = i;
        gregorianDay = gregorianDayNo + 1;

        return new YearMonthDate(gregorianYear, gregorianMonth, gregorianDay);

    }

    /*   public static Calendar getInstance(){
           return getInstance(TimeZone.getDefault(),Locale.getDefault());
        }
    
        public static Calendar getInstance(TimeZone zone){
            return getInstance(zone, Locale.getDefault());
        }
    
        public static Calendar getInstance(Locale locale){
            return getInstance(TimeZone.getDefault(),locale);
        }
    
        public static Calendar getInstance(TimeZone zone,Locale locale){
            timeZone=zone;
            return new JalaliCalendar(zone,locale);
        }
    
    */
    public static int weekOfYear(int dayOfYear, int year) {
        switch (dayOfWeek(JalaliCalendar.jalaliToGregorian(new YearMonthDate(year, 0, 1)))) {
            case 2:
                dayOfYear++;
                break;
            case 3:
                dayOfYear += 2;
                break;
            case 4:
                dayOfYear += 3;
                break;
            case 5:
                dayOfYear += 4;
                break;
            case 6:
                dayOfYear += 5;
                break;
            case 7:
                dayOfYear--;
                break;
        }
        ;
        dayOfYear = (int) Math.floor(dayOfYear / 7);
        return dayOfYear + 1;
    }

    public static int dayOfWeek(YearMonthDate yearMonthDate) {

        Calendar cal=new GregorianCalendar(yearMonthDate.getYear(),yearMonthDate.getMonth(),yearMonthDate.getDate());
        return cal.get(DAY_OF_WEEK);

    }

    public static boolean isLeepYear(int year) {
        if ((year % 33 == 1 || year % 33 == 5 || year % 33 == 9 || year % 33 == 13 ||
                year % 33 == 17 || year % 33 == 22 || year % 33 == 26 || year % 33 == 30)) {
            return true;
        } else return false;
    }

    @Override
    protected void computeTime() {

        if (!isTimeSet && !isTimeSeted) {
            Calendar cal = GregorianCalendar.getInstance(timeZone);
            set(HOUR_OF_DAY, cal.get(HOUR_OF_DAY));
            set(HOUR, cal.get(HOUR));
            set(MINUTE, cal.get(MINUTE));
            set(SECOND, cal.get(SECOND));
            set(MILLISECOND, cal.get(MILLISECOND));
            set(ZONE_OFFSET, cal.get(ZONE_OFFSET));
            set(DST_OFFSET, cal.get(DST_OFFSET));
            set(AM_PM, cal.get(AM_PM));

            YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(internalGet(YEAR), internalGet(MONTH), internalGet(DAY_OF_MONTH)));
            cal.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate()
                    , internalGet(HOUR_OF_DAY), internalGet(MINUTE), internalGet(SECOND));
            time = cal.getTimeInMillis();

        } else if (!isTimeSet && isTimeSeted) {

            if (internalGet(HOUR_OF_DAY) >= 12 && internalGet(HOUR_OF_DAY) <= 23) {
                // If hourOfDay is a valid PM hour, set the correct PM values
                // so that it won't throw an exception in case it's set to
                // non-lenient later.
                this.set(AM_PM, PM);
                this.set(HOUR, internalGet(HOUR_OF_DAY) - 12);
            } else {
                // The default value for AM_PM is AM.
                // We don't care any out of range value here for leniency.
                this.set(HOUR, internalGet(HOUR_OF_DAY));
                this.set(AM_PM, AM);
            }
            cal = new GregorianCalendar();
            this.set(ZONE_OFFSET, timeZone.getRawOffset());
            this.set(DST_OFFSET, timeZone.getDSTSavings());
            YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(internalGet(YEAR), internalGet(MONTH), internalGet(DAY_OF_MONTH)));
            cal.set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), internalGet(HOUR_OF_DAY),
                    internalGet(MINUTE), internalGet(SECOND));
            time = cal.getTimeInMillis();

        }
    }

    @Override
    protected void computeFields() {
        boolean temp = isTimeSet;
        if (!areFieldsSet) {
            setMinimalDaysInFirstWeek(1);
            setFirstDayOfWeek(7);

            //Day_Of_Year
            int dayOfYear = 0;
            int index = 0;

            while (index < fields[2]) {
                dayOfYear += jalaliDaysInMonth[index++];
            }
            dayOfYear += fields[5];
            set(DAY_OF_YEAR, dayOfYear);
            //***

            //Day_of_Week
            set(DAY_OF_WEEK, dayOfWeek(jalaliToGregorian(new YearMonthDate(fields[1], fields[2], fields[5]))));
            //***

            //Day_Of_Week_In_Month
            if (0 < fields[5] && fields[5] < 8) {
                set(DAY_OF_WEEK_IN_MONTH, 1);
            }

            if (7 < fields[5] && fields[5] < 15) {
                set(DAY_OF_WEEK_IN_MONTH, 2);
            }

            if (14 < fields[5] && fields[5] < 22) {
                set(DAY_OF_WEEK_IN_MONTH, 3);
            }

            if (21 < fields[5] && fields[5] < 29) {
                set(DAY_OF_WEEK_IN_MONTH, 4);
            }

            if (28 < fields[5] && fields[5] < 32) {
                set(DAY_OF_WEEK_IN_MONTH, 5);
            }
            //***


            //Week_Of_Year
            set(WEEK_OF_YEAR, weekOfYear(fields[6], fields[1]));
            //***

            //Week_Of_Month
            set(WEEK_OF_MONTH, weekOfYear(fields[6], fields[1]) - weekOfYear(fields[6] - fields[5], fields[1]) + 1);
            //

            isTimeSet = temp;
        }
    }

    @Override
    public void add(int field, int amount) {

        if (field == MONTH) {
            amount += get(MONTH);
            add(YEAR, amount / 12);
            set(MONTH, amount % 12);
            if (get(DAY_OF_MONTH) > jalaliDaysInMonth[amount % 12]) {
                set(DAY_OF_MONTH, jalaliDaysInMonth[amount % 12]);
                if (get(MONTH) == 11 && isLeepYear(get(YEAR))) {
                    set(DAY_OF_MONTH, 30);
                }
            }
            complete();

        } else if (field == YEAR) {

            set(YEAR, get(YEAR) + amount);
            if (get(DAY_OF_MONTH) == 30 && get(MONTH) == 11 && !isLeepYear(get(YEAR))) {
                set(DAY_OF_MONTH, 29);
            }

            complete();
        } else {
            YearMonthDate yearMonthDate = jalaliToGregorian(new YearMonthDate(get(YEAR), get(MONTH), get(DATE)));
            Calendar gc = new GregorianCalendar(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(),
                    get(HOUR_OF_DAY), get(MINUTE), get(SECOND));
            gc.add(field, amount);
            yearMonthDate = gregorianToJalali(new YearMonthDate(gc.get(YEAR), gc.get(MONTH), gc.get(DATE)));
            set(yearMonthDate.getYear(), yearMonthDate.getMonth(), yearMonthDate.getDate(), gc.get(HOUR_OF_DAY), gc.get(MINUTE)
                    , gc.get(SECOND));
            complete();
        }

    }

    @Override
    public void roll(int field, boolean up) {
        roll(field, up ? +1 : -1);
    }

    @Override
    public void roll(int field, int amount) {
        if (amount == 0) {
            return;
        }

        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException();
        }

        // Sync the time and calendar fields.
        complete();

        int min = getMinimum(field);
        int max = getMaximum(field);

        switch (field) {
            case AM_PM: {
                if (amount % 2 != 0) {
                    if (internalGet(AM_PM) == AM) {
                        fields[AM_PM] = PM;
                    } else {
                        fields[AM_PM] = AM;
                    }
                    if (get(AM_PM) == AM) {
                        set(HOUR_OF_DAY, get(HOUR));
                    } else {
                        set(HOUR_OF_DAY, get(HOUR) + 12);
                    }
                }
                break;
            }
            case YEAR: {
                set(YEAR, internalGet(YEAR) + amount);
                if (internalGet(MONTH) == 11 && internalGet(DAY_OF_MONTH) == 30 && !isLeepYear(internalGet(YEAR))) {
                    set(DAY_OF_MONTH, 29);
                }
                break;
            }
            case MINUTE: {
                int unit = 60;
                int m = (internalGet(MINUTE) + amount) % unit;
                if (m < 0) {
                    m += unit;
                }
                set(MINUTE, m);
                break;
            }
            case SECOND: {
                int unit = 60;
                int s = (internalGet(SECOND) + amount) % unit;
                if (s < 0) {
                    s += unit;
                }
                set(SECOND, s);
                break;
            }
            case MILLISECOND: {
                int unit = 1000;
                int ms = (internalGet(MILLISECOND) + amount) % unit;
                if (ms < 0) {
                    ms += unit;
                }
                set(MILLISECOND, ms);
                break;
            }

            case HOUR: {
                set(HOUR, (internalGet(HOUR) + amount) % 12);
                if (internalGet(HOUR) < 0) {
                    fields[HOUR] += 12;
                }
                if (internalGet(AM_PM) == AM) {
                    set(HOUR_OF_DAY, internalGet(HOUR));
                } else {
                    set(HOUR_OF_DAY, internalGet(HOUR) + 12);
                }

                break;
            }
            case HOUR_OF_DAY: {
                fields[HOUR_OF_DAY] = (internalGet(HOUR_OF_DAY) + amount) % 24;
                if (internalGet(HOUR_OF_DAY) < 0) {
                    fields[HOUR_OF_DAY] += 24;
                }
                if (internalGet(HOUR_OF_DAY) < 12) {
                    fields[AM_PM] = AM;
                    fields[HOUR] = internalGet(HOUR_OF_DAY);
                } else {
                    fields[AM_PM] = PM;
                    fields[HOUR] = internalGet(HOUR_OF_DAY) - 12;
                }

            }
            case MONTH: {
                int mon = (internalGet(MONTH) + amount) % 12;
                if (mon < 0) {
                    mon += 12;
                }
                set(MONTH, mon);

                int monthLen = jalaliDaysInMonth[mon];
                if (internalGet(MONTH) == 11 && isLeepYear(internalGet(YEAR))) {
                    monthLen = 30;
                }
                if (internalGet(DAY_OF_MONTH) > monthLen) {
                    set(DAY_OF_MONTH, monthLen);
                }
                break;
            }
            case DAY_OF_MONTH: {
                int unit = 0;
                if (0 <= get(MONTH) && get(MONTH) <= 5) {
                    unit = 31;
                }
                if (6 <= get(MONTH) && get(MONTH) <= 10) {
                    unit = 30;
                }
                if (get(MONTH) == 11) {
                    if (isLeepYear(get(YEAR))) {
                        unit = 30;
                    } else {
                        unit = 29;
                    }
                }
                int d = (get(DAY_OF_MONTH) + amount) % unit;
                if (d < 0) {
                    d += unit;
                }
                set(DAY_OF_MONTH, d);
                break;

            }
            case WEEK_OF_YEAR: {
                break;
            }
            case DAY_OF_YEAR: {
                int unit = (isLeepYear(internalGet(YEAR)) ? 366 : 365);
                int dayOfYear = (internalGet(DAY_OF_YEAR) + amount) % unit;
                dayOfYear = (dayOfYear > 0) ? dayOfYear : dayOfYear + unit;
                int month = 0, temp = 0;
                while (dayOfYear > temp) {
                    temp += jalaliDaysInMonth[month++];
                }
                set(MONTH, --month);
                set(DAY_OF_MONTH, jalaliDaysInMonth[internalGet(MONTH)] - (temp - dayOfYear));
                break;
            }
            case DAY_OF_WEEK: {
                int index = amount % 7;
                if (index < 0) {
                    index += 7;
                }
                int i = 0;
                while (i != index) {
                    if (internalGet(DAY_OF_WEEK) == FRIDAY) {
                        add(DAY_OF_MONTH, -6);
                    } else {
                        add(DAY_OF_MONTH, +1);
                    }
                    i++;
                }
                break;
            }

            case WEEK_OF_MONTH:{
                int dayOfMonth=internalGet(DAY_OF_MONTH);

            }
           
            default:
                throw new IllegalArgumentException();
        }
//        YearMonthDate yearMonthDate=jalaliToGregorian(new YearMonthDate(internalGet(YEAR),internalGet(MONTH)
//                ,internalGet(DAY_OF_MONTH)));
//        cal=new GregorianCalendar(yearMonthDate.getYear(),yearMonthDate.getMonth(),yearMonthDate.getDate()
//                ,internalGet(HOUR_OF_DAY),internalGet(MINUTE),internalGet(SECOND));
//        cal.roll(field,amount);
//        yearMonthDate=gregorianToJalali(new YearMonthDate(cal.get(YEAR),cal.get(MONTH),cal.get(DAY_OF_MONTH)));
//        set(yearMonthDate.getYear(),yearMonthDate.getMonth(),yearMonthDate.getDate(),cal.get(HOUR_OF_DAY)
//                ,cal.get(MINUTE),cal.get(DAY_OF_MONTH));
//        complete();

    }

    @Override
    public int getMinimum(int field) {
        return MIN_VALUES[field];
    }

    @Override
    public int getMaximum(int field) {
        return MAX_VALUES[field];
    }

    @Override
    public int getGreatestMinimum(int field) {
        return MIN_VALUES[field];
    }

    @Override
    public int getLeastMaximum(int field) {
        return LEAST_MAX_VALUES[field];
    }

    public static class YearMonthDate {

        public YearMonthDate(int year, int month, int date) {
            this.year = year;
            this.month = month;
            this.date = date;
        }

        private int year;
        private int month;
        private int date;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String toString() {
            return getYear() + "/" + getMonth() + "/" + getDate();
        }
    }

}

