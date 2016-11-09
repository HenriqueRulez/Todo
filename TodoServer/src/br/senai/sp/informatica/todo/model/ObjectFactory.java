package br.senai.sp.informatica.todo.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {
    }

    public Item createItem() {
        return new Item();
    }

    public Todo createTodo() {
        return new Todo();
    }

    public Todos createTodos() {
        return new Todos();
    }
}
