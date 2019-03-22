package com.example.eventosuva.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Bian on 26/11/2017.
 */

public class Eventos implements Parcelable {
    private int codigo, campus;
    private String caminho, nome;
    private String categoria, descricao, curso;
    private Date dataEvento, dataPostado;

    public Eventos(int codigo, String caminho, String nome, String descricao, String categoria, Date dataEvento, Date dataPostado, String curso, int campus) {
        this.codigo = codigo;
        this.caminho = caminho;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.dataPostado = dataPostado;
        this.curso = curso;
        this.campus = campus;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Date getDataPostado() {
        return dataPostado;
    }

    public void setDataPostado(Date dataPostado) {
        this.dataPostado = dataPostado;
    }

    public int getCampus() {
        return campus;
    }

    public void setCampus(int campus) {
        this.campus = campus;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    protected Eventos(Parcel in) {
        codigo = in.readInt();
        caminho = in.readString();
        nome = in.readString();
        descricao = in.readString();
        categoria = in.readString();
        dataEvento = (java.util.Date) in.readSerializable();
        dataPostado = (java.util.Date) in.readSerializable();
        curso = in.readString();
        campus = in.readInt();
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
        dest.writeString(curso);
        dest.writeInt(campus);
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