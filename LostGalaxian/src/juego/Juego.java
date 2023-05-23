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
	ProyectilNave proyectilNave;
	
	
	Asteroide[] asteroides;
	Enemigo[] enemigos;
	ProyectilEnemigo[] ionesEnemigos;

	int contador;
	

	double anguloFondo;
	double escalaFondo;
	double incremento;

	// Variables y métodos propios de cada grupo

	//Elegir nivel y otros
	double nivel = 1;
	double dificultad =  nivel * Extras.generarRandomDouble(3, 6);
	double rangoColision = 50;
	
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
		escalaFondo=1.8;
		incremento=0;
		
		// Inicializar lo que haga falta para el juego
		
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
		
		ionesEnemigos = new ProyectilEnemigo[4]; //Se eligio 4 como limite de cantidad de iones instanciados en pantalla 
		
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
		
		//CONTROLES
		if(nave != null) {
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))
				nave.mover(5,entorno);
				
			
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))
				nave.mover(-5,entorno);
			
			
			if (proyectilNave == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				proyectilNave= new ProyectilNave(entorno,3,5,nave.x,nave.y);
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
		if(proyectilNave != null){
			proyectilNave.dibujar();
			proyectilNave.mover();
			
			//debugMode
			if(debugMode == true)
				proyectilNave.dibujarCaja();
			
			//Desaparecerlo si esta fuera del mapa
			if(!Detector.estarEnEntorno(proyectilNave.x, proyectilNave.y, entorno)) {
				proyectilNave=null;
			}
		}
		
		
		//Proyectil enemigos
		for(int i=0;i < ionesEnemigos.length;i++) {
			if(ionesEnemigos[i] !=  null) {
				//Accionar proyectiles
				ionesEnemigos[i].dibujar();
				ionesEnemigos[i].mover();
				
				//Colisiones
				
				//Si colisiona a un jugador
				if(nave != null && Detector.colisiona(nave.x,nave.y,ionesEnemigos[i].x,ionesEnemigos[i].y,rangoColision/2)) {
					System.out.println("colision con iones!!!!");
					nave=null;
				}
				
				//Desaparecerlo si esta fuera del mapa
				if(!Detector.estarEnEntorno(ionesEnemigos[i].x, ionesEnemigos[i].y, entorno)) {
					ionesEnemigos[i] = null;
				}
			}
			if(ionesEnemigos[i] ==  null) {
				int enemigoElegido = (int) (Math.random() * enemigos.length); //elije un enemigo entre el 0 y la cantidad de enenemigos
				while(enemigos[enemigoElegido] == null) {
					enemigoElegido = (int) (Math.random() * enemigos.length); //elije un enemigo entre el 0 y la cantidad de enenemigos
				}
				ionesEnemigos[i] = new ProyectilEnemigo(entorno,3,5,enemigos[enemigoElegido].x,enemigos[enemigoElegido].y);
			}
		}
		
		
		
		//ASTEROIDES
		for(int i=0;i<asteroides.length;i++) {
			asteroides[i].mover(entorno);
			asteroides[i].dibujar(entorno);
			//debug mode
			if(debugMode == true)
				asteroides[i].dibujarCaja();
			
			//Si un asteroide colisiona con el jugador
			if(nave != null  && asteroides[i] != null && 
				Detector.colisiona(nave.x,nave.y,asteroides[i].x,asteroides[i].y,rangoColision/2)) {
				System.out.println("colision con asteroide!!!!");
				nave=null;
			}
			//Si un proyectil de un jugador colisiona con un asteroide
			if(proyectilNave != null && asteroides[i] != null && Detector.colisiona(proyectilNave.x,proyectilNave.y,asteroides[i].x,asteroides[i].y,rangoColision/2)) {
				proyectilNave=null;
			}
			//Si un asteoride ya no es visible verticalmente en la pantalla
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
				
				//En caso de cerca de otro enemigo
				for(int j=0;j < enemigos.length;j++) {
					
					//Si esta muy cerca de una nave
					if (enemigos[j] != null  && enemigos[i]!=null && i != j &&
						Detector.colisiona(enemigos[i].x,enemigos[i].y, enemigos[j].x,enemigos[j].y,rangoColision)) {
						enemigos[i].cambiarAngulo();
						enemigos[j].cambiarAngulo();
					}
				}
				//En caso de colisiones con un proyectil de jugador
				
				if(nave != null && proyectilNave != null && enemigos[i]!=null && Detector.colisiona(enemigos[i].x,enemigos[i].y,proyectilNave.x,proyectilNave.y,rangoColision/2)) {
					enemigos[i]=null;
					proyectilNave=null;
					System.out.println("Colision proyectil");
				}
				
				
				//Si esta muy cerca de un asteroide
				for(int k=0;k<asteroides.length;k++) {
					if (asteroides[k] != null && enemigos[k]!=null &&
						Detector.colisiona(asteroides[i].x,asteroides[i].y,enemigos[k].x,enemigos[k].y,rangoColision)) {
							enemigos[i].cambiarAngulo();
						}
				}
				//Si enemigo colisiona con el jugador
				if (nave != null  && enemigos[i] != null &&
					Detector.colisiona(nave.x,nave.y,enemigos[i].x,enemigos[i].y,rangoColision)) {
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
			
			//Veo si queda alguna Enemigo vivo
			boolean sinEnemigos = true; //asume que no hay enemigos hasta que se demuestre lo contrario
			for(int i=0;i<enemigos.length;i++) {
				if(enemigos[i] != null) {
					sinEnemigos = false;
				}
			}
			if(sinEnemigos) {
				entorno.dibujarImagen(ganaste,400, 300, 0.0);
			}
			
		}else if (nave == null) {
			entorno.dibujarImagen(gameover,400, 300, 0.0);
			// Herramientas.cargarSonido("perdiste.wav").start();
			
		}	
	
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
