package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	
	// Variables de instancia
	double x, y, angulo, escala,velocidad;
	int tipo;
	Image img;
	Entorno e;
	
	public Proyectil(Entorno ent, int tipoProyectil, double vel, double inicioX, double inicioY) {
		this.e=ent;
		this.tipo=tipoProyectil;
		
		//Si es de jugador
		if(tipo == 1) {
			this.escala=1;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY - 70;
			this.angulo=-1;
			img = Herramientas.cargarImagen("asteroide.png");
		}
		//Si es Destructor Estelar
	}
	
	// Dibujar
	public void dibujar() {
		//Cambiar para proyectil astro o ion u otro
		if (tipo == 1)
			e.dibujarImagen(img, this.x, this.y, this.angulo, escala);
		else if (tipo == 2)
			e.dibujarImagen(img, this.x, this.y, this.angulo, escala);
	}
	// movimientos
	public void mover() {
		this.y += Math.sin(this.angulo)*velocidad;
		
		
	}

	
}
