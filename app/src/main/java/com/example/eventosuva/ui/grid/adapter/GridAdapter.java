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

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


/**
 * Created by Victor on 21/11/2017.
 */

public class GridAdapter extends ArrayAdapter<Eventos> implements StickyListHeadersAdapter {

    private ArrayList<Eventos> eventsList;
    private Context context;
    private int layoutResourceId;
    private LayoutInflater inflater;

    public GridAdapter(Context context, int layoutResourceId, ArrayList<Eventos> eventsList) {
        super(context,layoutResourceId,eventsList);
        inflater = ((Activity) context).getLayoutInflater();
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
            holder = new ViewHolder();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder.nome = row.findViewById(R.id.titulo);
            holder.imagem = row.findViewById(R.id.imagem);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Eventos evento = eventsList.get(position);
        holder.nome.setText(evento.getNome());
        Glide.with(getContext()).load("http://cadier.com.br/uvapp/Imagens/"+evento.getCaminho()).into(holder.imagem);
        return row;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = "";

        switch (((Eventos)eventsList.get(position)).getCampus()) {
            case 0:
                headerText = "Tijuca";
                break;
            case 1:
                headerText = "Barra da Tijuca";
                break;
            case 2:
                headerText = "Cabo Frio";
        }
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return eventsList.get(position).getCampus();
    }

    class HeaderViewHolder {
        TextView text;
    }

    static class ViewHolder {
        TextView nome;
        ImageView imagem;
    }
}