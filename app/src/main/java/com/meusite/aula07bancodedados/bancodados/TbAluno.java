package com.meusite.aula07bancodedados.bancodados;

import android.content.Context;
import android.database.Cursor;

import com.meusite.aula07bancodedados.classes.Aluno;

import java.util.ArrayList;
import java.util.HashMap;

public class TbAluno {

    public TbAluno(Context context){
        //abre a instancia já aberta
        BancoDeDados.getInstance().abrirBanco(context);
        String sql = "CREATE TABLE IF NOT EXISTS tbAluno (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "nome TEXT, ra TEXT, cidade TEXT, estado TEXT)";

        BancoDeDados.getInstance().execSql(sql);

        BancoDeDados.getInstance().addColuna("tbAluno", "estado", "TEXT", "");
    }

    public  void salvar(Aluno aluno){
        if (aluno.id > 0) //verifica se existe a id
            alterar(aluno);
        else
            inserir(aluno);
    }

    private void inserir(Aluno aluno){
        String sql = "INSERT INTO TbAluno (nome, ra, cidade, estado) VALUES (" +
                addAspas(aluno.nome)    + ", " +
                addAspas(aluno.ra)      + ", " +
                addAspas(aluno.cidade)  + ", " +
                addAspas(aluno.estado)  + ")"  ;
        BancoDeDados.getInstance().execSql(sql);
    }

    private void alterar(Aluno aluno){
        String sql = "UPDATE tbAluno SET " +
                " nome = "      + addAspas(aluno.nome)  + ", " +
                " ra = "        + addAspas(aluno.ra)    + ", " +
                " cidade = "    + addAspas(aluno.cidade)+ ", " +
                " estado = "    + addAspas(aluno.estado) +
                "WHERE id = "   + aluno.id;
        BancoDeDados.getInstance().execSql(sql);
    }

    public void excluir(int id){
        String sql ="DELETE FROM TbAluno WHERE id = " + id;
        BancoDeDados.getInstance().execSql(sql);
    }

    public ArrayList<HashMap<String, String>> buscar(){
        /*Cursor cursor = BancoDeDados.getInstance().getDb().query(
                "tbAluno", //tabela
                new String[]{"nome", "ra", "cidade"}, //colunas
                "", //condição (WHERE)
                null, //parametros da condição
                null, //agrupar por
                null, //where do agrupar
                "nome", //ordenar por
                null //limite );

        */
        ArrayList<HashMap<String, String>> listaAlunos = new ArrayList<>();

        Cursor cursor = BancoDeDados.getInstance().execBusca("SELECT * FROM tbAluno ORDER BY nome");

        if(cursor.getCount() > 0) {
            cursor.moveToFirst(); // vai para o 1º item

            //faz a leitura
            int indiceCampoId = cursor.getColumnIndex("id");
            int indiceCampoNome = cursor.getColumnIndex("nome");
            int indiceCampoRa = cursor.getColumnIndex("ra");
            int indiceCampoCidade = cursor.getColumnIndex("cidade");
            int indiceCampoEstado = cursor.getColumnIndex("estado");

            //cria o objeto aluno com as infos
            for (int i = 0; i < cursor.getCount(); i++ ){
                HashMap<String, String> aluno = new HashMap<>();
                aluno.put("id", cursor.getString(indiceCampoId));
                aluno.put("nome", cursor.getString(indiceCampoNome));
                aluno.put("ra", cursor.getString(indiceCampoRa));
                aluno.put("cidade", cursor.getString(indiceCampoCidade));
                aluno.put("estado", cursor.getString(indiceCampoEstado));

                //add no array
                listaAlunos.add(aluno);

                //pula para o próximo
                cursor.moveToNext();
            }
        }
        return listaAlunos;
    }

    private String addAspas(String valor){
        return "'" + valor + "'";
    }
}
