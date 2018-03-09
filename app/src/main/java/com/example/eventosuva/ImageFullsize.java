package com.example.eventosuva;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by Victor on 01/12/2017.
 */

public class ImageFullsize extends AppCompatActivity {
    SmartImageView imagem;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullsize);
        imagem = findViewById(R.id.imagemFull);

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        imagem.setImageUrl(evento.getCaminho());
    }

}
