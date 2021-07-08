package com.example.eventosuva.ui.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventosuva.R;
import com.example.eventosuva.ui.grid.GridActivity;


public class MenuActivity extends AppCompatActivity implements MenuContract.onGetEventsListener {

    MenuPresenter presenter;
    ProgressDialog progressDialog;
    String json;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();
        presenter.getEventsList(this);
    }

    public void setup() {
        presenter = new MenuPresenter();
        setContentView(R.layout.fragment_category);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(topToolBar);
    }

    public void nextActivity(int position) {
        Intent intent = new Intent(MenuActivity.this, GridActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("json", json);
        startActivity(intent);
    }

    public void onClick(View view) {
        int position;
        switch (view.getId()) {
            case R.id.clNewEvents:
                position = 0;
                break;
            case R.id.clTodayEvents:
                position = 1;
                break;
            case R.id.clWeekEvents:
                position = 2;
                break;
            case R.id.clMonthEvents:
                position = 3;
                break;
            case R.id.clYearEvents:
                position = 4;
                break;
            default:
                return;
        }
        nextActivity(position);
    }

    public void reloadActivity(View view){
        Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoadingBegin() {
        progressDialog = ProgressDialog.show(MenuActivity.this, "Aguarde um pouco.", "Carregando informações...", false, false);
    }

    @Override
    public void onLoadingFailure() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet e reinicie o aplicativo!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadingSuccess(String json) {
        this.json = json;
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Eventos atualizados!", Toast.LENGTH_LONG).show();
    }
}