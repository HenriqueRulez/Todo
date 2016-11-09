package br.senai.sp.informatica.todoapp.view;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.senai.sp.informatica.todoapp.R;
import br.senai.sp.informatica.todoapp.model.AFazer;
import br.senai.sp.informatica.todoapp.model.AFazerDao;
import br.senai.sp.informatica.todoapp.model.Item;


public class AFazerAdapter extends BaseAdapter
            implements View.OnClickListener {
    private static AFazerDao dao = AFazerDao.instancia;
    private SparseArray<Long> mapa;
    private boolean feito;
    private Activity activity;

    public AFazerAdapter(Activity activity, boolean feito) {
        this.feito = feito;
        this.activity = activity;

        criarMapa();
    }

    public void criarMapa() {
        mapa = new SparseArray<>();
        int id = 0;

        for(AFazer todo : dao.listar(feito)) {
            mapa.put(id++, todo.getId());
        }
    }

    @Override
    public void notifyDataSetChanged() {
        criarMapa();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.localizar(id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View detalhe, ViewGroup parent) {
        ConstraintLayout layout;

        if(detalhe == null) {
            // Construir o layout
            Context ctx = parent.getContext();
            layout = new ConstraintLayout(ctx);
            LayoutInflater service = (LayoutInflater)ctx.getSystemService(
                 Context.LAYOUT_INFLATER_SERVICE);
            service.inflate(R.layout.afazer_adapter, layout, true);
        } else {
            // Obter o layout
            layout = (ConstraintLayout)detalhe;
        }

        // Atribuir os valores nos campos do Layout
        AFazer todo = dao.localizar(mapa.get(linha));

        TextView tvTitulo = (TextView) layout.findViewById(R.id.tvTitulo);
        tvTitulo.setText(todo.getTitulo());

        if(!todo.isDel()) {
            TextView tvDel = (TextView) layout.findViewById(R.id.tvDel);
            tvDel.setOnClickListener(this);
            tvDel.setTag(todo.getId());

            if(todo.isConcluido())
                layout.setBackgroundColor(parent.getResources().getColor(R.color.colorConcluido));
        } else {
            layout.setBackgroundColor(parent.getResources().getColor(R.color.colorApagado));
        }

        return layout;
    }

    @Override
    public void onClick(View view) {
        long id = (long)view.getTag();
        Alerta alerta = new Alerta();
        alerta.setId(id);
        alerta.setAdapter(this);
        alerta.setDao(dao);
        alerta.show(activity.getFragmentManager(), "Alerta");
    }

}
