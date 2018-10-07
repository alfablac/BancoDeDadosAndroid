package com.testebd.bancodedados.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.testebd.bancodedados.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO implements IContatoDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public ContatoDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Contato contato) {

        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNomeContato());
        cv.put("telefone", contato.getTelefoneContato());

        try {
            escreve.insert( DbHelper.TABELA_CONTATOS, null, cv );
            Log.e("INFO ", "Sucesso ao salvar contato! ");
        } catch (Exception e) {
            Log.e("INFO ", "Erro ao salvar contato: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Contato contato) {

        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNomeContato() );
        cv.put("telefone", contato.getTelefoneContato() );

        try {
            String[] args = { contato.getId().toString() };
            escreve.update( DbHelper.TABELA_CONTATOS, cv, "id=?", args );
            Log.e("INFO ", "Sucesso ao atualizar contato! ");
        } catch (Exception e) {
            Log.e("INFO ", "Erro ao atualizar contato: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Contato contato) {



        try {
            String[] args = { contato.getId().toString() };
            escreve.delete( DbHelper.TABELA_CONTATOS, "id=?", args );
            Log.e("INFO ", "Sucesso ao deletar contato! ");
        } catch (Exception e) {
            Log.e("INFO ", "Erro ao deletar contato: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Contato> listar() {
        List<Contato> contatos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CONTATOS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){
            Contato contato = new Contato();
            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeContato = c.getString( c.getColumnIndex("nome") );
            String telefoneContato = c.getString( c.getColumnIndex("telefone") );
            contato.setId( id );
            contato.setNomeContato( nomeContato );
            contato.setTelefoneContato( telefoneContato );

            contatos.add(contato);
        }

        return contatos;
    }
}
