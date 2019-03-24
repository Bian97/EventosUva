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

import java.text.DateFormat;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(evento.getDataEvento());
        data.setText("Data do Evento: " + dateHandler.convertDateToShow(strDate) + "\nDescrição do Evento: " + evento.getDescricao());
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

        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageInByte = baos.toByteArray();*/

        //intent.putExtra("bitmap", imageInByte);
        startActivity(intent);
    }
}