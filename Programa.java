package teste;


// S carregar
public class Programa {

	public static void main(String[] args) {

		Filogenia f = new Filogenia();
		f.load(5,10);
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(5, 10, 20);
		
	}

}
