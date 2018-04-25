package com.almundo.callcenter.model;

public class Operador extends Empleado {

	public Operador(String nombre) {
		super(nombre);
	}

	
	@Override
	public int getPriority() {
		return 1;
	}

}
