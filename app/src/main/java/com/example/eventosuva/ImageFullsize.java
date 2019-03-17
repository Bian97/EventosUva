package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;
import com.github.chrisbanes.photoview.PhotoView;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Victor on 01/12/2017.
 */

public class ImageFullsize extends AppCompatActivity {
    PhotoView imagem;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullsize);
        imagem = findViewById(R.id.imagemFull);

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        //Bitmap aux = BitmapFactory.decodeFile(evento.getCaminho());
        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
    }
}