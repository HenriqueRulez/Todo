package br.senai.sp.informatica.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "descricao",
    "feito"
})
@XmlRootElement(name = "item")
public class Item {
	@Id
	@Column(name="iditem")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @XmlElement(required = true)
    protected Long id;
    @XmlElement(required = true)
    protected String descricao;
    @XmlElement(required = true, name="feito")
    protected boolean feito;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String value) {
        this.descricao = value;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean value) {
        this.feito = value;
    }
}
