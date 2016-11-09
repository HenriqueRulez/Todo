package br.senai.sp.informatica.todoapp.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import br.senai.sp.informatica.todoapp.Main;
import br.senai.sp.informatica.todoapp.R;
import br.senai.sp.informatica.todoapp.model.AFazer;
import br.senai.sp.informatica.todoapp.model.AFazerDao;

public class MainActivity extends AppCompatActivity
        implements TabHost.OnTabChangeListener,
        AdapterView.OnItemClickListener,
        View.OnClickListener {
    private TabHost tabHost;
    private AFazerAdapter adapterAFazer;
    private AFazerAdapter adapterConluido;

    public static final int EDITAR_ITEM = 0;
    public static final int INCLUIR_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("afazer");
        spec.setContent(R.id.afazer);
        spec.setIndicator("A Fazer");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("concluido");
        spec.setContent(R.id.concluido);
        spec.setIndicator("Concluído");
        tabHost.addTab(spec);

        tabHost.setOnTabChangedListener(this);

        ListView listAFazer = (ListView)findViewById(R.id.listaAFazer);
        adapterAFazer = new AFazerAdapter(this, AFazer.A_FAZER);
        listAFazer.setAdapter(adapterAFazer);
        listAFazer.setOnItemClickListener(this);

        ListView listConcluido = (ListView)findViewById(R.id.listaConcluido);
        adapterConluido = new AFazerAdapter(this, AFazer.FEITO);
        listConcluido.setAdapter(adapterConluido);
        listConcluido.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        if(tabId.equals("afazer")) {
            tabHost.setCurrentTab(0);
        } else {
            tabHost.setCurrentTab(1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AFazer todo = AFazerDao.instancia.localizar(id);
        if(todo != null) {
            Intent intent = new Intent(getBaseContext(), AFazerActivity.class);
            intent.putExtra("id", id);
            startActivityForResult(intent, EDITAR_ITEM);
        } else {
            Toast.makeText(Main.context, "Este Todo já foi excluído por outra Pessoa", Toast.LENGTH_LONG).show();
            adapterAFazer.notifyDataSetChanged();
            adapterConluido.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), AFazerActivity.class);
        startActivityForResult(intent, INCLUIR_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            adapterAFazer.notifyDataSetChanged();

 //           if(resultCode == EDITAR_ITEM) {
                adapterConluido.notifyDataSetChanged();
 //           }
        }
    }
}
