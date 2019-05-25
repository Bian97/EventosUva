package com.example.eventosuva.ui.grid;

import com.example.eventosuva.model.Eventos;

import java.util.ArrayList;

public interface GridContract {

    interface IGridActivity {
        void onCreateListError(String message);
        void onCreateListEmpty();
        void onCreateListChoiceEmpty();
    }

    interface IGridPresenter {
        ArrayList<Eventos> createList(int choice, String json);
    }

}
