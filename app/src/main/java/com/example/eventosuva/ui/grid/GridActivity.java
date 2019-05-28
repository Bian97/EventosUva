package com.example.eventosuva.ui.grid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.eventosuva.ui.grid.GridContract.IGridPresenter;
import com.example.eventosuva.ui.details.DetailsActivity;
import com.example.eventosuva.ui.grid.adapter.GridAdapter;
import com.example.eventosuva.model.Eventos;
import com.example.eventosuva.R;

import java.util.ArrayList;

/**
 * Created by Bian on 21/11/2017.
 */

public class GridActivity extends AppCompatActivity implements GridContract.IGridActivity {

    IGridPresenter iGridPresenter;
    GridView gridview;
    int position;
    String json;

    ArrayList<Eventos> eventos;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();
        position = getIntent().getIntExtra("position",-1);
        json = getIntent().getStringExtra("json");
        eventos = iGridPresenter.createList(position,json);
        gridview.setAdapter(new GridAdapter(GridActivity.this, R.layout.activity_grid_image, eventos));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridActivity.this, DetailsActivity.class);
                intent.putExtra("evento",eventos.get(position));
                startActivity(intent);
            }
        });
    }

    public void setup(){
        setContentView(R.layout.fragment_grid_view);
        gridview = findViewById(R.id.gridview);
        iGridPresenter = new GridPresenter(this);
        eventos = new ArrayList<>();
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