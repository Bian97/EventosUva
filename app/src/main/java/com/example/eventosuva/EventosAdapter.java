package com.example.eventosuva;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drgreend.eventosuva.R;
import com.example.eventosuva.modelo.Eventos;
import com.loopj.android.image.SmartImageView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Victor on 21/11/2017.
 */

public class EventosAdapter extends ArrayAdapter<Eventos> {

    private ArrayList<Eventos> listaEventosAdapter;
    private Context context;
    private int layoutResourceId;

    public EventosAdapter(Context context, int layoutResourceId, ArrayList<Eventos> lista) {
        super(context,layoutResourceId,lista);
        listaEventosAdapter = lista;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            if(row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.nome = row.findViewById(R.id.titulo);
                holder.imagem = row.findViewById(R.id.imagem);
                row.setTag(holder);
            }
            else{
                holder = (ViewHolder) row.getTag();
            }
            Eventos evento = listaEventosAdapter.get(position);
            holder.nome.setText(evento.getNome());
<<<<<<< HEAD
            Glide.with(getContext()).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(holder.imagem);
=======
            holder.imagem.setImageUrl(evento.getCaminho());
>>>>>>> d7206bf044c93c2737d8768d5f61148c7cd9dd3e
            return row;
    }
    static class ViewHolder {
        TextView nome;
        SmartImageView imagem;
    }
}