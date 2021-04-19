package com.example.eventosuva.ui.menu;

import android.os.AsyncTask;

import com.example.eventosuva.control.EventosDAO;

public class MenuPresenter implements MenuContract.Presenter {

    private EventsListTask task;

    public MenuPresenter() {
    }

    @Override
    public void getEventsList(MenuContract.onGetEventsListener listener) {
        try {
            String url = "http://cadier.com.br/uvapp/wsListEventos.php";
            String tipo = "GET";
            if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
                task = new EventsListTask(listener);
                task.execute(url, tipo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EventsListTask extends AsyncTask<String, String, String> {

        private MenuContract.onGetEventsListener listener;

        public EventsListTask(MenuContract.onGetEventsListener listener){
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.onLoadingBegin();
            System.out.println("Baixando informações...");
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
                listener.onLoadingSuccess(json);
            } else {
                listener.onLoadingFailure();
            }
        }
    }

}
