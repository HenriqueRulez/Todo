package br.senai.sp.informatica.todoapp.model;

import java.util.ArrayList;
import java.util.List;

public class AFazerDaoObj implements Dao {
    public static AFazerDaoObj instancia = new AFazerDaoObj();

    private List<AFazer> lista;
    private long id = 0;

    private AFazerDaoObj() {
        lista = new ArrayList<>();
        salvar(new AFazer("Atividade 1"));
        salvar(new AFazer("Atividade 2"));
    }

    public void salvar(AFazer obj) {
        if(obj.getId() == null) {
            // incluir
            obj.setId(id++);
            lista.add(obj);
        } else {
            // atualizar
            AFazer aFazer = localizar(obj.getId());
           // aFazer.setConcluido(obj.isConcluido());
            aFazer.setTitulo(obj.getTitulo());
        }
    }

    public AFazer localizar(long id) {
        AFazer obj = null;

        for(AFazer aFazer : lista) {
            if(aFazer.getId() == id) {
                obj = aFazer;
                break;
            }
        }

        return obj;
    }

    public List<AFazer> listar(boolean feito) {
        List<AFazer> listaSaida = new ArrayList<>();

        for(AFazer aFazer : lista) {
            if(aFazer.isConcluido() == feito) {
                listaSaida.add(aFazer);
            }
        }

        return listaSaida;
    }

    public void remover(long id) {
        AFazer aFazer = localizar(id);

        if(aFazer != null)
            lista.remove(aFazer);
    }
}
