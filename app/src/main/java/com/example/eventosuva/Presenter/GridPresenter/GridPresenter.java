package com.example.eventosuva.Presenter.GridPresenter;

import android.util.Log;

import com.example.eventosuva.Helper.DateHandler;
import com.example.eventosuva.Helper.IDateHandler;
import com.example.eventosuva.Model.Eventos;
import com.example.eventosuva.View.GridActivity.IGridActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class GridPresenter implements IGridPresenter {

    private IGridActivity iGridActivity;
    private IDateHandler date;
    private ArrayList<Eventos> eventos = new ArrayList<>();

    public GridPresenter(IGridActivity iGridActivity) {
        this.iGridActivity = iGridActivity;
        this.date = new DateHandler();
    }

    @Override
    public ArrayList<Eventos> createList(int choice, String json) {
        Eventos evento;
        try {
            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {
                String dataEvento = jA.getJSONObject(i).getString("dataevento");
                String dataPostado = jA.getJSONObject(i).getString("datapostado");
                Date data1 = date.convertStringToData(dataEvento);
                Date data2 = date.convertStringToData(dataPostado);

                evento = new Eventos(jA.getJSONObject(i).getInt("codigo"),
                        jA.getJSONObject(i).getString("caminho"),
                        jA.getJSONObject(i).getString("nome"),
                        jA.getJSONObject(i).getString("descricao"),
                        "Recem adicionados", data1, data2,
                        jA.getJSONObject(i).getString("curso"),
                        jA.getJSONObject(i).getInt("campus"));
                if (choice == 0) {
                    if (date.isRecent(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 1) {
                    if (date.isToday(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 2) {
                    if (date.isWeek(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 3) {
                    if (date.isMonth(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 4) {
                    if (date.isYear(data1)) {
                        eventos.add(0, evento);
                    }
                }
                Log.e("XAMPSON", evento.getNome());
            }
        } catch (JSONException e) {
            eventos.clear();
            e.printStackTrace();
            iGridActivity.onCreateListError(e.getMessage());
        } catch (NullPointerException e) {
            iGridActivity.onCreateListEmpty();
        } catch (Exception e){
            iGridActivity.onCreateListError(e.getMessage());
        }
        return eventos;
    }
}
