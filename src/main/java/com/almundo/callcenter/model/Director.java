package com.almundo.callcenter.model;

public class Director extends Empleado{

	public Director(String nombre) {
		super(nombre);
	}

	@Override
	public int getPriority() {
		return 3;
	}

}
