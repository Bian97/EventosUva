package com.example.eventosuva.ui.grid;

import com.example.eventosuva.model.Event;

import java.util.ArrayList;

public interface GridContract {

    interface onCreateListListener {
        void onCreateListError(String message);
        void onCreateListEmpty();
        void onCreateListChoiceEmpty();
    }

    interface Presenter {
        ArrayList<Event> createList(onCreateListListener listener, int choice, String json);
    }
}