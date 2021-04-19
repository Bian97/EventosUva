package com.example.eventosuva.ui.grid.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventosuva.R;
import com.example.eventosuva.model.Eventos;

import java.util.ArrayList;


/**
 * Created by Victor on 21/11/2017.
 */

public class GridAdapter extends ArrayAdapter<Eventos> {

    private ArrayList<Eventos> eventsList;
    private Context context;
    private int layoutResourceId;

    public GridAdapter(Context context, int layoutResourceId, ArrayList<Eventos> eventsList) {
        super(context,layoutResourceId,eventsList);
        this.eventsList = eventsList;
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
        Eventos evento = eventsList.get(position);
        holder.nome.setText(evento.getNome());
        Glide.with(getContext()).load("http://cadier.com.br/uvapp/Imagens/"+evento.getCaminho()).into(holder.imagem);
        return row;
    }
    static class ViewHolder {
        TextView nome;
        ImageView imagem;
    }
}