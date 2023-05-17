package juego;


import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image fondo;
	
	Nave nave;
	Proyectil pNave;
	
	
	Asteroide[] asteroides;
	Enemigo[] enemigos;
	LinkedList<Proyectil> pEnemigos;
	int contador;
	

	double anguloFondo;
	double escalaFondo;
	double incremento;

	// Variables y métodos propios de cada grupo
	
	public final char TECLA_DERECHA_D = 'd';
	public final char TECLA_IZQUIERDA_A = 'a';
	
	//Elegir nivel
	double nivel = 1;
	
	double dificultad =  nivel * Extras.generarRandomDouble(3, 6);
	
	//Pantalla
	int ancho = 800;
	int alto = 600;
	// ...

	Juego() {
		// Inicializa el objeto entorno
		entorno = new Entorno(this, "Lost Galaxian - Grupo 8 - v1", ancho, alto);
		nave = new Nave(entorno, ancho/2, alto-40);
		fondo=Herramientas.cargarImagen("fondo.gif");
		anguloFondo= 0;
		escalaFondo=1.7;
		incremento=0.01;
		
		// Inicializar lo que haga falta para el juego
		
		
		//PROYECTILES
		pEnemigos = new LinkedList<Proyectil>();
		
		//ASTEROIDES
		asteroides = new Asteroide[4];
		for(int i=0;i<4;i++) {
			Asteroide a = new Asteroide(entorno, 0.5 ,Extras.generarRandomDouble(1,5));
			asteroides[i]=a;
		}
		
		//ENEMIGOS
		enemigos =new Enemigo[6];
		for (int i=0; i < enemigos.length;i++) {
			enemigos[i]=new Enemigo(entorno, 0.2 ,4.0*Math.random()+5);
		}
		contador=0;
		
		// ...

		// Inicia el juego!
		entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		
		
		//Fondo
		entorno.dibujarImagen(fondo, 400, 300, anguloFondo, escalaFondo);
		//anguloFondo +=0.001;
		//escalaFondo +=incremento;
		if(escalaFondo > 5) {
			incremento=-0.01;
		}
		if(escalaFondo < 2) {
			incremento=0.01;
		}
		
		
		//NAVE
		if(nave != null) {
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada(TECLA_DERECHA_D))
				nave.mover(5,entorno);
				
			
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada(TECLA_IZQUIERDA_A))
				nave.mover(-5,entorno);
			
			
			if (pNave == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				pNave= new Proyectil(entorno,1,5,nave.x,nave.y);
			}
			
			
		}
		
		
		//PROYECTILES
		
		//Proyectil Nave
		if(pNave != null){
			pNave.dibujar();
			pNave.mover();
			//Desaparecerlo si esta fuera del mapa
			if(!Detector.estarEnEntorno(pNave.x, pNave.y, entorno)) {
				pNave=null;
			}
		}
		
		
		//Proyectil enemigos
		
		for(int i=0;i<pEnemigos.size();i++) {
			if(pEnemigos.get(i) !=  null) {
				//Accionar proyectiles
				pEnemigos.get(i).dibujar();
				pEnemigos.get(i).mover();
				
				//Colisiones
				
				//Si colisiona a un jugador
				if(pEnemigos.get(i).tipo == 1 && Detector.colisiona(nave.x,nave.y, pEnemigos.get(i).x,pEnemigos.get(i).y,20)) {
					System.out.println("colision con iones!!!!");
					nave=null;
				}
				
				//Desaparecerlo si esta fuera del mapa
				if(!Detector.estarEnEntorno(pEnemigos.get(i).x, pEnemigos.get(i).y, entorno)) {
					pEnemigos.remove(i);
				}
			}
		}
		
		
		
		//ASTEROIDES
		for(int i=0;i<asteroides.length;i++) {
			asteroides[i].mover(entorno);
			asteroides[i].dibujar(entorno);
			
			//Si enemigo colisiona con el jugador
			if (nave != null  && asteroides[i] != null && 
				Detector.colisiona(nave.x,nave.y, asteroides[i].x,asteroides[i].y,20)) {
				System.out.println("colision con asteroide!!!!");
				nave=null;
			}
			if(!Detector.estarEnEntorno(asteroides[i].x,asteroides[i].y,entorno)) {
				asteroides[i].y = 0;
			}
		}
		
		
		//ENEMIGOS
		for (int i=0; i < enemigos.length;i++) {
			if(enemigos[i] != null) {
				enemigos[i].dibujar();
				enemigos[i].mover();
				
				//Regresa hacia arriba si bajo demasiado la nave
				if(!Detector.estarEnEntorno(enemigos[i].x,enemigos[i].y,entorno)) {
					enemigos[i].y = 0;
				}
				
				//En caso de colision o cercania
				for(int j=0;j < enemigos.length;j++) {
					
					//Si esta muy cerca de una nave
					if (enemigos[i] != null  && enemigos[j] != null && i != j && 
						Detector.colisiona(enemigos[i].x,enemigos[i].y, enemigos[j].x,enemigos[j].y,50)) {
						
						enemigos[i].cambiarAngulo();
						enemigos[j].cambiarAngulo();
					}
					//Si esta muy cerca de un asteroide
					
					for(int k=0;k<asteroides.length;k++) {
						if (enemigos[i] != null  && asteroides[k] != null && 
								Detector.colisiona(enemigos[i].x,enemigos[i].y, asteroides[k].x,asteroides[k].y,50)) {
								enemigos[i].cambiarAngulo();
							}
					}
				}
				
				//Si enemigo colisiona con el jugador
				if (nave != null  && enemigos[i] != null && 
					Detector.colisiona(nave.x,nave.y, enemigos[i].x,enemigos[i].y,20)) {
					System.out.println("colision con enemigo!!!!");
					nave=null;
				}
			}
			
		}
		
		
		
		
		
		
		//Entorno
		nave.dibujarse(entorno);

		entorno.cambiarFont("Arial", 20, Color.white);
		entorno.escribirTexto("El angulo es: " + nave.angulo, 500, 100);
		entorno.escribirTexto("posicion en x:" + nave.x, 500, 150);
		entorno.escribirTexto("posicion en y:" + nave.y, 500, 200);
		entorno.escribirTexto("contador:" + contador, 500, 250);
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
