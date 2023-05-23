package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x, y, angulo,escala,velocidad,ancho,alto;
	Image img;
	Entorno entorno;

	public Enemigo(Entorno ent, double esc, double vel, double diferenciaAltura) {
		this.entorno=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=Math.random()*entorno.ancho();
		this.y=diferenciaAltura*70;
		this.angulo=Math.PI / 4.0 + (Math.random()>0.5?1:0) * Math.PI/2;
		this.img=Herramientas.cargarImagen("enemigo.png");
		
		ancho=img.getWidth(entorno)*escala;
		alto=img.getHeight(entorno)*escala;

	} 

	

	// Dibujar
	public void dibujar() {
		this.entorno.dibujarImagen(this.img, this.x, this.y, 0.0, this.escala);
	}
	// movimientos
	public void mover() {
		this.x =this.x + this.velocidad * Math.cos(angulo);
		this.y =this.y + this.velocidad * Math.sin(angulo) * 0.1;
		
		//Cambia de angulo si toca el borde de la pantalla o se va de la pantalla
		if(Detector.tocarBordeEjeX(this.x,this.entorno)) {
			if(this.x > 0)
				this.x -= 10;
			else if(this.x < 5)
				this.x += 15;
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
		entorno.dibujarRectangulo(x, y, img.getWidth(entorno)*escala, img.getHeight(entorno)*escala, 0, color);
	}




}
