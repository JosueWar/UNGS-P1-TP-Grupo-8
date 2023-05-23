package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ProyectilEnemigo {
	// Variables de instancia
		double x, y, angulo, escala,velocidad;
		int tipo;
		Image img;
		Entorno entorno;
		
		public ProyectilEnemigo(Entorno ent, double esc, double vel, double inicioX, double inicioY) {
			//Si es Destructor Estelar
			this.entorno=ent;
			this.escala=esc;
			this.velocidad=vel;
			this.x=inicioX;
			this.y=inicioY + 40;
			this.angulo=Math.PI;
			img= Herramientas.cargarImagen("ionEnemigo.png");
		}
		
		// Dibujar
		public void dibujar() {
			entorno.dibujarImagen(img, this.x, this.y, this.angulo, escala);
		}
		// movimientos
		public void mover() {
			this.y += Math.sin(1)*velocidad;
		}
	}