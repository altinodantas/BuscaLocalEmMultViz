package teste;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


import java.io.IOException;



// S carregar
public class Programa {
	
	private static String[][] vetor;

<<<<<<< HEAD
	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Filogenia f = new Filogenia();
		f.load(8,12);
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute(8, 12, 20);
				
=======
	public static void main(String[] args) throws CloneNotSupportedException, FileNotFoundException {

//		Filogenia f = new Filogenia();
//		f.load(8,12);
//		Algoritmo algoritmo = new Algoritmo();
//		algoritmo.execute(8, 12, 20);
		
		vetor = new String[8][5];
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new FileReader("src/teste/arquivo.txt")).useDelimiter("\\n");
		for (int i = 0; i < 8; i++) {
			while (scanner.hasNext()) {
				String nota1 = scanner.next();
				String nota2 = scanner.next();
				String nota3 = scanner.next();
				String nota4 = scanner.next();
				vetor[i][0] = String.valueOf(i + 1);
				vetor[i][1] = nota1;
				vetor[i][2] = nota2;
				vetor[i][3] = nota3;
				vetor[i][4] = nota4;
			}
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(vetor[i][j] + " ");
			}
			System.out.println();
		}
>>>>>>> origin/master
	}

}
