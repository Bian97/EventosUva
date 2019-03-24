package com.example.eventosuva.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Bian on 26/11/2017.
 */

public class Eventos implements Parcelable {
    private int codigo;
    private String caminho, nome;
    private String categoria, descricao;
    private Date dataEvento, dataPostado;

    public Eventos(int codigo, String caminho, String nome, String descricao, String categoria, Date dataEvento, Date dataPostado) {
        this.codigo = codigo;
        this.caminho = caminho;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.dataPostado = dataPostado;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCaminho() {
        return caminho;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public Date getDataPostado() {
        return dataPostado;
    }

    protected Eventos(Parcel in) {
        codigo = in.readInt();
        caminho = in.readString();
        nome = in.readString();
        descricao = in.readString();
        categoria = in.readString();
        dataEvento = (java.util.Date) in.readSerializable();
        dataPostado = (java.util.Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(caminho);
        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeString(categoria);
        dest.writeSerializable(dataEvento);
        dest.writeSerializable(dataPostado);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Eventos> CREATOR = new Creator<Eventos>() {
        @Override
        public Eventos createFromParcel(Parcel in) {
            return new Eventos(in);
        }

        @Override
        public Eventos[] newArray(int size) {
            return new Eventos[size];
        }
    };
}