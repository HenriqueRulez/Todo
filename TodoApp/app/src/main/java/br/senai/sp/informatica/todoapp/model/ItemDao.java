package br.senai.sp.informatica.todoapp.model;


import java.util.List;

public class ItemDao  implements Dao {
    private static ItemDao instance;
    private List<Item> lista;
    private static long id = 0;

    private ItemDao(List<Item> lista) {
        this.lista = lista;
    }

    public static ItemDao getInstance(List<Item> lista) {
        ItemDao dao = new ItemDao(lista);
        ItemDao.instance = dao;
        return dao;
    }

    public static ItemDao getInstance() {
        return instance;
    }

    public void salvar(Item item) {
        if(item.getId() == null) {
            // Inclusão
            item.setId(id++);
            lista.add(item);
        } else {
            Item obj = localizar(item.getId());
            obj.setDescricao(item.getDescricao());
            obj.setConcluido(item.isConcluido());
        }
    }

    public Item localizar(long id) {
        Item item = null;

        for(Item obj : lista) {
            if(obj.getId() == id) {
                item = obj;
                break;
            }
        }

        return item;
    }

    public List<Item> listar() {
        return lista;
    }

    public void remover(long id) {
        Item item = localizar(id);

        if(item != null)
            lista.remove(item);
    }
}
