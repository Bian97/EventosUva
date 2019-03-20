package com.example.eventosuva.adapter;

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
import com.example.eventosuva.Model.Eventos;

import java.util.ArrayList;


/**
 * Created by Victor on 21/11/2017.
 */

public class EventosAdapter extends ArrayAdapter<Eventos> {

    private ArrayList<Eventos> listaEventosAdapter = new ArrayList<>();
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
        ViewHolder holder = null;
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
        Glide.with(getContext()).load("http://sicsu.net/uvapps/Imagens/"+evento.getCaminho()).into(holder.imagem);
        return row;
    }
    static class ViewHolder {
        TextView nome;
        ImageView imagem;
    }
}