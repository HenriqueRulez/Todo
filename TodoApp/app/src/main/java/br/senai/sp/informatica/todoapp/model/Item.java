package br.senai.sp.informatica.todoapp.model;


public class Item {
    private Long id;
    private String descricao;
    private boolean concluido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Item novo = new Item();
        novo.id = (long)id;
        novo.concluido = concluido;
        novo.descricao = new String(descricao.getBytes());

        return novo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", concluido=" + concluido +
                '}';
    }
}
