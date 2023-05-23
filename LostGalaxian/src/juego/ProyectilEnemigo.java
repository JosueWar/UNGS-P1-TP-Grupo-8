package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ProyectilEnemigo {
	// Variables de instancia
		double x, y, angulo, escala,velocidad,ancho,alto;
		int tipo;
		Image img;
		Entorno e;
		
		public ProyectilEnemigo(Entorno ent, double esc, double vel, double inicioX, double inicioY) {
			//Si es Destructor Estelar
			this.e=ent;
			this.escala=esc;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY + 40;
			this.angulo=Math.PI;
			img= Herramientas.cargarImagen("ionEnemigo.png");
			
			ancho=img.getWidth(e)*escala;
			alto=img.getHeight(e)*escala;
			
			
		}
		
		// Dibujar
		public void dibujar() {
			e.dibujarImagen(img, this.x, this.y, this.angulo, escala);
		}
		// movimientos
		public void mover() {
			this.y += Math.sin(1)*velocidad;
		}
		public void dibujarCaja() {
			Color color = new Color(255, 0, 0);
			e.dibujarRectangulo(x, y, img.getWidth(e)*escala, img.getHeight(e)*escala, 0, color);
		}
	}