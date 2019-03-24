package com.example.eventosuva.Presenter.GridPresenter;

import com.example.eventosuva.Model.Eventos;
import java.util.ArrayList;

public interface IGridPresenter {

    ArrayList<Eventos> createList(int choice, String json);
}
