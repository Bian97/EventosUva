package com.example.eventosuva.Presenter;

import java.util.Date;

public interface IDatePresenter {

    boolean isRecent (Date date);
    boolean isToday(Date date);
    boolean isYesterday(Date date);
    boolean isWeek(Date date);
    boolean isMonth(Date date);
    boolean isYear(Date date);

}
