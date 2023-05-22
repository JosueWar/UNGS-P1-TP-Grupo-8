package juego;

import entorno.Entorno;

public class Detector {
	
	//Colisiones y limites de mapa
	public static boolean tocarBordeEjeX(double x, Entorno e) {
    	return x <10 || x > e.ancho()-10;
    }
	public static boolean colisiona(double x1,double y1,double ancho1,double alto1, double x2,double y2,double ancho2,double alto2) {
		double ax1 = x1 - ancho1 / 2;
		double ay1 = y1 - alto1 / 2;
		double ax2 = x1 + ancho1 / 2;
		double ay2 = y1 + alto1 / 2;
		
		double bx1 = x2 - ancho2 / 2;
		double by1 = y2 - alto2 / 2;
		double bx2 = x2 + ancho2 / 2;
		double by2 = y2 + alto2 / 2;
		
		boolean colisionX = (ax1 <= bx2) && (ax2 >= bx1);
		boolean colisionY = (ay1 <= by2) && (ay2 >= by1);
		
		return colisionX && colisionY;
	}
	
	//El objeto esta en el mapa?
	public static boolean estarEnEntorno(double x, double y, Entorno e) {
		return !(x < - e.ancho()*0.2 || x > e.ancho()*1.2 ||
				y < - e.alto()*0.2  || y > e.alto()*1.2);  
	}
	
}
