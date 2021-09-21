package controller;

import java.util.concurrent.Semaphore;

import br.edu.fateczl.vinicius.pilhaDouble.Pilha;


public class ControllerCorrida extends Thread {

	Pilha Voltas = new Pilha();
	Pilha Melhores_tempos = new Pilha();
	private String escuderia;
	private int corredor;
	private Semaphore semaphore;
	double[] grid = new double[14];
	int contador = 0;

	public ControllerCorrida(String escuderia, int corredor, Semaphore semaphore) {
		this.escuderia = escuderia;
		this.corredor = corredor;
		this.semaphore = semaphore;
	}

	public void run() {

		liberarcorrida(corredor, escuderia);
		ordenar(corredor, escuderia);

	}

	private void liberarcorrida(int corredor, String escuderia) {
		System.out.println("Piloto " + corredor + " Escuderia " + escuderia + " ==> Está no BOX ");

		switch (escuderia) {
		case "A":
			liberar(corredor, escuderia);
			break;
		case "B":
			liberar(corredor, escuderia);
			break;
		case "C":
			liberar(corredor, escuderia);
			break;
		case "D":
			liberar(corredor, escuderia);
			break;
		case "E":
			liberar(corredor, escuderia);
			break;
		case "F":
			liberar(corredor, escuderia);
			break;
		case "G":
			liberar(corredor, escuderia);
			break;
		}
	}

	private void liberar(int corredor, String escuderia) {
		int permissoes = 1;
		Semaphore unico = new Semaphore(permissoes);
		try {
			unico.acquire();
			corrida(corredor, escuderia);
		} catch (Exception error) {
			error.printStackTrace();
		} finally {
			unico.release();
		}

	}

	private void corrida(int corredor, String escuderia) {
		try {
			semaphore.acquire();
			System.err.println("Piloto " + corredor + " Escuderia " + escuderia + " ==> Iniciou a corrida");
			int voltas = 0;
			int distancia = 0;
			int i = 0;
			for (voltas = 0; voltas < 3; voltas++) {
				double tempoInicial = System.nanoTime();// Armazenou o tempo inicial
				while (distancia < 5000) {
					distancia += (int) (Math.random() * 300 + 1);
				}
				double tempoFinal = System.nanoTime();// Armazenar o tempo final
				double tempoTotal = tempoFinal - tempoInicial;

				Voltas.push(tempoTotal);
				tempoTotal = tempoTotal / Math.pow(10, 9);
				System.err.println("Piloto " + corredor + " Escuderia " + escuderia + " " + "==> Volta " + i++
						+ " Tempo " + tempoTotal);
			}
			System.err.println("Piloto " + corredor + " Escuderia " + escuderia + " ==> Parou de Correr");
		} catch (Exception error) {
			error.printStackTrace();
		} finally {
			semaphore.release();

		}
	}

	private void ordenar(int corredor, String escuderia) {
		double tempo = 0;
		int permissoes = 1;
		Semaphore unico = new Semaphore(permissoes);

		// while (Voltas.size() >= 1) { //Computador fica com 100% de uso e não compila
		for (int i = 0; i < 3; i++) {
			try {
				if (tempo <= Voltas.top()) {
					tempo = Voltas.pop();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			unico.acquire();
			Melhores_tempos.push(tempo);
			tempo = tempo / Math.pow(10, 9);
			grid[corredor - 1] = tempo;
			contador++;
			System.out.println(
					"Piloto " + corredor + " Escuderia " + escuderia + " ==> Melhor tempo " + grid[corredor - 1] + "s");
		} catch (Exception error) {
			error.printStackTrace();
		} finally {
			unico.release();
		}
	}
}
