package teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Problema
public class Filogenia {
	
	public static Taxo []folhas;
	public static int n;
	public static int m;
	public static int cont = 0;
	
	public void load(int m, int n) throws IOException{
		folhas = new Taxo[m];
		
		for(int i = 0; i < m; i++){
			folhas[i] = folhaAleatoria(n, i);
		}
		this.n = n;
		this.m = m;

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
		int Clinha = 0;
		
		BufferedReader br = new BufferedReader(new FileReader("src/teste/arquivo.txt"));  
		  
        while(br.ready()){  
           String linha = br.readLine();
           char[] testes = linha.toCharArray();  
           
           if(Clinha == indice){
        	   
        	   for (int i = 0; i < testes.length; i++) {
        		   int x = Character.getNumericValue(testes[i]);
        		   tx.caracteristicas[i] = x;			
        	   }  
        	   
        	   break;
           }
           Clinha++;
           
        }  
        br.close(); 
 
		
		tx.indice = indice;
		tx.filhoDireita = -1;
		tx.filhoEsquerda = -1;
		tx.custo = 0;
		
		return tx;
	}
	
}
