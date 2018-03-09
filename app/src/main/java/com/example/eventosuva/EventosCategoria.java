package com.example.eventosuva;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.controle.EventosDAO;
import com.example.eventosuva.modelo.Eventos;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DrGreend on 19/12/2017.
 */

public class EventosCategoria extends AppCompatActivity{

    ArrayList<Eventos> listaEventosCategoria = new ArrayList<>();
    private ArrayList<Eventos> eventos = new ArrayList<Eventos>();
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_categoria);

        Toast.makeText(this, "Criado por Bian Medeiros e Victor Franklin", Toast.LENGTH_LONG).show();
        try {
            String chamadaWs;

            chamadaWs = "http://profsicsu.com.br/prototipos/eventosUva/wsListData.php";
            System.out.println("CHAMADAWS: "+chamadaWs);

           iniciarDownload(chamadaWs, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void proximaActivity(int position){
        Intent intent = new Intent(EventosCategoria.this,EventosGridView.class);
        pos = position;
        intent.putExtra("pos",pos);
        for(int i = 0; i < listaEventosCategoria.size(); i++){
            System.out.println("\nNome: "+listaEventosCategoria.get(i).getNome());
            System.out.println("\nCategoria: "+listaEventosCategoria.get(i).getCategoria());
        }
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


    public boolean diaDeHoje(int dia, int mes, int ano){
        int x,y,z;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        y = hoje.get(Calendar.MONTH) +1;
        z = hoje.get(Calendar.YEAR);
        if(dia == x && mes == y && ano == z){
            return true;
        }
        return false;
    }

    public boolean adicRecente(int dia, int mes, int ano){
        int x, y, z;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        y = hoje.get(Calendar.MONTH);
        z = hoje.get(Calendar.YEAR);
        if((((dia >= x) && (mes >= y + 1)) && (ano >= z)) || ((mes > y + 1) && (ano >= z))){
            return true;
        }
        return false;
    }

    public boolean diaAnterior(int dia, int mes, int ano){
        int x, y, z;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        y = hoje.get(Calendar.MONTH);
        z = hoje.get(Calendar.YEAR);
        if(dia < x && mes <= y + 1 && ano <= z){
            return true;
        }
        return false;
    }

    public boolean diaRecente(int dia){
        int x;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        if(dia >= x){
            return true;
        }
        return false;
    }

    public boolean diaDaSemana(int dia, int mes, int ano){
        int x, y, z;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        y = hoje.get(Calendar.MONTH);
        z = hoje.get(Calendar.YEAR);
        int auxX = x;
        int auxY = y + 1;
        if(auxX > 24){
            auxX = (x+7)-31;
            if(auxY == 12) {
                auxY = 1;
            } else {
                auxY = y + 1;
            }
        }
        if((dia != x) && (((dia < x + 7) && mes == y + 1) || ((dia < auxX) && ((mes == auxY) || (mes == auxY + 1))) && ano == z)) {
            return true;
        }
        return false;
    }

    public boolean diaDoMes(int dia,int mes){
        int x,y;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.DAY_OF_MONTH);
        y = hoje.get(Calendar.MONTH) +1; //primeiro mes = 0
        if(dia >= x+7 && mes == y ){
            return true;
        }
        return false;
    }

    public boolean diaDoAno(int mes,int ano){
        int x,y;
        Calendar hoje = Calendar.getInstance();
        x = hoje.get(Calendar.MONTH)+1;//primeiro mes = 0
        y = hoje.get(Calendar.YEAR);
        if(mes > x + 1 && ano == y ){
            return true;
        }
        return false;
    }


    private void listEventos(String resposta){
        Eventos evento = null;
        //EventosCategoria evt = new EventosCategoria();
        try{
            String json = null;

            json = resposta;
            Log.i("XAMPSON", json);

            JSONArray jA = new JSONArray(json);
            for(int i = 0; i < jA.length(); i++){

                evento = new Eventos(jA.getJSONObject(i).getInt("codigo"),
                        jA.getJSONObject(i).getString("caminho"),
                        jA.getJSONObject(i).getString("nome"),
                        jA.getJSONObject(i).getInt("dia"),
                        jA.getJSONObject(i).getInt("mes"),
                        jA.getJSONObject(i).getInt("ano"),
                        jA.getJSONObject(i).getString("descricao"),
                        "Recem adicionados");

                if(evento == null){
                    eventos.clear();
                } else {
                    eventos.add(0,evento);
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if(diaAnterior(ev.getDia(),ev.getMes(), ev.getAno())){
                    ev = null;
                } else if (diaRecente(ev.getDia())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Recem adicionados", ev.getDescricao()));//categoria: hoje
                    break;
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if(diaAnterior(ev.getDia(),ev.getMes(), ev.getAno())){
                    ev = null;
                } else if (diaDeHoje(ev.getDia(),ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Hoje", ev.getDescricao()));//categoria: hoje
                    break;
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if(diaAnterior(ev.getDia(),ev.getMes(), ev.getAno())){
                    ev = null;
                } else if (diaDaSemana(ev.getDia(),ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Próximos 7 dias", ev.getDescricao()));//categoria: Próximos 7 dias
                    break;
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if(diaAnterior(ev.getDia(),ev.getMes(), ev.getAno())){
                    ev = null;
                } else if (diaDoMes(ev.getDia(), ev.getMes())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Neste Mês", ev.getDescricao()));//categoria: mes
                    break;
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if(diaAnterior(ev.getDia(),ev.getMes(), ev.getAno())){
                    ev = null;
                } else if (diaDoAno(ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Neste Ano", ev.getDescricao()));//categoria: ano
                    break;
                }
            }
            if (listaEventosCategoria == null) {
                Toast.makeText(getApplicationContext(), "Não há eventos disponiveis", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e){
            eventos.clear();
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Exceção: "+ e.getMessage());
        }
    }

    JSONTask jsonTask;

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

    public void iniciarUpload(String url, String tipo, String json) {
        if (jsonTask == null ||  jsonTask.getStatus() != AsyncTask.Status.RUNNING) {
            jsonTask = new JSONTask();
            jsonTask.execute(url, tipo, json);
        }
    }


    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
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
            listEventos(result);
        }
    }
}