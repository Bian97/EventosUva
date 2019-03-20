package com.example.eventosuva.Presenter;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePresenter implements IDatePresenter {

    public DatePresenter() {
    }

    @Override
    public boolean isRecent(Date date) {
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(date.getTime(), hoje1.getTime());
        if((dur.getStandardDays() >= -1 && dur.getStandardDays() <= 1) || dur.getStandardDays() == 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isToday(Date date) {
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

    @Override
    public boolean isYesterday(Date date) {
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

    @Override
    public boolean isWeek(Date date) {
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(hoje1.getTime(), date.getTime());

        if(dur.getStandardDays() <= 6 && dur.getStandardDays() >= 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isMonth(Date date) {
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

    @Override
    public boolean isYear(Date date) {
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

    public String convertDateToShow(String strDate){//converter data do banco para a habitual brasileira
        // formato de entrada deve ser 2017-01-17
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // sem argumentos, pega o formato do sistema para exibir a data
        SimpleDateFormat dateFormatOut = new SimpleDateFormat();
        // com argumentos formato e locale a saida e sempre a mesma
        //  SimpleDateFormat dateFormatOut = new SimpleDateFormat("dd-MM-yy", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormatIn.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringToSpace(String.valueOf(dateFormatOut.format(convertedDate)));
    }

    private String stringToSpace(String string){

        int spaceIndex = string.indexOf(" ");
        if(spaceIndex > 0) {
            string = string.substring(0, spaceIndex);
        }
        return string;
    }
}
