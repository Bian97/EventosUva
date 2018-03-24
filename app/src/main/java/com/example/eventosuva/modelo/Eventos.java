package com.example.eventosuva.modelo;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor on 26/11/2017.
 */

public class Eventos implements Parcelable {
    //private String nome,imagem, dia, mes, ano;
    private int codigo;
    private String caminho, nome;
    private int dia , mes, ano;
    private String categoria, descricao;

    public Eventos(int codigo, String caminho, String nome, int dia, int mes, int ano, String descricao, String categoria) {
        this.codigo = codigo;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.caminho = caminho;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
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

    protected Eventos(Parcel in) {
        codigo = in.readInt();
        caminho = in.readString();
        nome = in.readString();
        dia = in.readInt();
        mes = in.readInt();
        ano = in.readInt();
        descricao = in.readString();
        categoria = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(caminho);
        dest.writeString(nome);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(ano);
        dest.writeString(descricao);
        dest.writeString(categoria);
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