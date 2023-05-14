package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroide
{
	// Variables de instancia
	double x;
	double y;
	double angulo;
	Image img1;
	Entorno e;
	double velocidad;
 
	public Asteroide() 
	{
		this.x = Extras.generarRandomDouble(20,200);
		this.y = Extras.generarRandomDouble(20,300);
		img1 = Herramientas.cargarImagen("asteroide.png");
		angulo=Extras.generarRandomDouble(0,3.14*0.5);
		velocidad=Extras.generarRandomDouble(1,5);;
		 
	}
	
	public void dibujarse(Entorno entorno)
	{
        //entorno.dibujarCirculo(x, y,50, Color.yellow);
		entorno.dibujarImagen(img1, this.x, this.y, this.angulo, 1);
       	
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

    public boolean tocarBorde() {
    	return this.x <10 || this.x > e.ancho()-10;
    }
	public boolean estarEnEntorno() {
		return !(this.x < - e.ancho()*0.2 || this.x > e.ancho()*1.2 ||
				this.y < - e.alto()*0.2  || this.y > e.alto()*1.2);  
	}
}


