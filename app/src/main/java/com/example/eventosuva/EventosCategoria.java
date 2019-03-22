package com.example.eventosuva;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eventosuva.Controle.EventosDAO;
import com.example.eventosuva.modelo.Eventos;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Bian on 19/12/2017.
 */

public class EventosCategoria extends AppCompatActivity{

    ArrayList<Eventos> listaEventosCategoria = new ArrayList<>();
    private ArrayList<Eventos> eventos = new ArrayList<Eventos>();
    private int pos;
    Eventos evento;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Criado por Bian Medeiros e Victor Franklin", Toast.LENGTH_LONG).show();
        iniciarTela();
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    public static boolean checkAndRequestPermissions(final Activity context) {
        int ExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int internet = ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void iniciarTela(){
        if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else {
            setContentView(R.layout.activity_grid_categoria);
        }
        ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET);
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.e("XAMPSON", "COM PERMISSÃO");
        } else {
            Log.e("XAMPSON", "SEM PERMISSÃO");

            checkAndRequestPermissions(EventosCategoria.this);
        }
        iniciarChamada();
    }

    public void proximaActivity(int position){
        Intent intent = new Intent(EventosCategoria.this, EventosGridView.class);
        pos = position;
        intent.putExtra("pos",pos);
        intent.putParcelableArrayListExtra("auxiliar",eventos);
        startActivity(intent);
    }

    public void onRecentesClick(View view) {
        proximaActivity(0);
    }

    public void onHojeClick(View view) {
        proximaActivity(1);
    }

    public void onSeteDiasClick(View view) {
        proximaActivity(2);
    }

    public void onMesClick(View view) {
        proximaActivity(3);
    }

    public void onAnoClick(View view) {
        proximaActivity(4);
    }


    public boolean diaDeHoje(Date data){
        Calendar calHoje = Calendar.getInstance();
        Date hoje = calHoje.getTime();
        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(data);

        if(localDateEvento.equals(localDateHoje)){
            return true;
        }
        return false;
    }

    public boolean adicRecente(Date dataPostado){
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(dataPostado.getTime(), hoje1.getTime());
        if((dur.getStandardDays() >= -1 && dur.getStandardDays() <= 1) || dur.getStandardDays() == 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean diaAnterior(Date data){
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        LocalDate localDateHoje = new LocalDate(hoje1);
        LocalDate localDateEvento = new LocalDate(data);
        if(localDateEvento.isBefore(localDateHoje)){
            return true;
        }else {
            return false;
        }
    }

    public boolean diaDaSemana(Date data){
        Calendar hoje = Calendar.getInstance();
        Date hoje1 = hoje.getTime();
        Duration dur = new Duration(hoje1.getTime(), data.getTime());

        if(dur.getStandardDays() <= 6 && dur.getStandardDays() >= 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean diaDoMes(Date data){
        Calendar hoje = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(data);

        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(dataEvento);

        if(((localDateHoje.isBefore(localDateEvento) || localDateHoje.equals(localDateEvento))&& localDateEvento.getMonthOfYear() == localDateHoje.getMonthOfYear())){
            return true;
        }else {
            return false;
        }
    }

    public boolean diaDoAno(Date data){
        Calendar hoje = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(data);

        LocalDate localDateHoje = new LocalDate(hoje);
        LocalDate localDateEvento = new LocalDate(dataEvento);

        if((localDateHoje.isBefore(localDateEvento) || localDateHoje.equals(localDateEvento)) && localDateEvento.getYear() == localDateHoje.getYear()){
            return true;
        }else {
            return false;
        }
    }

    private void listEventos(String resposta) {
        evento = null;
        try {
            String json = null;

            json = resposta;

            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {
                String dataEvento = jA.getJSONObject(i).getString("dataevento");
                String dataPostado = jA.getJSONObject(i).getString("datapostado");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date data1 = dateFormat.parse(dataEvento);
                Date data2 = dateFormat.parse(dataPostado);
                evento = new Eventos(jA.getJSONObject(i).getInt("codigo"),
                        jA.getJSONObject(i).getString("caminho"),
                        jA.getJSONObject(i).getString("nome"),
                        jA.getJSONObject(i).getString("descricao"),
                        "Recem adicionados", data1, data2, jA.getJSONObject(i).getString("curso"), jA.getJSONObject(i).getInt("campus"));
                Log.e("XAMPSON", evento.getNome());
                if (evento == null) {
                    eventos.clear();
                } else {
                    eventos.add(0, evento);
                }
            }
            progressDialog.dismiss();

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (adicRecente(ev.getDataPostado())){
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Recem adicionados", ev.getDataEvento(), ev.getDataPostado(), ev.getCurso(), ev.getCampus()));
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDeHoje(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Hoje", ev.getDataEvento(), ev.getDataPostado(), ev.getCurso(), ev.getCampus()));//categoria: hoje
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDaSemana(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Próximos 7 dias", ev.getDataEvento(), ev.getDataPostado(), ev.getCurso(), ev.getCampus()));
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDoMes(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Mês", ev.getDataEvento(), ev.getDataPostado(), ev.getCurso(), ev.getCampus()));
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDoAno(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Ano", ev.getDataEvento(), ev.getDataPostado(), ev.getCurso(), ev.getCampus()));
                }
            }
            if (listaEventosCategoria == null) {
                Toast.makeText(getApplicationContext(), "Não existem eventos disponiveis", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            eventos.clear();
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Exceção: "+ e.getMessage());
        }
    }



    JSONTask jsonTask;

    public void iniciarChamada(){
        try {
            String chamadaWs;

            chamadaWs = "http://sicsu.net/uvapps/wsListData.php";

            iniciarDownload(chamadaWs, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exibirProgress(boolean exibir) {
        if (exibir) {
            System.out.println("Baixando informações...");
        }
    }

    public void iniciarDownload(String url, String tipo) {
        if (jsonTask == null ||  jsonTask.getStatus() != AsyncTask.Status.RUNNING) {
            jsonTask = new JSONTask();
            jsonTask.execute(url, tipo);
        }
    }


    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
            progressDialog = ProgressDialog.show(EventosCategoria.this,"Aguarde um pouco.", "Carregando informações...", false, false);
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
        protected void onPostExecute(String result) {
            if(result != null) {
                listEventos(result);
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet e reinicie o aplicativo!", Toast.LENGTH_LONG);
            }
        }
    }
}