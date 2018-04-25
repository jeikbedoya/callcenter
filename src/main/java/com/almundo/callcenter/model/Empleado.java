package com.almundo.callcenter.model;

/**
 * Clase abstracta de la cual se 
 * crearan los diferentes tipos de empleados 
 * @author jheyson
 *
 */
public abstract class Empleado {
	
	
	protected String nombre;

	/**
	 * Controla si esta disponible para 
	 * atender una llamada o no
	 */
	protected Boolean enabled;
	
	public Empleado(String nombre) {
		this.enabled = true;
		this.nombre = nombre;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Se crea metodo de tipo abstracto para obligar a
	 * implementarlo a cada una de las clases que la hereden
	 * y asi saber la prioridad de atención de llamadas
	 * @return
	 */
	public abstract int getPriority();
	
	
	
	
	
}
