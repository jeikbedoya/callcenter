package com.almundo.callcenter.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;

/**
 * Clase que administra las llamadas
 * @author jheyson
 *
 */
public class Dispatcher {
	
	/**
	 * Cantidad maxima de llamadas que se atenderan de manera concurrente
	 */
	public static final int MAX_LLAMADAS_CONCURRENTES = 10;
	
	/**
	 * Listado de los empleados disponibles para responder
	 * a las llamadas 
	 */
	private List<Empleado> empleados = new ArrayList<Empleado>();
	
	/**
	 * Listado de llamadas se crea de tipo linkedList para 
	 * cumplir con el principio FIFO( First In, First Out)
	 * y de esta manera poder atender las llamadas en el
	 * mismo orden en que van llegando
	 */
	private LinkedList<Llamada>  llamadas = new LinkedList<Llamada>();
	
	/**
	 * Se crea el metodo de tipo  synchronized
	 * para que solo puedan acceder a el un hilo a la vez, ya que si se
	 * estan procesando las llamadas no es necesario volver a llamar este metodo,
	 * pero si ingresan mas llamadas es necesario llamarlo para  disparar la asignacion 
	 * de llamadas
	 */
	public synchronized void dispatchCall() {
		
		//se recorre las llamadas que aun no estan procesadas
		while(!llamadas.isEmpty()) {
			
			/**
			 * primero se filtra por los empleados disponibles despues se ordena por
			 * la prioridad definida en cada una de las clases Operador, Supervisor y dicrector
			 * y se toma el primero en ese orden
			 */
			Optional<Empleado> empleadoLibre = empleados.stream().filter(e -> e.isEnabled())
					.sorted(Comparator.comparing(Empleado::getPriority))
					.findFirst();
			
			/**
			 * Si se encontro un empleado disponible y ademas la cantidad de llamadas
			 * activas es menor a la cantidad maxima de llamas concurrentes
			 */
			if (empleadoLibre.isPresent() && ProcessCall.canLlamadasEnProceso < MAX_LLAMADAS_CONCURRENTES) {
				
				/**
				 * se crea el proceso para atender la llamada y se le asigna el empleado disponible
				 * y la llamada que esta de primera en la cola
				 */
				Runnable runnable = new ProcessCall(empleadoLibre.get(), llamadas.poll());
				Thread t = new Thread(runnable);
				
				//se cambia el estado del empleado a no disponible
				empleadoLibre.get().setEnabled(false);
				//se inicia la atención de la llamada
				t.start();

			}
		}
		
	}
	
	/**
	 * Se agrean empleados de manera dinamica
	 * @param empleado
	 */
	public void agregarEmpleado(Empleado empleado) {
		empleados.add(empleado);
	}
	
	
	/**
	 * Se agregan llamadas a la cola
	 * @param llamada
	 */
	public void agregarLlamada(Llamada llamada) {
		llamadas.offer(llamada);
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public LinkedList<Llamada> getLlamadas() {
		return llamadas;
	}

	public void setLlamadas(LinkedList<Llamada> llamadas) {
		this.llamadas = llamadas;
	}
	
	
	
	
	
}
