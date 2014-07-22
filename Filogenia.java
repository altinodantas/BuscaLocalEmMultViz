package teste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// Problema
public class Filogenia {
	
	public static Taxon []folhas;
	public static int n;
	public static int m;
	public static int cont = 0;
	public static String instancia;
	public static int upperbound;
	
	public void load(int m, int n, String arquivo) throws IOException{
		this.n = n;
		this.m = m;
		folhas = new Taxon[m];
		
		for(int i = 0; i < m; i++){
			folhas[i] = gerarFolha(n, i, arquivo);
		}
		
		//define instance name
		String[] str = 		arquivo.split("/");
		String instancia =  str[str.length-1].replace(".", "-");
		String[] strNome = 	instancia.split("-",2);
		this.instancia = strNome[0];
		
		// ceate folder if there isn't
		File file = new File("src/teste/instancias/solucoes/"+this.instancia);  
		if (!file.exists()) {
			file.mkdir(); 			
		}
	}
	
	public void avaliar(Configuracao conf) {
		
		//Evaluates if parsimony ins't infinite  
		if(conf.parcimonia != Integer.MAX_VALUE){			
			int f = 0;
			
			for(int i = m; i < (2*m-1); i++){
				f += conf.pais[i].custo;
			}
			conf.parcimonia = f;
		}
	}
	
	//read the instance file and set the taxon - file should have the correct format
	
	Taxon gerarFolha(int n, int indice, String arquivo) throws IOException{
		Taxon tx = new Taxon();
		tx.caracteristicas = new int[n];
		int Clinha = -1;
		String[] vetorLinha = null;
		
		BufferedReader br = new BufferedReader(new FileReader(arquivo));  
		  
        while(br.ready()){  
           String linha = br.readLine();
           vetorLinha = linha.split(" ", 2);
           
           char[] testes = vetorLinha[1].toCharArray();  
           
           int startIndiceCar;
           for(startIndiceCar=0;Character.getNumericValue(testes[startIndiceCar]) == -1;startIndiceCar++);
           
           if(Clinha == indice){
        	   
        	   for (int i = startIndiceCar, ix = 0; ix < n; i++, ix++) {
        		   int x = Character.getNumericValue(testes[i]);
        		   tx.caracteristicas[ix] = x;			
        	   }  
        	   
        	   break;
           }
           Clinha++;
           
        }  
        br.close(); 
 
		tx.nome = vetorLinha[0];
		tx.indice = indice;
		tx.filhoDireita = -1;
		tx.filhoEsquerda = -1;
		tx.custo = 0;
		
		return tx;
	}
	
}
