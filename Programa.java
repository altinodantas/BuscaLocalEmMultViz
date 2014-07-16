package teste;


import java.io.IOException;


// S carregar
public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Filogenia f = new Filogenia();
		f.load(49,59);
//		f.load(113,146);
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(49, 59, 100);
//		algoritmo.execute(113, 146, 30);
				
	}
}
