package view;

import java.util.concurrent.Semaphore;
import controller.ControllerCorrida;

public class Principal {

	public static void main(String[] args) {
		int permissoes = 5;
		String[] escuderia = new String[7];
		int corredor = 0;
		escuderia[0] = "A";
		escuderia[1] = "B";
		escuderia[2] = "C";
		escuderia[3] = "D";
		escuderia[4] = "E";
		escuderia[5] = "F";
		escuderia[6] = "G";
		Semaphore semaphore = new Semaphore(permissoes);
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 7; i++) {
				corredor+= 1;
				Thread Corrida = new ControllerCorrida(escuderia[i], corredor ,semaphore);
				Corrida.start();
			}
		}
		
		
	}

}
