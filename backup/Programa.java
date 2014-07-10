package teste;

import java.util.Random;

public class Programa {

	public static void main(String[] args) {

		Filogenia f = new Filogenia();
		f.load(5,10);
		Configuracao conf = new Configuracao();
		conf.inicializar(5,10);
		
		for(int i = 0; i < 5; i++){
			System.out.println(f.folhas[i].indice + "\n");
			for(int j = 0; j < 10; j++){
				System.out.print(f.folhas[i].caracteristicas[j] + " ");
			}
			System.out.println("\n");
		}
		
		
		
		
	}

}
