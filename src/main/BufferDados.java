package main;

public class BufferDados {
	private int inicio;
	private int fim;
	private int limite;
	private int buffer[];

	public BufferDados(int limite) {
		this.inicio = 0;
		this.fim = 0;
		this.limite = limite;
		this.buffer = new int[limite];
	}

	public void adicionarValorNoBuffer(int valor) {
		buffer[fim++ % limite] = valor;
	}

	public int retirarValorDoBuffer() {
		return buffer[inicio++ % limite];
	}
}
