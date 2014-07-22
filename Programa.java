package teste;

import java.io.IOException;

public class Programa {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		// settings parameters 
		
		long MAXTEMPO = (1000 * 60 * 5); 	// in mills
		int QtdEspecies = 64;
		int QtdCaracteristicas = 147;
		int TmPopulacao = 130;
		int FatorItera = 100000; 			// count of algorithm iterations (FatorItera * QtdEspecies)   
		
		// hill climbing parameters
		
		long HCDELAY= (1000 * 30); 			// time between which hill climbing procedure. For NO HILL CLIMBING put equal MAXTEMPO  
		int HCITER = 30; 					// count of iteration on hill climbing procedure
		
		Filogenia f = new Filogenia();
		f.load(QtdEspecies, QtdCaracteristicas, "src/teste/instancias/tst12.txt");
		
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(f, TmPopulacao, MAXTEMPO, FatorItera, HCDELAY, HCITER);
				
	}
}
