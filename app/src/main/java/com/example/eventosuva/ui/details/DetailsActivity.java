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
import com.example.eventosuva.model.Event;
import com.example.eventosuva.R;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView text, date;

    Event event;
    Bitmap bitmap;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();

        event = getIntent().getParcelableExtra("event");
        Glide.with(this).load("http://cadier.com.br/uvapp/Imagens/"+ event.getPath()).into(image);
        text.setText(event.getName());

        SimpleDateFormat simpleDateFormat = DateUtil.createDateFormat();
        String campus = "";
        switch (event.getCampus()) {
            case 0:
                campus = "NET";
                break;
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
                + simpleDateFormat.format(event.getEventDate())+ "\n\nCurso do Evento: "
                + event.getCourse() +"\n\nDescrição do Evento: " + event.getDescription();

        date.setText(string);
    }

    public void setup(){
        setContentView(R.layout.activity_eventos_detalhes);
        image = findViewById(R.id.imagemDetalhe);
        text = findViewById(R.id.tituloDetalhe);
        date = findViewById(R.id.informacoesDetalhe);
    }

    public void onImageDetailsClick(View view) {
        Intent intent = new Intent(DetailsActivity.this, ImageActivity.class);
        intent.putExtra("path", event.getPath());
        image.buildDrawingCache();
        bitmap = image.getDrawingCache();
        startActivity(intent);
    }
}