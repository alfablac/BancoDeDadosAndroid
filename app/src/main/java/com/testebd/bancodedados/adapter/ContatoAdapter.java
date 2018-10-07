package com.testebd.bancodedados.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testebd.bancodedados.R;
import com.testebd.bancodedados.model.Contato;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.MyViewHolder> {

    private List<Contato> listaContatos;

    public ContatoAdapter(List<Contato> lista) {
        this.listaContatos = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemContato = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.lista_contato_adapter, parent, false);
        return new MyViewHolder(itemContato);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contato contato = listaContatos.get(position);
        holder.nomeContato.setText( contato.getNomeContato() );
        holder.telefoneContato.setText( contato.getTelefoneContato() );
    }

    @Override
    public int getItemCount() {
        return this.listaContatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeContato;
        TextView telefoneContato;


        public MyViewHolder(View itemView) {
            super(itemView);

            nomeContato = itemView.findViewById(R.id.textNomeContato);
            telefoneContato = itemView.findViewById(R.id.textTelefoneContato);


        }

    }
}
