package juego;

public class Extras {
	public static double generarRandomDouble(double valorMinimo, double valorMaximo) {
		
	    double rango = valorMaximo - valorMinimo; //Calcular el rango de números posibles
	    double numeroAleatorio = Math.random() * rango; //Generar un número aleatorio dentro del rango
	    numeroAleatorio += valorMinimo; //Ajustar el número aleatorio al rango deseado
	    
	    return numeroAleatorio;
	}
	public static int generarRandom(int valorMinimo, int valorMaximo) {
		
	    int rango = valorMaximo - valorMinimo + 1; //Calcular el rango de números posibles
	    int numeroAleatorio = (int) (Math.random() * rango); //Generar un número aleatorio dentro del rango
	    numeroAleatorio += valorMinimo; //Ajustar el número aleatorio al rango deseado
	    
	    return numeroAleatorio;
	}
}
