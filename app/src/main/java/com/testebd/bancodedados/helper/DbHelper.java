package com.testebd.bancodedados.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CONTATOS";
    public static String TABELA_CONTATOS = "contatos";


    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_CONTATOS +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  nome TEXT NOT NULL , " +
                        "  telefone VARCHAR(10) ); ";

        try  {
            db.execSQL( sql );
            Log.i("INFO DB", " Sucesso ao criar a tabela ");
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA_CONTATOS + " ; ";

        try  {
            db.execSQL( sql );
            onCreate( db );
            Log.i("INFO DB", " Sucesso ao atualizar app ");
        } catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar app: " + e.getMessage());
        }

    }




}
