package com.example.eventosuva.ui.grid;

import android.util.Log;

import com.example.eventosuva.util.DateUtil;
import com.example.eventosuva.model.Eventos;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class GridPresenter implements GridContract.Presenter {

    private ArrayList<Eventos> eventos = new ArrayList<>();

    public GridPresenter() {
    }

    @Override
    public ArrayList<Eventos> createList(GridContract.onCreateListListener listener, int choice, String json) {
        Eventos evento;
        try {
            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {
                String dataEvento = jA.getJSONObject(i).getString("DataEvento");
                String dataPostado = jA.getJSONObject(i).getString("DataPostado");
                Date data1 = DateUtil.convertStringToData(dataEvento);
                Date data2 = DateUtil.convertStringToData(dataPostado);

                evento = new Eventos(jA.getJSONObject(i).getInt("IdEvento"),
                        jA.getJSONObject(i).getString("Caminho"),
                        jA.getJSONObject(i).getString("Nome"),
                        jA.getJSONObject(i).getString("Descricao"),
                        "Recem adicionados", data1, data2,
                        jA.getJSONObject(i).getString("Curso"),
                        jA.getJSONObject(i).getInt("Campus"));
                if (choice == 0) {
                    if (DateUtil.isRecent(data2)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 1) {
                    if (DateUtil.isToday(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 2) {
                    if (DateUtil.isWeek(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 3) {
                    if (DateUtil.isMonth(data1)) {
                        eventos.add(0, evento);
                    }
                } else if (choice == 4) {
                    if (DateUtil.isYear(data1)) {
                        eventos.add(0, evento);
                    }
                }
                if(eventos == null){
                    listener.onCreateListChoiceEmpty();
                }
                Log.e("XAMPSON", evento.getNome());
            }
        } catch (JSONException e) {
            eventos.clear();
            e.printStackTrace();
            listener.onCreateListError(e.getMessage());
        } catch (NullPointerException e) {
            listener.onCreateListEmpty();
        } catch (Exception e){
            listener.onCreateListError(e.getMessage());
        }
        return eventos;
    }
}
