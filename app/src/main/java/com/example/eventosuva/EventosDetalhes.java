package com.example.eventosuva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class EventosDetalhes extends AppCompatActivity {
    SmartImageView imagem;
    TextView text,data;
    ArrayList<Eventos> eventos = new ArrayList<>();
    int position;

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
        intent.putExtra("position",position);
        intent.putParcelableArrayListExtra("evento",eventos);
        startActivity(intent);
    }
}
