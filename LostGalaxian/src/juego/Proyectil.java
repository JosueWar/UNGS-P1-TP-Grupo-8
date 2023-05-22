package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	
	// Variables de instancia
	double x, y, angulo, escala,velocidad,ancho,alto;
	int tipo;
	Image img;
	Entorno e;
	
	public Proyectil(Entorno ent, int tipoProyectil, double esc, double vel, double inicioX, double inicioY) {
		this.e=ent;
		this.tipo=tipoProyectil;
		
		//Si es de jugador
		if(tipo == 1) {
			this.escala=esc;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY - 80;
			this.angulo=0;
			img= Herramientas.cargarImagen("Proyectil.png");
			
			ancho=img.getWidth(e)*escala;
			alto=img.getHeight(e)*escala;
		}
		//Si es Destructor Estelar
		if(tipo == 2) {
			this.escala=esc;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY + 80;
			this.angulo=0;
			img= Herramientas.cargarImagen("Proyectil.png");
			
			ancho=img.getWidth(e)*escala;
			alto=img.getHeight(e)*escala;
		}
		//Si es Jefe
	}
	
	// Dibujar
	public void dibujar() {
		//Cambiar para proyectil astro o ion u otro
		e.dibujarImagen(img, this.x, this.y, this.angulo, escala);
	}
	// movimientos
	public void mover() {
		if(tipo == 1)
			this.y += Math.sin(-1)*velocidad;
	}
	
	public void dibujarCaja() {
		
		//System.out.println("Alto: "+img1.getHeight(e)*escala);
		//System.out.println("Ancho: "+img1.getWidth(e)*escala);
		
		Color color = new Color(255, 0, 0);
		e.dibujarRectangulo(x, y, img.getWidth(e)*escala, img.getHeight(e)*escala, 0, color);
	}

	
}
