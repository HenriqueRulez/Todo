package br.senai.sp.informatica.todoapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;

import br.senai.sp.informatica.todoapp.model.Dao;

public class Alerta extends DialogFragment
        implements DialogInterface.OnClickListener {
    private long id;
    private BaseAdapter adapter;
    private Dao dao;

    public void setId(long id) {
        this.id = id;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder tela = new AlertDialog.Builder(getActivity());
        tela.setMessage("Confirma a exclusão do Todo?");
        tela.setPositiveButton("Sim", this);
        return tela.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dao.remover(id);
        adapter.notifyDataSetChanged();
    }
}