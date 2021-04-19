package com.example.eventosuva.ui.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventosuva.ui.image.ImageActivity;
import com.example.eventosuva.util.DateUtil;
import com.example.eventosuva.model.Eventos;
import com.example.eventosuva.R;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

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
        Glide.with(this).load("http://cadier.com.br/uvapp/Imagens/"+evento.getCaminho()).into(imagem);
        text.setText(evento.getNome());

        SimpleDateFormat simpleDateFormat = DateUtil.createDateFormat();
        String campus = "";
        switch (evento.getCampus()) {
            case 0:
                campus = "Tijuca";
                break;
            case 1:
                campus = "Barra da Tijuca";
                break;
            case 2:
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
    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(DetailsActivity.this, ImageActivity.class);
        intent.putExtra("caminho", evento.getCaminho());
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();
        startActivity(intent);
    }
}