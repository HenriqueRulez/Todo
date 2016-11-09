package br.senai.sp.informatica.todo.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<Integer, Boolean> {
	@Override
	public Boolean unmarshal(Integer value) throws Exception {
		return value == null ? null : value == 1;
	}

	@Override
	public Integer marshal(Boolean value) throws Exception {
		return value == null ? null : value ? 1 : 0;
	}
}