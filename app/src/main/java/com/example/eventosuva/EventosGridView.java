package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bian on 21/11/2017.
 */

public class EventosGridView extends AppCompatActivity {

    GridView gridview;
    EventosCategoria ec = new EventosCategoria();
    ArrayList<Eventos> listaEventosEscolha = new ArrayList<>();
    ArrayList<Eventos> listaAuxiliar = new ArrayList<>();
    int pos;
    ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gridview = findViewById(R.id.gridview);

        pos = getIntent().getIntExtra("pos",-1);
        listaAuxiliar = getIntent().getParcelableArrayListExtra("auxiliar");
        inicializarEventos();

        gridview.setAdapter(new EventosAdapter(EventosGridView.this, R.layout.activity_grid_image, listaEventosEscolha));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventosGridView.this, EventosDetalhes.class);
                intent.putExtra("position",position);
                intent.putParcelableArrayListExtra("evento",listaEventosEscolha);
                startActivity(intent);
            }
        });
    }

    public void inicializarEventos(){
        int escolha = pos;
        if(escolha == 0){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento;
                evento = listaAuxiliar.get(i);
                if(ec.diaAnterior(evento.getDataEvento())) {
                    listaAuxiliar.remove(i);
                } else if(ec.adicRecente(evento.getDataPostado())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 1){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(ec.diaAnterior(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(ec.diaDeHoje(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 2){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(ec.diaAnterior(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(ec.diaDaSemana(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 3){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if((ec.diaAnterior(evento.getDataEvento()))){
                    listaAuxiliar.remove(i);
                } else if(ec.diaDoMes(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
        if(escolha == 4){
            for(int i = 0; i < listaAuxiliar.size(); i++) {
                Eventos evento = listaAuxiliar.get(i);
                if(ec.diaAnterior(evento.getDataEvento())){
                    listaAuxiliar.remove(i);
                } else if(ec.diaDoAno(evento.getDataEvento())){
                    listaEventosEscolha.add(evento);
                }
            }
        }
    }
}