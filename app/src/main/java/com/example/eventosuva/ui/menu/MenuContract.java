package com.example.eventosuva.ui.menu;

public interface MenuContract {

    interface onGetEventsListener {
        void onLoadingBegin();
        void onLoadingFailure();
        void onLoadingSuccess(String json);
    }

    interface Presenter {
        void getEventsList(onGetEventsListener listener);
    }
}