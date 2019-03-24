package com.example.eventosuva.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IDateHandler {

    boolean isRecent (Date date);
    boolean isToday(Date date);
    boolean isYesterday(Date date);
    boolean isWeek(Date date);
    boolean isMonth(Date date);
    boolean isYear(Date date);
    Date convertStringToData(String string);
    SimpleDateFormat createDateFormat();
}
