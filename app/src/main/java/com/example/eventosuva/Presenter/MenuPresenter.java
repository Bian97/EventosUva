package com.example.eventosuva.Presenter;

import android.os.AsyncTask;

import com.example.eventosuva.Controle.EventosDAO;
import com.example.eventosuva.Model.DateModel;
import com.example.eventosuva.View.IMenuActivity;

public class MenuPresenter implements IMenuPresenter {

    private IMenuActivity iMenuActivity;
    private DateModel dateModel;

    private JSONTask jsonTask;


    public MenuPresenter(IMenuActivity iMenuActivity){
        this.iMenuActivity = iMenuActivity;
        dateModel = new DateModel();
    }

    @Override
    public void onGetRequest() {
        try {
            String url = "http://sicsu.net/uvapps/wsListData.php";
            String tipo = "GET";
            if (jsonTask == null ||  jsonTask.getStatus() != AsyncTask.Status.RUNNING) {
                jsonTask = new JSONTask();
                jsonTask.execute(url, tipo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            iMenuActivity.onLoadingBegin();
            printProgress();
        }

        @Override
        protected String doInBackground(String... params) {
            EventosDAO aD = new EventosDAO();
            String aux = null;
            if(params[1].equalsIgnoreCase("GET")){
                aux = aD.request(params[0]);
            } else if(params[1].equalsIgnoreCase("POST") || params[1].equalsIgnoreCase("PUT")){
                aux = aD.post(params[0], params[1], params[2]);
            }
            return aux;
        }

        @Override
        protected void onPostExecute(String json) {
            if(json != null) {
                // createEventList(result);
                iMenuActivity.onLoadingFinish();
            } else {
                iMenuActivity.onLoadingFail();
            }
        }
    }

    private void printProgress() {
        System.out.println("Baixando informações...");
    }

}
