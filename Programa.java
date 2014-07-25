package teste;

import java.io.IOException;

public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		// settings parameters 
		
		long MAXTEMPO = (1000 * 60 * 5); 	// in mills
		int QtdEspecies = 49;
		int QtdCaracteristicas = 59;
		int TmPopulacao = 100;
		int FatorItera = 100000; 				// count of algorithm iterations (FatorItera * QtdEspecies)   
		
		// hill climbing parameters
		
		int HCITER = 30; 					// count of iteration on hill climbing procedure
		
		Filogenia f = new Filogenia();
		f.load(QtdEspecies, QtdCaracteristicas, "src/teste/instancias/ANGI2.txt");
		
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(f, TmPopulacao, MAXTEMPO, FatorItera, HCITER);
				
	}
}
