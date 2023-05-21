package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Nave 
{
	// Variables de instancia
	double x;
	double y;
	double angulo;
	Image img1;
	Image img2;

	Entorno e;
	
	public Nave(Entorno ent,int x, int y) 
	{
		this.x = x;
		this.y = y;
		e = ent;
	
		img1 = Herramientas.cargarImagen("nave.png");
		img2 = Herramientas.cargarImagen("nave.png");
	}
	
	public void dibujarse(Entorno entorno)
	{
//		entorno.dibujarTriangulo(this.x, this.y, 50, 30, this.angulo, Color.yellow);
		//Cambiar para izq y der nave
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA))
			entorno.dibujarImagen(img1, this.x, this.y, this.angulo, 0.2);
		else
			entorno.dibujarImagen(img2, this.x, this.y, this.angulo, 0.2);
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
