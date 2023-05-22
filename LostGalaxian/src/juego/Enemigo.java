package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x, y, angulo,escala,velocidad,ancho,alto;
	Image img;
	Entorno e;

	public Enemigo(Entorno ent, double esc, double vel) {
		this.e=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=Math.random()*e.ancho();
		this.y=-Math.random()*e.alto()*0.2;
		this.angulo=Math.PI / 4.0 + (Math.random()>0.5?1:0) * Math.PI/2;
		this.img=Herramientas.cargarImagen("enemigo.png");
		
		ancho=img.getWidth(e)*escala;
		alto=img.getHeight(e)*escala;

	} 

	

	// Dibujar
	public void dibujar() {
		this.e.dibujarImagen(this.img, this.x, this.y, 0.0, this.escala);
	}
	// movimientos
	public void mover() {
		this.x =this.x + this.velocidad * Math.cos(angulo);
		this.y =this.y + this.velocidad * Math.sin(angulo) * 0.1;
		
		//Cambia de angulo si toca el borde de la pantalla
		if(Math.random() > 0.995 || Detector.tocarBordeEjeX(this.x,this.e)) {
		   cambiarAngulo();
		}
		
	}
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
		Color color = new Color(255, 102, 102);
		e.dibujarRectangulo(x, y, img.getWidth(e)*escala, img.getHeight(e)*escala, 0, color);
	}




}
