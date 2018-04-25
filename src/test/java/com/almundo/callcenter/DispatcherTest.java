package com.almundo.callcenter;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.model.Director;
import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.Operador;
import com.almundo.callcenter.model.Supervisor;
import com.almundo.callcenter.service.Dispatcher;


/**
 * Casos de prueba
 */
public class DispatcherTest {

	Dispatcher dispatcher;

	@Before
	public void setup() {
		dispatcher = new Dispatcher();
	}
	
	/**
	 * Test de 10 llamadas, 7 supervisores 
	 * 2 supervisores y 1 director
	 */
	@Test
	public void responderLlamadas() {
		
		System.out.println("********* Test 1 ************** ");
		
		/**
		 * Se crean 7 operadores 
		 */
		for (int i = 1; i <= 7; i++) {
			
			dispatcher.agregarEmpleado(new Operador("Operador "+ i));
		}        
      
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 1"));
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 2"));
        
        dispatcher.agregarEmpleado(new Director("Director 1"));
        
        int cantLlamadas = 0;
        
        //se crean 10 llamadas 
        for (int i = 0; i < 10; i++) {        	
        	cantLlamadas++;
			dispatcher.agregarLlamada(new Llamada(cantLlamadas));
			
		}
        
        //se crea un hilo para procesar las 5 llamadas 
        
        Thread t1 = new Thread( ) {
        	public void run() {
        		dispatcher.dispatchCall();
        	};
        };
   
        //se lanza el hilo        
        t1.start();
        
        //se espera que termine de asignar las llamadas
        try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
        //se pone una pequeña pausa para permitir la 
        //aignación de la ultima llamada
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        //se valida que no existan llamadas en la cola
        assertTrue(dispatcher.getLlamadas().size() == 0);
	}
	
	
	/**
	 * Test de 15 llamadas, 7 supervisores 
	 * 2 supervisores y 1 director y dos hilos una solicitud 
	 * de 5 llamdas y la otra se 10.
	 * 
	 * En este caso las 5 llamadas restantes quedan en la cola 
	 * a ala espera de un empleado libre 
	 */
	@Test
	public void responderLlamadasDosHilos() {
		
		
		System.out.println("********* Test 2 ************** ");
		
		/**
		 * Se crean 7 operadores 
		 */
		for (int i = 1; i <= 7; i++) {
			
			dispatcher.agregarEmpleado(new Operador("Operador "+ i));
		}        
      
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 1"));
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 2"));
        
        dispatcher.agregarEmpleado(new Director("Director 1"));
        
        int cantLlamadas = 0;
        
        //se crean 5 llamadas 
        for (int i = 0; i < 5; i++) {        	
        	cantLlamadas++;
			dispatcher.agregarLlamada(new Llamada(cantLlamadas));
			
		}
        
        //se crea un hilo para procesar las 5 llamadas 
        
        Thread t1 = new Thread( ) {
        	public void run() {
        		dispatcher.dispatchCall();
        	};
        };
   
        //se lanza el primer hilo        
        t1.start();
        
        
      //se crean 10 llamadas 
        for (int i = 0; i < 10; i++) {        	
        	cantLlamadas++;
			dispatcher.agregarLlamada(new Llamada(cantLlamadas));
			
		}
        
        //se crea un hilo para procesar las 5 llamadas 
        
        Thread t2 = new Thread( ) {
        	public void run() {
        		dispatcher.dispatchCall();
        	};
        };
   
      //se lanza el segundo  hilo        
        t2.start();
        
        //se espera que termine de asignar las llamadas
        try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
        //se pone una pequeña pausa para permitir la 
        //aignación de la ultima llamada
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        //se valida que no existan llamadas en la cola
        assertTrue(dispatcher.getLlamadas().size() == 0);
	}
	
	
	/**
	 * Se crea un hilo por cada llamada
	 */
	@Test
	public void responderLlamadaHilo() {
		
		
		System.out.println("********* Test 3 ************** ");
		
		/**
		 * Se crean 7 operadores 
		 */
		for (int i = 1; i <= 7; i++) {
			
			dispatcher.agregarEmpleado(new Operador("Operador "+ i));
		}        
      
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 1"));
        
        dispatcher.agregarEmpleado(new Supervisor("Supervisor 2"));
        
        dispatcher.agregarEmpleado(new Director("Director 1"));
        
        int cantLlamadas = 20;
        
        Thread[] hilos = new Thread[cantLlamadas];
        
        
        /**
         * se crean las llamadas y los hilos de manera dinamica
         * a medida que se crea la llamada se llama el metodo 
         * dispatchCall 
         */
        for (int i = 0; i < cantLlamadas; i++) {        	
			
        	dispatcher.agregarLlamada(new Llamada(i+1));
			
			
	        Thread t1 = new Thread( ) {
	        	public void run() {
	        		dispatcher.dispatchCall();
	        	};
	        };
	   
	        hilos[i] = t1;
	        t1.start();
			
		}
        
        //se espera que termine de asignar las llamadas
        for (int i = 0; i < hilos.length; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        
        //se pone una pequeña pausa para permitir la 
        //aignación de la ultima llamada
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        //se valida que no existan llamadas en la cola
        assertTrue(dispatcher.getLlamadas().size() == 0);
	}
	
	
}
