package br.senai.sp.informatica.todoapp.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.senai.sp.informatica.todoapp.R;
import br.senai.sp.informatica.todoapp.model.Item;
import br.senai.sp.informatica.todoapp.model.ItemDao;


public class ItemActivity extends AppCompatActivity {
    private EditText edItem;

    private Item itemEditado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        edItem = (EditText) findViewById(R.id.edItem);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                long id = extras.getLong("id");
                itemEditado = ItemDao.getInstance().localizar(id);
                if (itemEditado != null) {
                    edItem.setText(itemEditado.getDescricao());
                }
            }
        }

        ActionBar bar = getActionBar();
        if (bar != null) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                // Voltar
                setResult(RESULT_CANCELED);
                break;
            case R.id.action_salvar:
                // Salvar o item;
                if (itemEditado != null) {
                    itemEditado.setDescricao(edItem.getText().toString());
                    ItemDao.getInstance().salvar(itemEditado);
                    itemEditado = null;
                } else {
                    Item obj = new Item();
                    obj.setDescricao(edItem.getText().toString());
                    ItemDao.getInstance().salvar(obj);
                }
                setResult(RESULT_OK);
        }

        finish();

        return true;
    }
}
