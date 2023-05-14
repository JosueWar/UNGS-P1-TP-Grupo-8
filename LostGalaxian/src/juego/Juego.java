package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Nave nave;
	Image fondo;
	Asteroide[] asteroides;
	
	Enemigo[] enemigos;
	int contador;
	

	double anguloFondo;
	double escalaFondo;
	double incremento;

	// Variables y métodos propios de cada grupo
	
	public final char TECLA_DERECHA_D = 'd';
	public final char TECLA_IZQUIERDA_A = 'a';
	
	//Pantalla
	int ancho = 800;
	int alto = 600;
	// ...

	Juego() {
		// Inicializa el objeto entorno
		entorno = new Entorno(this, "Lost Galaxian - Grupo 8 - v1", ancho, alto);
		nave = new Nave(ancho/2, alto-40);
		fondo=Herramientas.cargarImagen("fondo.jpg");
		anguloFondo=0;
		escalaFondo=2;
		incremento=0.01;
		
		// Inicializar lo que haga falta para el juego
		
		//ASTEROIDES
		asteroides = new Asteroide[4];
		for(int i=0;i<4;i++) {
			Asteroide a = new Asteroide();
			asteroides[i]=a;
		}
		
		//ENEMIGOS
		enemigos =new Enemigo[6];
		for (int i=0; i < enemigos.length;i++) {
			enemigos[i]=new Enemigo(entorno, 0.3 ,4.0*Math.random()+1);
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
		anguloFondo +=0.001;
		escalaFondo +=incremento;
		if(escalaFondo > 5) {
			incremento=-0.01;
		}
		if(escalaFondo < 2) {
			incremento=0.01;
		}
		
		
		//Controles Nave
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada(TECLA_DERECHA_D))
			nave.moverDerecha();
			
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada(TECLA_IZQUIERDA_A))
			nave.moverIzquierda();
		
		//System.out.println(asteroides.length);

		
		
		//ASTEROIDES
		for(int i=0;i<asteroides.length;i++) {
			asteroides[i].mover(entorno);
			asteroides[i].dibujarse(entorno);
			
			//Jugador peligros
			if(colision(nave, asteroides[i],40)){
				nave=null;
				System.out.println("colision!!!!");
			}
		}
		
		
		//ENEMIGOS
		for (int i=0; i < enemigos.length;i++) {
			if(enemigos[i] != null) {
				enemigos[i].dibujar();
				enemigos[i].mover();
				if(!Detector.estarEnEntorno(enemigos[i].x,enemigos[i].y,entorno)) {
					enemigos[i]=null;
					contador++;
				}
			}
			
			else {
				enemigos[i]=new Enemigo(entorno, 0.3 ,4.0*Math.random()+1);
			} 
			for(int j=0;j < enemigos.length;j++) {
				if (enemigos[i] != null  && enemigos[j] != null &&
						i != j && 
						colisionar(enemigos[i].x,enemigos[i].y, enemigos[j].x,enemigos[j].y,20.0)) {
					enemigos[i]	=null;
					enemigos[j] =null;
					contador++;
					contador++;
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
	
	public boolean colision(Nave n, Asteroide a, double d) {
		return (n.x-a.x)*(n.x-a.x)+(n.y-a.y)*(n.y-a.y)<d*d;
	}
	public boolean colisionar(double x1, double y1, double x2, double y2, double dist) {
		return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2) < dist*dist;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
