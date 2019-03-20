package com.example.eventosuva.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.GridViewActivity;
import com.example.eventosuva.controle.EventosDAO;
import com.example.eventosuva.Model.Eventos;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bian on 19/12/2017.
 */

public class MenuActivity extends AppCompatActivity implements IMenuActivity{

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
        iniciarChamada();
    }

    public void proximaActivity(int position){
        Intent intent = new Intent(MenuActivity.this, GridViewActivity.class);
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

    public String convertDateToShow(String strDate){//converter data do banco para a habitual brasileira
        // formato de entrada deve ser 2017-01-17
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // sem argumentos, pega o formato do sistema para exibir a data
        SimpleDateFormat dateFormatOut = new SimpleDateFormat();
        // com argumentos formato e locale a saida e sempre a mesma
        //  SimpleDateFormat dateFormatOut = new SimpleDateFormat("dd-MM-yy", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormatIn.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return stringToSpace(String.valueOf(dateFormatOut.format(convertedDate)));
    }

    private String stringToSpace(String string){

        int spaceIndex = string.indexOf(" ");
        if(spaceIndex > 0) {
            string = string.substring(0, spaceIndex);
        }
        return string;
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
                        "Recem adicionados", data1, data2);
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
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Recem adicionados", ev.getDataEvento(), ev.getDataPostado()));
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDeHoje(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Hoje", ev.getDataEvento(), ev.getDataPostado()));//categoria: hoje
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDaSemana(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Próximos 7 dias", ev.getDataEvento(), ev.getDataPostado()));//categoria: PrÃƒÂ³ximos 7 dias
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDoMes(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Mês", ev.getDataEvento(), ev.getDataPostado()));//categoria: mes
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDataEvento())) {
                    ev = null;
                } else if (diaDoAno(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Ano", ev.getDataEvento(), ev.getDataPostado()));//categoria: ano
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
            progressDialog = ProgressDialog.show(MenuActivity.this,"Aguarde um pouco.", "Carregando informações...", false, false);
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