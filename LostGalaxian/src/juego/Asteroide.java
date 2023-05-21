package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroide
{
	// Variables de instancia
	double x, y, angulo, escala,velocidad;
	Image img1;
	Entorno e;
	
	public Asteroide(Entorno ent, double esc, double vel) 
	{
		this.e=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=Math.random()*e.ancho();
		this.y=-Math.random()*e.alto()*0.2;
		
		img1 = Herramientas.cargarImagen("asteroide.png");
		//Se tuvo que tener cuenta en radianes y la direccion de los asteroides
		angulo=Extras.generarRandomDouble(0,3.14);
		velocidad=Extras.generarRandomDouble(1,5);
		
		 
	}
	public void crearAsteroides() {
	Asteroide[] asteroides = new Asteroide[4];
	for(int i=0;i<4;i++) {
		Asteroide a = new Asteroide(e, 0.5 ,Extras.generarRandomDouble(1,5));
		asteroides[i]=a;
	}
	}
	
	public void dibujar(Entorno entorno)
	{
        //entorno.dibujarCirculo(x, y,50, Color.yellow);
		entorno.dibujarImagen(img1, this.x, this.y, this.angulo, this.escala);
       	
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
	public void dibujarCaja() {
		
		//System.out.println("Alto: "+img1.getHeight(e)*escala);
		//System.out.println("Ancho: "+img1.getWidth(e)*escala);
		
		Color color = new Color(255, 229, 204);
		e.dibujarRectangulo(x, y, img1.getWidth(e)*escala, img1.getHeight(e)*escala, angulo, color);
	}

   
}


