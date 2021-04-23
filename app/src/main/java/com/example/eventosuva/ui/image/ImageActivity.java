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

    PhotoView image;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        String path = getIntent().getStringExtra("path");
        Glide.with(this).load("http://cadier.com.br/uvapp/Imagens/"+path).into(image);
    }

    public void setup(){
        setContentView(R.layout.activity_fullsize);
        image = findViewById(R.id.imagemFull);
    }
}