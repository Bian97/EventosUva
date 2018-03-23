package com.example.eventosuva;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

        //para usar imagem local, comentar PegaImagem e usar as duas linhas abaixo

        //byte[] imageByte = getIntent().getByteArrayExtra("bitmap");
        //bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        eventos = getIntent().getParcelableArrayListExtra("evento");
        position = getIntent().getIntExtra("position",0);
        Eventos evento = eventos.get(position);

        //imagem.setImageUrl(evento.getCaminho());
        Log.d("XAMPSONEVENTO", String.valueOf(evento.getImagem()));

        //new PegaImagemFull pega do servidor
        new PegaImagemFull(){
            @Override
            protected void onPostExecute(Eventos evento){
                super.onPostExecute(evento);
                imagem.setImageBitmap(evento.getImagem());
                progressDialog.dismiss();
            }
        }.execute(evento);
        //imagem.setImageBitmap(bitmap);
    }

    public class PegaImagemFull extends AsyncTask<Eventos, String, Eventos> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ImageFullsize.this,"Aguarde um pouco.", "Carregando dados...", false, false);
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