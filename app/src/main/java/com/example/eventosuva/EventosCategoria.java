package com.example.eventosuva;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.controle.EventosDAO;
import com.example.eventosuva.modelo.Eventos;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DrGreend on 19/12/2017.
 */

public class EventosCategoria extends AppCompatActivity{

    ArrayList<Eventos> listaEventosCategoria = new ArrayList<>();
    private ArrayList<Eventos> eventos = new ArrayList<Eventos>();
    private int pos;
    Bitmap imagem;
    Eventos evento;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Log.e("XAMPSON", "Large screen");
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            Log.e("XAMPSON", "Normal screen");
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            Log.e("XAMPSON", "Small screen");
            setContentView(R.layout.activity_grid_categoria);
        }
        else {
            Log.e("XAMPSON", "Nenhuma das screens");
            setContentView(R.layout.activity_grid_categoria);
        }
        //setContentView(R.layout.activity_grid_categoria);

        Toast.makeText(this, "Criado por Bian Medeiros e Victor Franklin", Toast.LENGTH_LONG).show();
        try {
            String chamadaWs;
            Calendar hoje = Calendar.getInstance();
            int m = hoje.get(Calendar.MONTH) +1;
            int a = hoje.get(Calendar.YEAR);

            chamadaWs = "http://profsicsu.com.br/prototipos/eventosUva/wsListData.php?mes="+m+"&ano="+a;
            System.out.println("CHAMADAWS: "+chamadaWs);

           iniciarDownload(chamadaWs, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private void listEventos(String resposta) {
         evento = null;
        //EventosCategoria evt = new EventosCategoria();
        try {
            String json = null;

            json = resposta;
            Log.i("XAMPSON", json);

            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {

                evento = new Eventos(jA.getJSONObject(i).getInt("codigo"),
                        jA.getJSONObject(i).getString("caminho"),
                        jA.getJSONObject(i).getString("nome"),
                        jA.getJSONObject(i).getInt("dia"),
                        jA.getJSONObject(i).getInt("mes"),
                        jA.getJSONObject(i).getInt("ano"),
                        jA.getJSONObject(i).getString("descricao"),
                        "Recem adicionados");

                if (evento == null) {
                    eventos.clear();
                } else {
                    eventos.add(0, evento);
                }
            }
            progressDialog.dismiss();
            //for(int i = 0; i < eventos.size(); i++){
                PegaImagemCategoria pegaImagemCategoria = new PegaImagemCategoria();
                pegaImagemCategoria.execute();

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDia(), ev.getMes(), ev.getAno())) {
                    ev = null;
                } else if (diaRecente(ev.getDia())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Recem adicionados", ev.getDescricao()));
                    break;
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDia(), ev.getMes(), ev.getAno())) {
                    ev = null;
                } else if (diaDeHoje(ev.getDia(), ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Hoje", ev.getDescricao()));//categoria: hoje
                    break;
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDia(), ev.getMes(), ev.getAno())) {
                    ev = null;
                } else if (diaDaSemana(ev.getDia(), ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Próximos 7 dias", ev.getDescricao()));//categoria: Próximos 7 dias
                    break;
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDia(), ev.getMes(), ev.getAno())) {
                    ev = null;
                } else if (diaDoMes(ev.getDia(), ev.getMes())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Neste Mês", ev.getDescricao()));//categoria: mes
                    break;
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (diaAnterior(ev.getDia(), ev.getMes(), ev.getAno())) {
                    ev = null;
                } else if (diaDoAno(ev.getMes(), ev.getAno())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDia(), ev.getMes(), ev.getAno(), "Neste Ano", ev.getDescricao()));//categoria: ano
                    break;
                }
            }
            if (listaEventosCategoria == null) {
                Toast.makeText(getApplicationContext(), "Não há eventos disponiveis", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
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
                Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet e reabra o aplicativo!", Toast.LENGTH_LONG);
            }
        }
    }
    public class PegaImagemCategoria extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(EventosCategoria.this, "Aguarde um pouco.", "Baixando Imagens...", false, false);
        }

        @Override
        protected String doInBackground(String... evento) {
            String status = null;
            try {
                for(int i = 0; i < eventos.size(); i++) {
                    Bitmap aux = null;
                    String nomeArquivo = eventos.get(i).getCaminho();
                    nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
                    Log.d("XAMPSON", nomeArquivo);
                    URL url = new URL("http://profsicsu.com.br/prototipos/eventosUva/pegaImagem.php?arquivo=" + nomeArquivo);
                    aux = BitmapFactory.decodeStream((InputStream) url.openStream());

                    File direct = new File(Environment.getExternalStorageDirectory() + File.separator + "UVACategorias");

                    if(!direct.exists()){
                        File diretorioImagem = new File(Environment.getExternalStorageDirectory() + File.separator + "/UVACategorias/");
                        diretorioImagem.mkdirs();
                    }
                    File file = new File(new File(Environment.getExternalStorageDirectory() + File.separator +"/UVACategorias/"), nomeArquivo);
                    if(file.exists()){
                        file.delete();
                    }

                    FileOutputStream out = new FileOutputStream(file);
                    String extensaoArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf(".")+1);
                    Log.d("XAMPSON", extensaoArquivo);
                    if(extensaoArquivo.equalsIgnoreCase("jpg") || extensaoArquivo.equalsIgnoreCase("jpeg")) {
                        aux.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    } else if(extensaoArquivo.equalsIgnoreCase("png")){
                        aux.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                    }
                    eventos.get(i).setCaminho(file.getAbsolutePath());

                    if(eventos.get(i).getCaminho() != null){
                        status = "cheio";
                    } else {
                        status = null;
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                status = null;
            } catch (Exception e) {
                e.printStackTrace();
                status = null;
            }
            return status;
        }
        @Override
        protected void onPostExecute(String status){
            super.onPostExecute(status);
            progressDialog.dismiss();
            if(status == null){
                Toast.makeText(getApplicationContext(), "As imagems não foram baixadas completamente", Toast.LENGTH_SHORT).show();
            }
        }
    }
}