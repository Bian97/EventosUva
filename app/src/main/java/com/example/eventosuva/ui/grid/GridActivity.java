package com.example.eventosuva.ui.grid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.eventosuva.ui.details.DetailsActivity;
import com.example.eventosuva.ui.grid.adapter.GridAdapter;
import com.example.eventosuva.model.Event;
import com.example.eventosuva.R;
import com.example.eventosuva.util.SortByCampi;

import java.util.ArrayList;
import java.util.Collections;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Bian on 21/11/2017.
 */

public class GridActivity extends AppCompatActivity implements GridContract.onCreateListListener {

    StickyListHeadersListView listView;

    int position;
    String json;
    ArrayList<Event> events;
    GridPresenter presenter;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();
        position = getIntent().getIntExtra("position",-1);
        json = getIntent().getStringExtra("json");
        events = presenter.createList(this, position, json);

        Collections.sort(events, new SortByCampi());

        StickyListHeadersAdapter adapter = new GridAdapter(this, R.layout.activity_grid_image, events);
        listView.setAdapter(adapter);

        //listView.setAdapter(new GridAdapter(GridActivity.this, R.layout.activity_grid_image, eventos));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridActivity.this, DetailsActivity.class);
                intent.putExtra("event", events.get(position));
                startActivity(intent);
            }
        });
    }

    public void setup(){
        setContentView(R.layout.fragment_grid_view);
        //listView = findViewById(R.id.list);
        listView = (StickyListHeadersListView) findViewById(R.id.list);
        presenter = new GridPresenter();
        events = new ArrayList<>();
    }

    @Override
    public void onCreateListEmpty() {
        Toast.makeText(getApplicationContext(), "Não existem eventos disponiveis", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateListChoiceEmpty() {
        Toast.makeText(getApplicationContext(), "Não existem eventos nesta categoria", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateListError(String message) {
        Toast.makeText(getApplicationContext(), "Erro: "+message, Toast.LENGTH_SHORT).show();
    }
}