package com.example.eventosuva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;

import java.util.ArrayList;

/**
 * Created by Victor on 21/11/2017.
 */

public class EventosGridView extends AppCompatActivity {

    GridView gridview;
    EventosCategoria ec = new EventosCategoria();
    ArrayList<Eventos> listaEventosEscolha = new ArrayList<>();
    ArrayList<Eventos> listaAuxiliar = new ArrayList<>();
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gridview = findViewById(R.id.gridview);

        pos = getIntent().getIntExtra("pos",-1);

        listaAuxiliar = getIntent().getParcelableArrayListExtra("auxiliar");
        inicializarEventos();

        gridview.setAdapter(new EventosAdapter(this, R.layout.activity_grid_image, listaEventosEscolha));

        //Ampliar imagem
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
                    if(ec.adicRecente(evento.getDia(),evento.getMes(), evento.getAno())){
                        listaEventosEscolha.add(evento);
                    } else {
                        listaAuxiliar.remove(i);
                    }
                }
            }
            if(escolha == 1){
                for(int i = 0; i < listaAuxiliar.size(); i++) {
                    Eventos evento = listaAuxiliar.get(i);
                    if(ec.diaAnterior(evento.getDia(),evento.getMes(), evento.getAno())){
                        listaAuxiliar.remove(i);
                    } else if(ec.diaDeHoje(evento.getDia(),evento.getMes(), evento.getAno())){
                        System.out.println(evento.getNome());
                        listaEventosEscolha.add(evento);
                    }
                }
            }
            if(escolha == 2){
                for(int i = 0; i < listaAuxiliar.size(); i++) {
                    Eventos evento = listaAuxiliar.get(i);
                    if(ec.diaAnterior(evento.getDia(),evento.getMes(), evento.getAno())){
                        listaAuxiliar.remove(i);
                    } else if(ec.diaDaSemana(evento.getDia(),evento.getMes(), evento.getAno())) {
                        System.out.println(ec.diaDaSemana(evento.getDia(),evento.getMes(), evento.getAno()));
                        System.out.println(evento.getNome());
                        listaEventosEscolha.add(evento);
                    }
                }
            }
            if(escolha == 3){
                for(int i = 0; i < listaAuxiliar.size(); i++) {
                    Eventos evento = listaAuxiliar.get(i);
                    if(ec.diaAnterior(evento.getDia(),evento.getMes(), evento.getAno())){
                        listaAuxiliar.remove(i);
                    } else if(ec.diaDoMes(evento.getDia(),evento.getMes())) {
                        listaEventosEscolha.add(evento);
                    }
                }
            }
            if(escolha == 4){
                for(int i = 0; i < listaAuxiliar.size(); i++) {
                    Eventos evento = listaAuxiliar.get(i);
                    if(ec.diaAnterior(evento.getDia(),evento.getMes(), evento.getAno())){
                        listaAuxiliar.remove(i); //confirmar se vão querer tirar os eventos que já foram no ano
                    } else if(ec.diaDoAno(evento.getMes(),evento.getAno())) {
                        listaEventosEscolha.add(evento);
                    }
                }
            }
    }
}