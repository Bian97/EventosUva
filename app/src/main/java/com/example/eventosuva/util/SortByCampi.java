package com.example.eventosuva.util;

import com.example.eventosuva.model.Eventos;

import java.util.Comparator;

public class SortByCampi implements Comparator<Eventos> {
    public int compare(Eventos a, Eventos b)
    {
        return a.getCampus() - b.getCampus();
    }
}