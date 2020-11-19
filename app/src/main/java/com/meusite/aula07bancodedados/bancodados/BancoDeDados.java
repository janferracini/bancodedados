package com.meusite.aula07bancodedados.bancodados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados {
    //controle da instancia do DB
    private SQLiteDatabase db;

    //vai existir apenas uma na memória
    private static final BancoDeDados bancoDeDados = new BancoDeDados();

    //pega a instancia ativa do DB
    public static BancoDeDados getInstance() {
        return bancoDeDados;
    }

    //abrir ou criar o DB
    public void abrirBanco(Context context){
        if (db == null){ //se não existir
                db = context.openOrCreateDatabase("dbAula.db", context.MODE_PRIVATE, null);
                // nome.db - tipo de abertura (privado) - retorno(?)
        }
    }

    public  void  addColuna(String tabela, String coluna, String tipo, String valorPadrao) {
        if (existeColuna(tabela, coluna) == false) {
            String padrao = "";
            if (valorPadrao.isEmpty() == false)
                padrao = "DEFAULT" + valorPadrao;
            execSql("ALTER TABLE " + tabela + "ADD COLUMN " + coluna + "" + tipo + " " + padrao);
        }
    }

    private boolean existeColuna(String tabela, String coluna){
        try{
            Cursor cursor = execBusca("SELECT * FROM " + tabela + " LIMIT 0");
            return cursor.getColumnIndex(coluna) >= 0;
//            if (cursor.getColumnIndex(coluna >= 0))
//                return true;
//            else
//                return false;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return  false;
    }

    public void fecharBanco(){
        try {
            if (db != null) { //se existir o DB
                db.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void execSql(String sql){
        try {
            if (db != null)
                db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor execBusca(String sql){
        try {
            if (db != null)
                return db.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
