package com.pardazeshgaran.jalaliCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Amir
 * Date: 6/25/12
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class YearMonthDate {

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

    public String toString(){
        return getYear()+" "+getMonth()+" "+getDate();
    }
}
