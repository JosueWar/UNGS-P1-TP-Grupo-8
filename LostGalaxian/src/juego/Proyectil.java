package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	
	// Variables de instancia
	double x, y, angulo, escala,velocidad;
	int tipo;
	Image proyecNave;
	Image proyecEnem;
	Entorno e;
	
	public Proyectil(Entorno ent, int tipoProyectil, double vel, double inicioX, double inicioY) {
		this.e=ent;
		this.tipo=tipoProyectil;
		
		//Si es de jugador
		if(tipo == 1) {
			this.escala=1;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY - 80;
			this.angulo=-1;
			proyecNave= Herramientas.cargarImagen("Proyectil.png");
		}
		//Si es Destructor Estelar
		if(tipo == 2) {
			this.escala=1;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY + 80;
			this.angulo=1;
			proyecEnem= Herramientas.cargarImagen("Proyectil.png");
		}
		//Si es Jefe
	}
	
	// Dibujar
	public void dibujar() {
		//Cambiar para proyectil astro o ion u otro
		e.dibujarImagen(proyecNave, this.x, this.y, this.angulo, escala);
	}
	// movimientos
	public void mover() {
		if(tipo == 1)
			this.y += Math.sin(this.angulo)*velocidad;
		
		
	}

	
}
