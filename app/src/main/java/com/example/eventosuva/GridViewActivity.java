package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.eventosuva.Presenter.DatePresenter;
import com.example.eventosuva.adapter.EventosAdapter;
import com.example.eventosuva.Model.Eventos;

import java.util.ArrayList;

/**
 * Created by Bian on 21/11/2017.
 */

public class GridViewActivity extends AppCompatActivity {

    DatePresenter datePresenter;
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
        listaAuxiliar = getIntent().getParcelableArrayListExtra("auxiliar");
        inicializarEventos();

        gridview.setAdapter(new EventosAdapter(GridViewActivity.this, R.layout.activity_grid_image, listaEventosEscolha));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridViewActivity.this, DetailsActivity.class);
                intent.putExtra("position",position);
                intent.putParcelableArrayListExtra("evento",listaEventosEscolha);
                startActivity(intent);
            }
        });
    }

    public void setup(){
        setContentView(R.layout.activity_grid_view);
        gridview = findViewById(R.id.gridview);
        datePresenter = new DatePresenter();
    }

    public void inicializarEventos(){
        int escolha = pos;
        if(escolha == 0){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento;
                evento = listaAuxiliar.get(i);
                if(datePresenter.isYesterday(evento.getDataEvento())) {
                    listaAuxiliar.remove(i);
                } else if(datePresenter.isRecent(evento.getDataPostado())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 1){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(datePresenter.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(datePresenter.isToday(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 2){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(datePresenter.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(datePresenter.isWeek(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 3){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if((datePresenter.isYesterday(evento.getDataEvento()))){
                    listaAuxiliar.remove(i);
                } else if(datePresenter.isMonth(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 4){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(datePresenter.isYesterday(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(datePresenter.isYear(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
    }
}