package com.example.eventosuva.util;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil{

    public static boolean isRecent(Date date) {
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(date.getTime(), hoje1.getTime());
        if((dur.getStandardDays() >= -1 && dur.getStandardDays() <= 1) || dur.getStandardDays() == 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isToday(Date date) {
        Calendar calHoje = Calendar.getInstance();
        Date hoje = calHoje.getTime();
        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(date);
        if(localDateEvento.equals(localDateHoje)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isYesterday(Date date) {
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        LocalDate localDateHoje = new LocalDate(hoje1);
        LocalDate localDateEvento = new LocalDate(date);
        if(localDateEvento.isBefore(localDateHoje)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isWeek(Date date) {
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(hoje1.getTime(), date.getTime());

        if(dur.getStandardDays() <= 6 && dur.getStandardDays() >= 0){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isMonth(Date date) {
        Calendar hoje = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(date);

        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(dataEvento);

        if(((localDateHoje.isBefore(localDateEvento) || localDateHoje.equals(localDateEvento))&& localDateEvento.getMonthOfYear() == localDateHoje.getMonthOfYear())){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isYear(Date date) {
        Calendar hoje = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(date);

        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(dataEvento);

        if((localDateHoje.isBefore(localDateEvento) || localDateHoje.equals(localDateEvento)) && localDateEvento.getYear() == localDateHoje.getYear()){
            return true;
        }else {
            return false;
        }
    }

    public static Date convertStringToData(String string) {
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

    public static SimpleDateFormat createDateFormat() {
        Locale localeBR = new Locale("pt", "BR");
        return new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", localeBR);
    }

}
