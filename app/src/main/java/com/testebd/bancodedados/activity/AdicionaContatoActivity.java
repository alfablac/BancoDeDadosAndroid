package com.testebd.bancodedados.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.testebd.bancodedados.R;
import com.testebd.bancodedados.helper.ContatoDAO;
import com.testebd.bancodedados.model.Contato;

public class AdicionaContatoActivity extends AppCompatActivity {

    private TextInputEditText editNomeContato;
    private TextInputEditText editTelefoneContato;

    private Contato contatoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_contato);

        editNomeContato = findViewById(R.id.textNomeContato);
        editTelefoneContato = findViewById(R.id.textTelefoneContato);

        contatoAtual = (Contato) getIntent().getSerializableExtra( "contatoSelecionado" );

        if ( contatoAtual != null ) {
            editNomeContato.setText( contatoAtual.getNomeContato() );
            editTelefoneContato.setText( contatoAtual.getTelefoneContato() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_contato, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.itemSalvar:
                ContatoDAO contatoDAO = new ContatoDAO( getApplicationContext() );


                if ( contatoAtual != null ){

                    String nomeContato = editNomeContato.getText().toString();
                    String telefoneContato = editTelefoneContato.getText().toString();

                    if ( !nomeContato.isEmpty() ) {
                        Contato contato = new Contato();
                        contato.setNomeContato( nomeContato );
                        contato.setId( contatoAtual.getId() );
                        contato.setTelefoneContato( telefoneContato );
                        if ( contatoDAO.atualizar( contato ) ) {
                            finish();
                            Toast.makeText( getApplicationContext(), "Sucesso ao atualizar contato!" , Toast.LENGTH_SHORT ).show();
                        }
                        else {
                            Toast.makeText( getApplicationContext(), "Erro ao atualizar contato!" , Toast.LENGTH_SHORT ).show();
                        }
                    }
                } else {

                    String nomeContato = editNomeContato.getText().toString();
                    String telefoneContato = editTelefoneContato.getText().toString();

                    if ( !nomeContato.isEmpty() ) {
                        Contato contato = new Contato();
                        contato.setNomeContato( nomeContato );
                        contato.setTelefoneContato( telefoneContato );
                        if ( contatoDAO.salvar( contato ) ) {
                            finish();
                            Toast.makeText( getApplicationContext(), "Sucesso ao salvar contato!" , Toast.LENGTH_SHORT ).show();
                        }
                        else {
                            Toast.makeText( getApplicationContext(), "Erro ao salvar contato!" , Toast.LENGTH_SHORT ).show();
                        }
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

