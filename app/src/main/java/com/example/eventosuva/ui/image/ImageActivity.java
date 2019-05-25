package com.example.eventosuva.ui.image;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.eventosuva.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by Victor on 01/12/2017.
 */

public class ImageActivity extends AppCompatActivity {
    PhotoView imagem;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        String caminho = getIntent().getStringExtra("caminho");
        //Bitmap aux = BitmapFactory.decodeFile(evento.getCaminho());
        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+caminho).into(imagem);
    }

    public void setup(){
        setContentView(R.layout.activity_fullsize);
        imagem = findViewById(R.id.imagemFull);
    }
}