package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Nave 
{
	// Variables de instancia
	double x,y,escala;
	double angulo;
	Image img1;
	Entorno entorno;
	
	public Nave(Entorno ent,double x, double y, double esc) 
	{
		this.x = x;
		this.y = y;
		this.escala = esc;
		entorno = ent;
	
		img1 = Herramientas.cargarImagen("nave.png");
	}
	
	public void dibujarse(Entorno entorno){
		entorno.dibujarImagen(img1, this.x, this.y, this.angulo, escala);
	}
	
	public void mover(double direccion, Entorno entorno) {
		//Segun positivo seria derecha y negativo izquierda
		if(!Detector.tocarBordeEjeX(this.x,entorno))
			this.x += direccion;
		else
			if(this.x > 0)
				this.x -= 10;
			else if(this.x < 5)
				this.x += 15;
	}
}
