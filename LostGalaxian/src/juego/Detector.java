package juego;

import entorno.Entorno;

public class Detector {
	
	//Colisiones y limites de mapa
	public static boolean tocarBorde(double x, double y, Entorno e) {
    	return x <10 || x > e.ancho()-10;
    }
	
	//El objeto esta en el mapa?
	public static boolean estarEnEntorno(double x, double y, Entorno e) {
		return !(x < - e.ancho()*0.2 || x > e.ancho()*1.2 ||
				y < - e.alto()*0.2  || y > e.alto()*1.2);  
	}
	
}
