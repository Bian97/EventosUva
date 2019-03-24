package com.example.eventosuva.View.DetailsActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventosuva.View.ImageActivity.ImageActivity;
import com.example.eventosuva.Helper.DateHandler;
import com.example.eventosuva.Model.Eventos;
import com.example.eventosuva.R;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {
    DateHandler dateHandler;
    ImageView imagem;
    TextView text,data;
    Eventos evento;
    Bitmap bitmap;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        evento = getIntent().getParcelableExtra("evento");
        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
        text.setText(evento.getNome());

        SimpleDateFormat simpleDateFormat = dateHandler.createDateFormat();
        String campus = "";
        switch (evento.getCampus()) {
            case 1:
                campus = "Tijuca";
                break;
            case 2:
                campus = "Barra da Tijuca";
                break;
            case 3:
                campus = "Cabo Frio";
        }

        String string = "Campus do Evento: "+campus+"\n\nData do Evento: "
                + simpleDateFormat.format(evento.getDataEvento())+ "\n\nCurso do Evento: "
                + evento.getCurso() +"\n\nDescrição do Evento: " + evento.getDescricao();

        data.setText(string);
    }

    public void setup(){
        setContentView(R.layout.activity_eventos_detalhes);
        imagem = findViewById(R.id.imagemDetalhe);
        text = findViewById(R.id.tituloDetalhe);
        data = findViewById(R.id.informacoesDetalhe);
        dateHandler = new DateHandler();
    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(DetailsActivity.this, ImageActivity.class);
        intent.putExtra("caminho", evento.getCaminho());
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();
        startActivity(intent);
    }
}