package com.example.eventosuva.Model;

import java.util.Date;

public interface IDateModel {

    boolean isRecent (Date date);
    boolean isToday(Date date);
    boolean isYesterday(Date date);
    boolean isWeek(Date date);
    boolean isMonth(Date date);
    boolean isYear(Date date);
    Date convertStringToData(String string);
    String convertDateToShow(String strDate);
}
