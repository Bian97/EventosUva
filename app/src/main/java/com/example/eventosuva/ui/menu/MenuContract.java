package com.example.eventosuva.ui.menu;

public interface MenuContract {

    interface IMenuActivity {
        void onLoadingBegin();
        void onLoadingFailure();
        void onLoadingFinish(String json);
    }

    interface IMenuPresenter {
        void getEventsJSON();
    }

}
