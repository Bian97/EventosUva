package com.example.eventosuva.util;

import com.example.eventosuva.model.Event;

import java.util.Comparator;

public class SortByCampi implements Comparator<Event> {
    public int compare(Event a, Event b)
    {
        return a.getCampus() - b.getCampus();
    }
}