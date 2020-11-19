package com.meusite.aula07bancodedados;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.meusite.aula07bancodedados.bancodados.TbAluno;
import com.meusite.aula07bancodedados.classes.Aluno;

public class CadAlunoActivity extends AppCompatActivity {

    int idAluno;

    EditText edtNome, edtRA, edtCidade, edtEstado;
    Button btnSalvar, btnCalcelar, btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_aluno);

        edtNome = findViewById(R.id.edtNome);
        edtRA = findViewById(R.id.edtRa);
        edtCidade = findViewById(R.id.edtCidade);
        edtEstado = findViewById(R.id.edtEstado);

        btnCalcelar = findViewById(R.id.btnCancelar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);

        idAluno = 0;
        btnExcluir.setVisibility(View.INVISIBLE);

        //cria a tela a partir do caminho da main com as infos
        Intent caminhoTela = getIntent();

        if (caminhoTela != null) {
            //recebe os parametros
            Bundle parametros = caminhoTela.getExtras();

            //se existir parametros - add os parametros nos campos
            if (parametros != null) {
                edtNome.setText(parametros.getString("nome"));
                edtRA.setText(parametros.getString("ra"));
                edtCidade.setText(parametros.getString("cidade"));
                edtEstado.setText(parametros.getString("estado"));
                idAluno = Integer.parseInt(parametros.getString("id"));

                //em caso de edição, mostr o btn excuir
                btnExcluir.setVisibility(View.VISIBLE);
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome.getText().toString().isEmpty()) {
                    mostrarMensagem("O campo Nome é obrigatório!");
                    return;
                }
                if (edtRA.getText().toString().isEmpty()) {
                    mostrarMensagem("O campo RA é obrigatório!");
                    return;
                }
                if (edtCidade.getText().toString().isEmpty()) {
                    mostrarMensagem("O campo Cidade é obrigatório!");
                    return;
                }
                if (edtEstado.getText().toString().isEmpty()) {
                    mostrarMensagem("O campo Estado é obrigatório!");
                    return;
                }

                try {
                    Aluno aluno = new Aluno();
                    aluno.id = idAluno;
                    aluno.nome = edtNome.getText().toString();
                    aluno.ra = edtRA.getText().toString();
                    aluno.cidade = edtCidade.getText().toString();
                    aluno.estado = edtEstado.getText().toString();

                    TbAluno tbAluno = new TbAluno(CadAlunoActivity.this);
                    tbAluno.salvar(aluno);

                    onBackPressed();
                }
                catch (Exception e) {
                    mostrarMensagem("Erro ao cadastrar o aluno: " + e.getMessage());
                }
            }
        });

        btnCalcelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(CadAlunoActivity.this);
                alerta.setTitle("Atenção");
                alerta.setMessage("Deseja realmente exluir esse registro?");
                alerta.setNegativeButton("Não", null);
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir();
                    }
                });
                alerta.show();
            }
        });
    }

    private void excluir() {
        try {
            TbAluno tbAluno = new TbAluno(CadAlunoActivity.this);
            tbAluno.excluir(idAluno);

            onBackPressed();

        } catch (Exception e) {
            mostrarMensagem("Erro ao Excluir" + e.getMessage());
        }
    }

    private void mostrarMensagem(String texto) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadAlunoActivity.this);
        alerta.setTitle("Atenção");
        alerta.setMessage(texto);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }
}