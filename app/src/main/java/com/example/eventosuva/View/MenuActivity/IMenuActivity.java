package com.example.eventosuva.View.MenuActivity;

public interface IMenuActivity {

    void onLoadingBegin();
    void onLoadingFailure();
    void onLoadingFinish(String json);

}