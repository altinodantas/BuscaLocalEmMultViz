package teste;

import java.util.Random;

public class Filogenia {
	
	public static Taxo []folhas;
	public static int n;
	public static int m;
	
	public void load(int m, int n){
		folhas = new Taxo[m];
		
		for(int i = 0; i < m; i++){
			folhas[i] = folhaAleatoria(n, i);
		}
		this.n = n;
		this.m = m;
	}
	
	public void avaliar(Configuracao conf) {
		int f = 0;
		
		for(int i = 0; i < (2*m-2); i++){
			f += conf.pais[i].custo;
		}
		
		conf.parcimonia = f;
		
	}
	
	
	Taxo folhaAleatoria(int n, int indice){
		Taxo tx = new Taxo();
		tx.caracteristicas = new int[n];
		
		Random rand = new Random();
		for(int j = 0; j < n; j++){
			tx.caracteristicas[j] = rand.nextInt(2);
		}
		
		tx.indice = indice;
		tx.filhoDireita = -1;
		tx.filhoEsquerda = -1;
		tx.custo = 0;
		
		return tx;
		
	}
	
}
