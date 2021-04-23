package com.example.eventosuva.ui.grid;

import android.util.Log;

import com.example.eventosuva.util.DateUtil;
import com.example.eventosuva.model.Event;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class GridPresenter implements GridContract.Presenter {

    private ArrayList<Event> events = new ArrayList<>();

    public GridPresenter() {
    }

    @Override
    public ArrayList<Event> createList(GridContract.onCreateListListener listener, int choice, String json) {
        Event event;
        try {
            JSONArray jA = new JSONArray(json);
            for (int i = 0; i < jA.length(); i++) {
                Date eventDate = DateUtil.convertStringToDateTime(jA.getJSONObject(i).getString("EventDate"));
                Date publishDate = DateUtil.convertStringToDate(jA.getJSONObject(i).getString("PublishDate"));

                event = new Event(jA.getJSONObject(i).getInt("IdEvent"),
                        jA.getJSONObject(i).getString("Path"),
                        jA.getJSONObject(i).getString("Name"),
                        jA.getJSONObject(i).getString("Description"),
                        "Recem adicionados", eventDate, publishDate,
                        jA.getJSONObject(i).getString("Course"),
                        jA.getJSONObject(i).getInt("Campus"));

                if (choice == 0) {
                    if (DateUtil.isRecent(publishDate)) {
                        events.add(0, event);
                    }
                } else if (choice == 1) {
                    if (DateUtil.isToday(eventDate)) {
                        events.add(0, event);
                    }
                } else if (choice == 2) {
                    if (DateUtil.isWeek(eventDate)) {
                        events.add(0, event);
                    }
                } else if (choice == 3) {
                    if (DateUtil.isMonth(eventDate)) {
                        events.add(0, event);
                    }
                } else if (choice == 4) {
                    if (DateUtil.isYear(eventDate)) {
                        events.add(0, event);
                    }
                }
                if(events == null){
                    listener.onCreateListChoiceEmpty();
                }
            }
        } catch (JSONException e) {
            events.clear();
            e.printStackTrace();
            listener.onCreateListError(e.getMessage());
        } catch (NullPointerException e) {
            listener.onCreateListEmpty();
        } catch (Exception e){
            listener.onCreateListError(e.getMessage());
        }
        return events;
    }
}