package br.senai.sp.informatica.todo.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tarefa"
})
@XmlRootElement(name = "tarefas")
public class Todos {
    protected List<Todo> tarefa;

    public List<Todo> getTarefa() {
        if (tarefa == null) {
            tarefa = new ArrayList<Todo>();
        }
        return this.tarefa;
    }
}
