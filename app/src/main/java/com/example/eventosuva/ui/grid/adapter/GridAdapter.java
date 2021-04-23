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
import com.example.eventosuva.model.Event;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


/**
 * Created by Victor on 21/11/2017.
 */

public class GridAdapter extends ArrayAdapter<Event> implements StickyListHeadersAdapter {

    private ArrayList<Event> eventsList;
    private Context context;
    private int layoutResourceId;
    private LayoutInflater inflater;

    public GridAdapter(Context context, int layoutResourceId, ArrayList<Event> eventsList) {
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
            holder.name = row.findViewById(R.id.titulo);
            holder.image = row.findViewById(R.id.imagem);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Event event = eventsList.get(position);
        holder.name.setText(event.getName());
        Glide.with(getContext()).load("http://cadier.com.br/uvapp/Imagens/"+event.getPath()).into(holder.image);
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

        switch (((Event)eventsList.get(position)).getCampus()) {
            case 0:
                headerText = "NET";
                break;
            case 1:
                headerText = "Tijuca";
                break;
            case 2:
                headerText = "Barra da Tijuca";
                break;
            case 3:
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
        TextView name;
        ImageView image;
    }
}