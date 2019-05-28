package com.example.eventosuva.ui.menu;

import android.os.AsyncTask;

import com.example.eventosuva.control.JSONTask;
import com.example.eventosuva.ui.menu.MenuContract.IMenuActivity;

public class MenuPresenter implements MenuContract.IMenuPresenter {

    private IMenuActivity iMenuActivity;
    private JSONTask jsonTask;

    public MenuPresenter(IMenuActivity iMenuActivity) {
        this.iMenuActivity = iMenuActivity;
    }

    @Override
    public void getEventsJSON() {
        try {
            String url = "http://sicsu.net/uvapps/wsListData.php";
            String tipo = "GET";
            if (jsonTask == null || jsonTask.getStatus() != AsyncTask.Status.RUNNING) {
                jsonTask = new JSONTask(this);
                jsonTask.execute(url, tipo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beganLoading() {
        iMenuActivity.onLoadingBegin();
    }

    public void failedLoading() {
        iMenuActivity.onLoadingFailure();
    }

    public void finishedLoading(String json) {
        iMenuActivity.onLoadingSuccess(json);
    }

}
