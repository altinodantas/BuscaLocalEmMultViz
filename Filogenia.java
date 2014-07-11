package teste;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

// Problema
public class Filogenia {
	
	public static Taxo []folhas;
	public static int n;
	public static int m;
	
	public void load(int m, int n) throws IOException{
		folhas = new Taxo[m];
		
		for(int i = 0; i < m; i++){
			folhas[i] = folhaAleatoria(n, i);
		}
		this.n = n;
		this.m = m;
		
		for (int i = 0; i < folhas.length; i++) {
			for (int k = 0; k < folhas[i].caracteristicas.length; k++) {				
				System.out.print(folhas[i].caracteristicas[k]);
			}
			System.out.println();
		}
	}
	
	public void avaliar(Configuracao conf) {
		int f = 0;
		
		for(int i = m; i < (2*m-1); i++){
			f += conf.pais[i].custo;
		}
		conf.parcimonia = f;
	}
	
	
	Taxo folhaAleatoria(int n, int indice) throws IOException{
		Taxo tx = new Taxo();
		tx.caracteristicas = new int[n];
		
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/altino/workspace/BuscaLocal/src/teste/arquivo.txt"));  
		  
        while(br.ready()){  
           String linha = br.readLine();
           char[] testes = linha.toCharArray();  
           
           int y = Character.getNumericValue(testes[0]); 
           if((y - 1) == indice){
        	   
        	   for (int i = 1; i < testes.length; i++) {
        		   int x = Character.getNumericValue(testes[i]);
        		   tx.caracteristicas[i-1] = x;			
        	   }  
        	   
        	   break;
           }
           
        }  
        br.close();  
		
		tx.indice = indice;
		tx.filhoDireita = -1;
		tx.filhoEsquerda = -1;
		tx.custo = 0;
		
		return tx;
	}
	
}
