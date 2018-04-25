package com.almundo.callcenter.model;

public class Supervisor extends Empleado {

	public Supervisor(String nombre) {
		super(nombre);
	}

	@Override
	public int getPriority() {
		return 2;
	}

}
