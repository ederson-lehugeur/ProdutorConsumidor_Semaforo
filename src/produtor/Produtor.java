package produtor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import main.BufferDados;

public class Produtor extends Thread {
	private int idThread;
	private int valor;
	private Semaphore fill;
	private Semaphore empty;
	private BufferDados buffer;
	private Lock mutex;

	public Produtor(int idThread, BufferDados buffer, Semaphore fill, Semaphore empty, Lock mutex) {
		this.idThread = idThread;
		this.buffer = buffer;
		this.fill = fill;
		this.empty = empty;
		this.mutex = mutex;
	}

	public void put() {
		try {
			valor = (int)(Math.random() * 100 + 1);
			System.out.println("Thread Produtor #" + idThread + " colocando valor " + valor + " no buffer...");
			buffer.adicionarValorNoBuffer(valor);
			Thread.sleep((long)(Math.random() * 250));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				// Proberen (Decrementar)
				empty.acquire();
				mutex.lock();
				put();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				mutex.unlock();
				// Verhogen (Incrementar)
				fill.release();
			}
		}
	}
}