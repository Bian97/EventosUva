package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.eventosuva.Model.DateModel;
import com.example.eventosuva.Adapter.EventosAdapter;
import com.example.eventosuva.Model.Eventos;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bian on 21/11/2017.
 */

public class GridViewActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Eventos> listaEventosEscolha = new ArrayList<>();
    ArrayList<Eventos> listaAuxiliar = new ArrayList<>();

    int pos;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        pos = getIntent().getIntExtra("pos",-1);

        gridview.setAdapter(new EventosAdapter(GridViewActivity.this, R.layout.activity_grid_image, listaEventosEscolha));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridViewActivity.this, DetailsActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    public void setup(){
        setContentView(R.layout.activity_grid_view);
        gridview = findViewById(R.id.gridview);
    }


    //TODO: Move to presenter
    /*
    private ArrayList<Eventos> eventos = new ArrayList<>();
    private void createEventList(String json) {
        Eventos evento = null;
        try {
            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {
                String dataEvento = jA.getJSONObject(i).getString("dataevento");
                String dataPostado = jA.getJSONObject(i).getString("datapostado");
                Date data1 = dateModel.convertStringToData(dataEvento);
                Date data2 = dateModel.convertStringToData(dataPostado);

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

        } catch (JSONException e) {
            eventos.clear();
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Exceção: "+ e.getMessage());
        }
    }
    */
    /*
    ArrayList<Eventos> listaEventosCategoria = new ArrayList<>();
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (dateModel.isYesterday(ev.getDataEvento())) {
                    ev = null;
                } else if (dateModel.isRecent(ev.getDataPostado())){
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Recem adicionados", ev.getDataEvento(), ev.getDataPostado()));
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (dateModel.isYesterday(ev.getDataEvento())) {
                    ev = null;
                } else if (dateModel.isToday(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Hoje", ev.getDataEvento(), ev.getDataPostado()));//categoria: hoje
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (dateModel.isYesterday(ev.getDataEvento())) {
                    ev = null;
                } else if (dateModel.isWeek(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Próximos 7 dias", ev.getDataEvento(), ev.getDataPostado()));//categoria: PrÃƒÂ³ximos 7 dias
                }
            }
            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (dateModel.isYesterday(ev.getDataEvento())) {
                    ev = null;
                } else if (dateModel.isMonth(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Mês", ev.getDataEvento(), ev.getDataPostado()));//categoria: mes
                }
            }

            for (int j = 0; j < eventos.size(); j++) {
                Eventos ev = eventos.get(j);
                if (dateModel.isYesterday(ev.getDataEvento())) {
                    ev = null;
                } else if (dateModel.isYear(ev.getDataEvento())) {
                    listaEventosCategoria.add(new Eventos(ev.getCodigo(), ev.getCaminho(), ev.getNome(), ev.getDescricao(), "Neste Ano", ev.getDataEvento(), ev.getDataPostado()));//categoria: ano
                }
            }
            if (listaEventosCategoria == null) {
                Toast.makeText(getApplicationContext(), "Não existem eventos disponiveis", Toast.LENGTH_SHORT).show();
            }*/

    /*

    public void inicializarEventos(){
        int escolha = pos;
        if(escolha == 0){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento;
                evento = listaAuxiliar.get(i);
                if(dateModel.isYesterday(evento.getDataEvento())) {
                    listaAuxiliar.remove(i);
                } else if(dateModel.isRecent(evento.getDataPostado())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 1){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(dateModel.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(dateModel.isToday(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 2){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(dateModel.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(dateModel.isWeek(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 3){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if((dateModel.isYesterday(evento.getDataEvento()))){
                    listaAuxiliar.remove(i);
                } else if(dateModel.isMonth(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 4){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(dateModel.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(dateModel.isYear(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
    }*/
}