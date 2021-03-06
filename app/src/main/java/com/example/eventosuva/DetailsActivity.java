package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    ImageView imagem;
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

        Glide.with(this).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(imagem);
        text.setText(evento.getNome());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(evento.getDataEvento());
        data.setText("Data do Evento: " + convertDateToShow(strDate) + "\nDescrição do Evento: " + evento.getDescricao());
    }

    public String convertDateToShow(String strDate){//converter data do banco para a habitual brasileira
        // formato de entrada deve ser 2017-01-17
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // sem argumentos, pega o formato do sistema para exibir a data
        SimpleDateFormat dateFormatOut = new SimpleDateFormat();
        // com argumentos formato e locale a saida e sempre a mesma
        //  SimpleDateFormat dateFormatOut = new SimpleDateFormat("dd-MM-yy", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormatIn.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return stringToSpace(String.valueOf(dateFormatOut.format(convertedDate)));
    }

    private String stringToSpace(String string){

        int spaceIndex = string.indexOf(" ");
        if(spaceIndex > 0) {
            string = string.substring(0, spaceIndex);
        }
        return string;
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