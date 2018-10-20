package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;
import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EventosDetalhes extends AppCompatActivity {
    SmartImageView imagem;
    TextView text,data;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;
    ProgressDialog progressDialog;
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

        imagem.setImageUrl(evento.getCaminho());
        text.setText(evento.getNome());
        String dataS = evento.getDia()+"/"+evento.getMes()+"/"+evento.getAno();
        data.setText("Data do Evento: " + dataS + "\nDescrição do Evento: " + evento.getDescricao());



    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(EventosDetalhes.this,ImageFullsize.class);
/*
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();
        Log.d("XAMPSONBITMAP", String.valueOf(bitmap));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        intent.putExtra("bitmap", imageInByte);
*/
        intent.putExtra("position",position);
        intent.putParcelableArrayListExtra("evento",eventos);
        startActivity(intent);
    }
}