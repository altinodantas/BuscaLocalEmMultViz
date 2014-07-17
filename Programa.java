package teste;


import java.io.IOException;


// S carregar
public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Filogenia f = new Filogenia();
		f.load(71,159);
//		f.load(113,146);
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(71, 159, 100);
//		algoritmo.execute(113, 146, 100);
				
	}
}
