package com.example.eventosuva.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Event implements Parcelable {
    private int id, campus;
    private String path, name;
    private String category, description, course;
    private Date eventDate;
    private Date publishDate;

    public Event(int id, String path, String name, String description, String category, Date eventDate, Date publishDate, String course, int campus) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.publishDate = publishDate;
        this.course = course;
        this.campus = campus;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public int getCampus() {
        return campus;
    }

    public String getCourse() {
        return course;
    }

    protected Event(Parcel in) {
        id = in.readInt();
        path = in.readString();
        name = in.readString();
        description = in.readString();
        category = in.readString();
        eventDate = (Date) in.readSerializable();
        publishDate = (java.util.Date) in.readSerializable();
        course = in.readString();
        campus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeSerializable(eventDate);
        dest.writeSerializable(publishDate);
        dest.writeString(course);
        dest.writeInt(campus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}