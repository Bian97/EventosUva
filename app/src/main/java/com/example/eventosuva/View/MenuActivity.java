package com.example.eventosuva.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.eventosuva.Presenter.MenuPresenter;
import com.example.eventosuva.R;
import com.example.eventosuva.GridViewActivity;

/**
 * Created by Bian on 19/12/2017.
 */

public class MenuActivity extends AppCompatActivity implements IMenuActivity{

    MenuPresenter menuPresenter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setup();
        menuPresenter.onGetRequest();
    }

    public void setup(){
        menuPresenter = new MenuPresenter(this);
        iniciarTela();
        Toast.makeText(this, R.string.app_creators , Toast.LENGTH_LONG).show();
    }

    public void iniciarTela(){
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            setContentView(R.layout.activity_grid_categoria);
        }
        else {
            setContentView(R.layout.activity_grid_categoria);
        }
    }

    public void proximaActivity(int position){
        Intent intent = new Intent(MenuActivity.this, GridViewActivity.class);
        intent.putExtra("pos",position);
        startActivity(intent);
    }

    public void onRecentesClick(View view) {
        proximaActivity(0);
    }

    public void onHojeClick(View view) {
        proximaActivity(1);
    }

    public void onSeteDiasClick(View view) {
        proximaActivity(2);
    }

    public void onMesClick(View view) {
        proximaActivity(3);
    }

    public void onAnoClick(View view) {
        proximaActivity(4);
    }

    @Override
    public void onLoadingBegin() {
        progressDialog = ProgressDialog.show(MenuActivity.this,"Aguarde um pouco.", "Carregando informações...", false, false);
    }

    @Override
    public void onLoadingFail() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet e reinicie o aplicativo!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadingFinish() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Eventos atualizados!", Toast.LENGTH_LONG).show();
    }
}