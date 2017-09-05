package consumidor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import main.BufferDados;

public class Consumidor extends Thread {
	private int idThread;
	private int valor;
	private Semaphore fill;
	private Semaphore empty;
	private BufferDados buffer;
	private Lock mutex;

	public Consumidor(int idThread, BufferDados buffer, Semaphore fill, Semaphore empty, Lock mutex) {
		this.idThread = idThread;
		this.buffer = buffer;
		this.fill = fill;
		this.empty = empty;
		this.mutex = mutex;
	}

	public void get() {
		try {
			valor = buffer.retirarValorDoBuffer();
			System.out.println("Thread Consumidor #" + idThread + " retirando valor " + valor + " do buffer...");
			Thread.sleep((long)(Math.random() * 500));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				// Proberen (Decrementar)
				fill.acquire();
				mutex.lock();
				get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				mutex.unlock();
				// Verhogen (Incrementar)
				empty.release();
			}
		}
	}
}
