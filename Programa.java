package teste;


import java.io.IOException;


// S carregar
public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Filogenia f = new Filogenia();
//		f.load(8,12);
		f.load(113,146);
		Algoritmo algoritmo = new Algoritmo();
//		algoritmo.execute(8, 12, 20);
		algoritmo.execute(113, 146, 30);
				
	}

}
