package com.meusite.aula07bancodedados;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.meusite.aula07bancodedados.bancodados.TbAluno;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    Button btnAdd;
    ArrayList<HashMap<String, String>> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela cadastro
                startActivity(new Intent(MainActivity.this, CadAlunoActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarAlunos();
    }

    @Override //clique no item da listagem
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //caminho da tela
        Intent telaCadastro = new Intent(MainActivity.this, CadAlunoActivity.class);

        //seleciona o item clicado pela posição
        HashMap<String, String> item = listaAlunos.get(position);

        //cria parametros add os dados do item selecionado
        Bundle parametros = new Bundle();
        parametros.putString("nome", item.get("nome"));
        parametros.putString("ra", item.get("ra"));
        parametros.putString("cidade", item.get("cidade"));
        parametros.putString("estado", item.get("estado"));
        parametros.putString("id", item.get("id"));

        //add paramatros no caminho para a tela
        telaCadastro.putExtras(parametros);

        startActivity(telaCadastro);
    }

    private void buscarAlunos(){
        TbAluno tbAluno = new TbAluno(MainActivity.this);
        listaAlunos = tbAluno.buscar();

        //criar a listagem dos alunos qual contexto / array / modelo
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, listaAlunos, R.layout.lista_modelo,
                              new String[]{"nome", "ra", "cidade"}, new int[]{R.id.lblNome, R.id.lblRa, R.id.lblCidade});
        setListAdapter(adapter);
    }
}