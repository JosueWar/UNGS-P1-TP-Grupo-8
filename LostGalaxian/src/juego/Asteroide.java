package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroide
{
	// Variables de instancia
	double x,y;
	double angulo;
	double escala;
	double velocidad;
	Image img;
	Entorno entorno;
	
	public Asteroide(Entorno ent, double esc, double vel) 
	{
		this.entorno=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=Math.random()*entorno.ancho();
		this.y=-Math.random()*entorno.alto()*0.2;
		
		img = Herramientas.cargarImagen("asteroide.png");
		angulo=Utiles.generarRandomDouble(0,Math.PI);
		velocidad=Utiles.generarRandomDouble(1,5);
		 
	}
	public void crearAsteroides() {
	Asteroide[] asteroides = new Asteroide[4];
	for(int i=0;i<4;i++) {
		Asteroide a = new Asteroide(entorno, 0.5 ,Utiles.generarRandomDouble(1,5));
		asteroides[i]=a;
	}
	}
	
	public void dibujar(Entorno entorno)
	{
		entorno.dibujarImagen(img, this.x, this.y, this.angulo, this.escala);
       	
	}

			
	public void mover(Entorno e) {
		this.x += Math.cos(this.angulo)*velocidad;
		this.y += Math.sin(this.angulo)*velocidad;
		if(this.x > e.ancho()*1.05) {
			this.x=-e.ancho()*0.05;
		}
		if(this.x < -e.ancho()*0.05) {
			this.x=e.ancho()*1.05;
		}
		if(this.y > e.alto()*1.05) {
			this.y=-e.alto()*0.05;
		}
		if(this.y < -e.alto()*0.05) {
			this.y=e.alto()*1.05;
		}
		
	}
	//limites
	public void cambiarAngulo() {
		if(this.angulo > Math.PI/2) {
			this.angulo=Math.PI/4;
		}
		else
		{
			this.angulo=3 * Math.PI/4;
		}
	}
   
}


