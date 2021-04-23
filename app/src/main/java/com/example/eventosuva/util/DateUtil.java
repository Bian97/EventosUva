package com.example.eventosuva.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil{

    public static boolean isRecent(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        Duration dur = new Duration(date.getTime(), today.getTime());
        if((dur.getStandardDays() >= -1 && dur.getStandardDays() <= 1) || dur.getStandardDays() == 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        LocalDate localDateToday = new LocalDate(today);
        LocalDate localDateEvent = new LocalDate(date);
        if(localDateEvent.equals(localDateToday)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        LocalDate localDateToday = new LocalDate(today);
        LocalDate localDateEvent = new LocalDate(date);
        if(localDateEvent.isBefore(localDateToday)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        Duration dur = new Duration(today.getTime(), date.getTime());

        if(dur.getStandardDays() <= 6 && dur.getStandardDays() >= 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar eventDate = Calendar.getInstance();
        eventDate.setTime(date);

        LocalDate localDateToday = new LocalDate(calendar);
        LocalDate localDateEvent = new LocalDate(eventDate);

        if(((localDateToday.isBefore(localDateEvent) || localDateToday.equals(localDateEvent))&& localDateEvent.getMonthOfYear() == localDateToday.getMonthOfYear())){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isYear(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar eventDate = Calendar.getInstance();
        eventDate.setTime(date);

        LocalDate localDateToday = new LocalDate(today);
        LocalDate localDateEvent = new LocalDate(eventDate);

        if((localDateToday.isBefore(localDateEvent) || localDateToday.equals(localDateEvent)) && localDateEvent.getYear() == localDateToday.getYear()){
            return true;
        } else {
            return false;
        }
    }

    public static Date convertStringToDate(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = dateFormat.parse(string);
        }catch (ParseException e){
            date = null;
            System.out.println("Exceção: "+ e.getMessage());
        }
        return date;
    }

    public static Date convertStringToDateTime(String string) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("pt", "BR"));
        Date date;
        try{
            date = dateFormat.parse(string);
        }catch (ParseException e){
            date = null;
            System.out.println("Exceção: "+ e.getMessage());
        }
        return date;
    }

    public static SimpleDateFormat createDateFormat() {
        Locale localeBR = new Locale("pt", "BR");
        return new SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'às' HH:mm'hrs'", localeBR);
    }

}
