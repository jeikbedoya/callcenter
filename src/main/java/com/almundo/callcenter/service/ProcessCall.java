package com.almundo.callcenter.service;

import java.util.concurrent.ThreadLocalRandom;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;

/**
 * Clase que procesa cada una de las  las llamadas 
 * en un hilo diferente
 * @author jheyson
 *
 */
public class ProcessCall implements Runnable {
	
	/**
	 * Contiene la cantidad de llamadas que
	 * estan activas por eso es estatica
	 */
	public static int canLlamadasEnProceso = 0;
	
	public static final int MIN_TIEMPO = 5000;
	
	public static final int MAX_TIEMPO = 10000;
	
	private Llamada llamada;
	
	private Empleado empleado;
	
	/**
	 * Se recibe la llamada y el empleado aasignado a la llamda
	 * @param empleado
	 * @param llamada
	 */
	public ProcessCall(Empleado empleado, Llamada llamada) {
		this.empleado = empleado;
		this.llamada = llamada;
	}

	@Override
	public void run() {
		
		/**
		 * Se realiza synchronized para evitar 
		 * el problema de indeterminismo y se aaumenta 
		 * en uno la cantidad de llamadas en proceso
		 */
		synchronized (empleado) {
			canLlamadasEnProceso++;
		}		
		
		/**
		 * Se crea un valor aleatorio del tiempo de la llamada entre 3 y 5 segundos
		 */
		int valueSleep = ThreadLocalRandom.current().nextInt(MIN_TIEMPO, MAX_TIEMPO + 1);
		System.out.println("Resolviendo llamada "+llamada.getId()+" empleado "+empleado.getNombre() + " tiempo llamada "+valueSleep);
		try {
			Thread.sleep(valueSleep);
			//se cambia el estsdo del empleado cuando termina la llamada.
			empleado.setEnabled(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/**
		 * Se realiza synchronized para evitar 
		 * el problema de indeterminismo y se dismunuye 
		 * en uno la cantidad de llamadas en proceso
		 */
		synchronized (empleado) {
			canLlamadasEnProceso--;
			
		}
		
	}

	
	
}
