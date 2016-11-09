package br.senai.sp.informatica.todo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "titulo",
    "feito",
    "itens",
    "del"
})
@XmlRootElement(name = "todo")
public class Todo {
	@Id
	@Column(name="idtodo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @XmlElement(required = true)
    protected Long id;
    @XmlElement(required = true)
    protected String titulo;
    @XmlElement(required = true, name="feito")
    protected boolean feito;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="idtodo")
    @XmlElement(name="itens")
    protected List<Item> itens;
    @XmlElement(name="del", required = false)
    private boolean del;
    
    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String value) {
        this.titulo = value;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean value) {
        this.feito = value;
    }

    public List<Item> getItens() {
        if (itens == null) {
        	itens = new ArrayList<Item>();
        }
        return this.itens;
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

    
}
