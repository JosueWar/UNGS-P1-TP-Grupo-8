package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image fondo;
	Image gameover;
	Image ganaste;
	boolean debugMode;
	
	Nave nave;
	Proyectil pNave;
	
	
	Asteroide[] asteroides;
	Enemigo[] enemigos;
	Proyectil[] pEnemigos;
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
		nave = new Nave(entorno, ancho/2, alto-40,0.2);
		fondo=Herramientas.cargarImagen("fondo.gif");
		debugMode = false;
		
		
	    gameover=Herramientas.cargarImagen("gameover.png");
	    ganaste=Herramientas.cargarImagen("ganaste.png");
		anguloFondo= 0;
		escalaFondo=1.7;
		incremento=0.01;
		
		// Inicializar lo que haga falta para el juego
		
		
		//PROYECTILES
		
		
		
		//ASTEROIDES
		asteroides = new Asteroide[4];
		for(int i=0;i<4;i++) {
			Asteroide a = new Asteroide(entorno, 0.5 ,Extras.generarRandomDouble(1,5));
			asteroides[i]=a;
		}
		
		//ENEMIGOS
		enemigos =new Enemigo[4];
		for (int i=0; i < enemigos.length;i++) {
			enemigos[i]=new Enemigo(entorno, 0.2 ,6);
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
		
		
		//CONTROLES
		if(nave != null) {
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada(TECLA_DERECHA_D))
				nave.mover(5,entorno);
				
			
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada(TECLA_IZQUIERDA_A))
				nave.mover(-5,entorno);
			
			
			if (pNave == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				pNave= new Proyectil(entorno,1,3,5,nave.x,nave.y);
			}
			
			if (entorno.sePresiono('m')) {
				debugMode=true;
			}
			if (entorno.sePresiono('n')) {
				debugMode=false;
			}
			
		}
		
		
		//PROYECTILES
		
		//Proyectil Nave
		if(pNave != null){
			pNave.dibujar();
			pNave.mover();
			
			//debugMode
			if(debugMode == true)
				pNave.dibujarCaja();
			
			//Desaparecerlo si esta fuera del mapa
			if(!Detector.estarEnEntorno(pNave.x, pNave.y, entorno)) {
				pNave=null;
			}
		}
		
		
		//Proyectil enemigos
		pEnemigos = new Proyectil[enemigos.length ];
		for(int i=0;i < pEnemigos.length;i++) {
			if(pEnemigos[i] !=  null) {
				//Accionar proyectiles
				pEnemigos[i].dibujar();
				pEnemigos[i].mover();
				
				//Colisiones
				
				//Si colisiona a un jugador
				if(pEnemigos[i].tipo == 1 && Detector.colisiona(nave.x,nave.y,nave.ancho,nave.alto,pEnemigos[i].x,pEnemigos[i].y,pEnemigos[i].ancho,pEnemigos[i].alto)) {
					System.out.println("colision con iones!!!!");
					nave=null;
				}
				
				//Desaparecerlo si esta fuera del mapa
				if(!Detector.estarEnEntorno(pEnemigos[i].x, pEnemigos[i].y, entorno)) {
					pEnemigos[i] = null;
				}
			}
		}
		
		
		
		//ASTEROIDES
		for(int i=0;i<asteroides.length;i++) {
			asteroides[i].mover(entorno);
			asteroides[i].dibujar(entorno);
			//debug mode
			if(debugMode == true)
				asteroides[i].dibujarCaja();
			
			//Si enemigo colisiona con el jugador
			if (nave != null  && asteroides[i] != null && 
				Detector.colisiona(nave.x,nave.y,nave.ancho,nave.alto,asteroides[i].x,asteroides[i].y,asteroides[i].ancho,asteroides[i].alto)) {
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
				//debugMode
				if(debugMode == true) {
					enemigos[i].dibujarCaja();
				}
				
				//Regresa hacia arriba si bajo demasiado la nave
				if(!Detector.estarEnEntorno(enemigos[i].x,enemigos[i].y,entorno)) {
					enemigos[i].y = 0;
				}
				
				//En caso de colision o cercania
				for(int j=0;j < enemigos.length;j++) {
					
					//Si esta muy cerca de una nave
					if (enemigos[j] != null && i != j && 
						Detector.colisiona(enemigos[i].x,enemigos[i].y,enemigos[i].ancho,enemigos[i].alto, enemigos[j].x,enemigos[j].y,enemigos[j].ancho,enemigos[j].alto)) {
						
						enemigos[i].cambiarAngulo();
						enemigos[j].cambiarAngulo();
					}
				}
				//Si esta muy cerca de un asteroide
				
				for(int k=0;k<asteroides.length;k++) {
					if (asteroides[k] != null && 
						Detector.colisiona(asteroides[i].x,asteroides[i].y,asteroides[i].ancho,asteroides[i].alto,enemigos[k].x,enemigos[k].y,enemigos[k].ancho,enemigos[k].alto)) {
							enemigos[i].cambiarAngulo();
						}
				}
				
				//Si enemigo colisiona con el jugador
				if (nave != null  && enemigos[i] != null && 
					Detector.colisiona(nave.x,nave.y,nave.ancho,nave.alto,enemigos[i].x,enemigos[i].y,enemigos[i].ancho,enemigos[i].alto)) {
					System.out.println("colision con enemigo!!!!");
					nave=null;
				}
				
			
			}
			
		}
		
		
		
		
		
		
		//NAVE
		
		if (nave != null) {
			nave.dibujarse(entorno);
			
			//debug mode
			if(debugMode == true) {
				nave.dibujarCaja();
				entorno.cambiarFont("Arial", 20, Color.white);
				entorno.escribirTexto("El angulo es: " + nave.angulo, 500, 100);
				entorno.escribirTexto("posicion en x:" + nave.x, 500, 150);
				entorno.escribirTexto("posicion en y:" + nave.y, 500, 200);
				entorno.escribirTexto("contador:" + contador, 500, 250);
			}
			
		}else if (nave == null) {
			entorno.dibujarImagen(gameover,400, 300, 0.0);
			// Herramientas.cargarSonido("perdiste.wav").start();
			
		}
		else if(nave != null && enemigos == null) {
			entorno.dibujarImagen(ganaste,400, 300, 0.0);
		}
			
	
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
