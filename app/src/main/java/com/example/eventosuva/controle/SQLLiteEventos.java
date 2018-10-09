package com.example.eventosuva.controle;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by DrGreend on 09/10/2018.
 */

public class SQLLiteEventos extends SQLiteOpenHelper{
    private static final String NOME_BANCO = "eventos.db";
    private static final String TABELA = "eventos";
    private static final String CODIGO = "codigo";
    private static final String NOME = "nome";
    private static final String DESCRICAO = "descricao";
    private static final String CATEGORIA = "categoria";
    private static final String DIA = "dia";
    private static final String MES = "mes";
    private static final String ANO = "ano";
    private static final String CAMINHO = "caminho";
    private static final int VERSAO = 1;

    public SQLLiteEventos(Context context){
        super(context, NOME_BANCO, null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA + " ("+CODIGO+" integer primary key, "+NOME+" text, "+DESCRICAO+" text, "+CATEGORIA+" text, "+DIA+" integer,"
        +MES+" integer, "+ANO+" integer, "+CAMINHO+" text)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
