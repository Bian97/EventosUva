package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.eventosuva.modelo.Eventos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class EventosDetalhes extends AppCompatActivity {
    ImageView imagem;
    TextView text,data;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;
    Bitmap bitmap;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_detalhes);

        imagem = findViewById(R.id.imagemDetalhe);
        text = findViewById(R.id.tituloDetalhe);
        data = findViewById(R.id.informacoesDetalhe);

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
        text.setText(evento.getNome());
        Locale localeBR = new Locale("pt", "BR");
        SimpleDateFormat fmt = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", localeBR);
        String campus = "";
        switch (evento.getCampus()){
            case 1: campus = "Tijuca";
                    break;
            case 2: campus = "Barra da Tijuca";
                    break;
            case 3: campus = "Cabo Frio";
        }

        data.setText("Campus do Evento: "+campus+"\n\nData do Evento: " + fmt.format(evento.getDataEvento())+ "\n\nCurso do Evento: "+ evento.getCurso() +"\n\nDescrição do Evento: " + evento.getDescricao());
    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(EventosDetalhes.this,ImageFullsize.class);
        intent.putExtra("position",position);
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();

        intent.putParcelableArrayListExtra("evento",eventos);
        startActivity(intent);
    }
}