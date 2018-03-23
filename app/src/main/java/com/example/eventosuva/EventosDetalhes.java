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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EventosDetalhes extends AppCompatActivity {
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

        new PegaImagemUnidade(){
            @Override
            protected void onPostExecute(Eventos evento){
                super.onPostExecute(evento);
                imagem.setImageBitmap(evento.getImagem());
                eventos.get(position).setImagem(evento.getImagem());
                text.setText(evento.getNome());
                String dataS = evento.getDia()+"/"+evento.getMes()+"/"+evento.getAno();
                data.setText("Data do Evento: " + dataS + "\nDescrição do Evento: " + evento.getDescricao());
                progressDialog.dismiss();
            }
        }.execute(evento);

        //imagem.setImageUrl(evento.getCaminho());

    }

    public void onImagemDetalhesClick(View view) {
        Intent intent = new Intent(EventosDetalhes.this,ImageFullsize.class);
        intent.putExtra("position",position);
        imagem.buildDrawingCache();
        bitmap = imagem.getDrawingCache();
        Log.d("XAMPSONBITMAP", String.valueOf(bitmap));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        intent.putExtra("bitmap", imageInByte);
        intent.putParcelableArrayListExtra("evento",eventos);
        startActivity(intent);
    }

    public class PegaImagemUnidade extends AsyncTask<Eventos, String, Eventos> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(EventosDetalhes.this,"Aguarde um pouco.", "Carregando dados...", false, false);
        }

        @Override
        protected Eventos doInBackground(Eventos... evento) {
            try {
                Bitmap aux = null;
                String arquivo = evento[0].getCaminho();
                arquivo = arquivo.substring(arquivo.lastIndexOf("/") + 1);
                Log.d("XAMPSON", arquivo);
                URL url = new URL("http://profsicsu.com.br/prototipos/eventosUva/pegaImagem.php?arquivo=" + arquivo);
                aux = BitmapFactory.decodeStream((InputStream) url.openStream());
                evento[0].setImagem(aux);
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return evento[0];
        }
    }
}