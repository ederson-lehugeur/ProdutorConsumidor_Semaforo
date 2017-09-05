package main;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import consumidor.Consumidor;
import produtor.Produtor;

public class Main {
	public static void main(String[] args) {
		int numeroFill = 0;
		int numeroEmpty = 100;
		BufferDados buffer = new BufferDados(numeroEmpty);
		Semaphore fill = new Semaphore(numeroFill);
		Semaphore empty = new Semaphore(numeroEmpty);
		Lock mutex = new ReentrantLock();

		Produtor produtor = new Produtor(1, buffer, fill, empty, mutex);
		Consumidor consumidor = new Consumidor(1, buffer, fill, empty, mutex);

		produtor.start();
		consumidor.start();
	}
}
