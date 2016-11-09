package br.senai.sp.informatica.todoapp.model;

import java.util.ArrayList;
import java.util.List;

public class AFazer {
    private Long id;
    private String titulo;
    private List<Item> itens = new ArrayList<>();
    private boolean del;

    public static final boolean FEITO = true;
    public static final boolean A_FAZER = false;

    public AFazer() {
    }

    public AFazer(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isConcluido() {
        boolean feito = true;

        for(Item item : itens) {
            if(!item.isConcluido()) {
                feito = false;
                break;
            }
        }

        return feito && itens.size() > 0;
    }

    // Implementação para o correto funcionamento
    // da conversão de JSON para Object executado
    // pela biblioteca Jackson 2
    public void setConcluido(boolean feito) {}

    public List<Item> getItens() {
        List<Item> novaLista = null;

        try {
            novaLista = new ArrayList<>();
            for (Item item : itens) {
                novaLista.add((Item)item.clone());
            }
        } catch (CloneNotSupportedException ex) {
            novaLista = itens;
        }

        return novaLista;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "AFazer{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", del=" + del +
                '}';
    }
}
