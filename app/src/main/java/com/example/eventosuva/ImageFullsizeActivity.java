package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.eventosuva.Model.Eventos;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by Victor on 01/12/2017.
 */

public class ImageFullsizeActivity extends AppCompatActivity {
    PhotoView imagem;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        //Bitmap aux = BitmapFactory.decodeFile(evento.getCaminho());
        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
    }

    public void setup(){
        setContentView(R.layout.activity_fullsize);
        imagem = findViewById(R.id.imagemFull);
    }
}