package juego;

import java.util.Random;
public class Extras {
	public static double generarRandomDouble(double valorMinimo, double valorMaximo) {
		Random r= new Random();
		double valorRandom= valorMinimo+(valorMaximo-valorMinimo)*r.nextDouble();
		return valorRandom;
	}

	


}
