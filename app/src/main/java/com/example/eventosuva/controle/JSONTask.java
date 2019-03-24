package com.example.eventosuva.Controle;

import android.os.AsyncTask;

import com.example.eventosuva.Presenter.MenuPresenter.MenuPresenter;

public class JSONTask extends AsyncTask<String, String, String> {

    private MenuPresenter presenter;

    public JSONTask(MenuPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        presenter.beganLoading();
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
            presenter.finishedLoading(json);
        } else {
            presenter.failedLoading();
        }
    }

    private void printProgress() {
        System.out.println("Baixando informações...");
    }
}
