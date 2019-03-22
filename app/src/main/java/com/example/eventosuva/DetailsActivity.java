package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventosuva.Model.DateModel;
import com.example.eventosuva.Model.Eventos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    DateModel dateModel;
    ImageView imagem;
    TextView text,data;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;
    Bitmap bitmap;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
        text.setText(evento.getNome());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(evento.getDataEvento());
        data.setText("Data do Evento: " + dateModel.convertDateToShow(strDate) + "\nDescrição do Evento: " + evento.getDescricao());
    }

    public void setup(){
        setContentView(R.layout.activity_eventos_detalhes);
        imagem = findViewById(R.id.imagemDetalhe);
        text = findViewById(R.id.tituloDetalhe);
        data = findViewById(R.id.informacoesDetalhe);
        dateModel = new DateModel();
    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(DetailsActivity.this, ImageFullsizeActivity.class);
        intent.putExtra("position",position);
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();

        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageInByte = baos.toByteArray();*/

        //intent.putExtra("bitmap", imageInByte);
        intent.putParcelableArrayListExtra("evento",eventos);
        startActivity(intent);
    }
}