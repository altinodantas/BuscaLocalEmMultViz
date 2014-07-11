package teste;


import java.io.IOException;



// S carregar
public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Filogenia f = new Filogenia();
		f.load(8,12);
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(8, 12, 20);
				
	}

}
