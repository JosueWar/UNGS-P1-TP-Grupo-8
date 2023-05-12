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
	Asteroide[] asteroides  = new Asteroide[4];
	

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
		
		for(int i=0;i<4;i++) {
			Asteroide a = new Asteroide();
			asteroides[i]=a;
		}

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
		//Teclas: d=100, a=101
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada(TECLA_DERECHA_D))
			nave.moverDerecha();
			
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada(TECLA_IZQUIERDA_A))
			nave.moverIzquierda();
		
		//System.out.println(asteroides.length);

		
		
		//Asteroides
		for(int i=0;i<asteroides.length;i++) {
			asteroides[i].moverAdelante(entorno);
			asteroides[i].dibujarse(entorno);
			
			//Jugador peligros
			if(colision(nave, asteroides[i],40)){
				nave=null;
				System.out.println("colision!!!!");
			}
		}
		
		
		
		
		
		
		//Entorno
		nave.dibujarse(entorno);

		entorno.cambiarFont("Arial", 18, Color.white);
		entorno.escribirTexto("El angulo es: " + nave.angulo, 500, 100);
		entorno.escribirTexto("posicion en x:" + nave.x, 500, 150);
		entorno.escribirTexto("posicion en y:" + nave.y, 500, 200);
		
	}
	
	public boolean colision(Nave n, Asteroide a, double d) {
		return (n.x-a.x)*(n.x-a.x)+(n.y-a.y)*(n.y-a.y)<d*d;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
