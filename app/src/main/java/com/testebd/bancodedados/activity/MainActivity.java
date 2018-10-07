package com.testebd.bancodedados.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.testebd.bancodedados.R;
import com.testebd.bancodedados.adapter.ContatoAdapter;
import com.testebd.bancodedados.helper.ContatoDAO;
import com.testebd.bancodedados.helper.DbHelper;
import com.testebd.bancodedados.helper.RecyclerItemClickListener;
import com.testebd.bancodedados.model.Contato;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContatoAdapter contatoAdapter;
    private List<Contato> listaContatos = new ArrayList<>();
    private Contato contatoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(
                                                    getApplicationContext(),
                                                    recyclerView,
                                                    new RecyclerItemClickListener.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(View view, int position) {

                                                                Contato contatoSelecionado = listaContatos.get( position );
                                                                Intent intent = new Intent(MainActivity.this, AdicionaContatoActivity.class);
                                                                intent.putExtra("contatoSelecionado", contatoSelecionado);
                                                                startActivity( intent );

                                                            }

                                                            @Override
                                                            public void onLongItemClick(View view, int position) {
                                                                contatoSelecionado = listaContatos.get( position );
                                                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                                                dialog.setTitle("Confirmar exclusão");
                                                                dialog.setMessage("Deseja excluir o contato: " + contatoSelecionado.getNomeContato() + "?" );

                                                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        ContatoDAO contatoDAO = new ContatoDAO( getApplicationContext() );
                                                                        if ( contatoDAO.deletar( contatoSelecionado ) ) {
                                                                            carregarListaContatos();
                                                                            Toast.makeText( getApplicationContext(), "Sucesso ao deletar contato!" , Toast.LENGTH_SHORT ).show();
                                                                        }
                                                                        else {
                                                                            Toast.makeText( getApplicationContext(), "Erro ao deletar contato!" , Toast.LENGTH_SHORT ).show();
                                                                        }
                                                                    }
                                                                });

                                                                dialog.setNegativeButton("Não", null) ;
                                                                dialog.create();
                                                                dialog.show();

                                                            }

                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                            }
        }
        ));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionaContatoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaContatos (){
        //Listar tarefas
/*
        Contato contato1 = new Contato();
        contato1.setNomeContato("Teste1");
        contato1.setTelefoneContato("319123456789");
        listaContatos.add(contato1);

        Contato contato2 = new Contato();
        contato2.setNomeContato("Teste2");
        contato2.setTelefoneContato("319123456789");
        listaContatos.add(contato2);
*/
        ContatoDAO contatoDAO = new ContatoDAO( getApplicationContext() );
        listaContatos = contatoDAO.listar();


        //Exibe Lista de tarefas no RecyclerView

        //COnfigurar um adaoter
        contatoAdapter = new ContatoAdapter( listaContatos );

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( contatoAdapter );
    }

    @Override
    protected void onStart() {
        carregarListaContatos();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
