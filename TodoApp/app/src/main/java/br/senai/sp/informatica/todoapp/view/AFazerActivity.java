package br.senai.sp.informatica.todoapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.senai.sp.informatica.todoapp.R;
import br.senai.sp.informatica.todoapp.model.Item;
import br.senai.sp.informatica.todoapp.model.ItemDao;
import br.senai.sp.informatica.todoapp.model.AFazer;
import br.senai.sp.informatica.todoapp.model.AFazerDao;

public class AFazerActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    private EditText edTitulo;
    private BaseAdapter detalhe;

    private AFazer todoEditado;

    private ItemDao itemDao;

    public static final int EDITA_ITEM = 0;
    public static final int NOVO_ITEM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.afazer_activity);

        edTitulo = (EditText) findViewById(R.id.edTitulo);

        Intent intent =getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            // Alteração
            long id = extras.getLong("id");
            todoEditado = AFazerDao.instancia.localizar(id);
            if(todoEditado != null) {
                edTitulo.setText(todoEditado.getTitulo());
                itemDao = ItemDao.getInstance(todoEditado.getItens());
                detalhe = new ItemAdapter(this, itemDao);
            }
        } else {
            // Inclusão
            itemDao = ItemDao.getInstance(new ArrayList<Item>());
            detalhe = new ItemAdapter(this, itemDao);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(detalhe);
        listView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ActionBar bar = getActionBar();
        if(bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ItemActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, EDITA_ITEM);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), ItemActivity.class);
        startActivityForResult(intent, NOVO_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            detalhe.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                break;
            case R.id.action_salvar:
                if(todoEditado != null) {
                    // Atualizar o Registro
                    todoEditado.setTitulo(edTitulo.getText().toString());
                    todoEditado.setItens(itemDao.listar());

                    AFazerDao.instancia.salvar(todoEditado);
                    todoEditado = null;
                } else {
                    // Incluir o Registro
                    AFazer todo = new AFazer();
                    todo.setTitulo(edTitulo.getText().toString());
                    todo.setItens(itemDao.listar());

                    AFazerDao.instancia.salvar(todo);
                }
                setResult(RESULT_OK);

                break;
        }

        finish();

        return true;
    }
}
