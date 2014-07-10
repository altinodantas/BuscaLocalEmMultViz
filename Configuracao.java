package teste;

import java.util.ArrayList;
import java.util.Random;

// Cromossomo/Indivuo
public class Configuracao implements Cloneable {
	public int nos[];
	public Taxo pais[];
	public int parcimonia;
	
	public void inicializar(int m, int n) {
		ArrayList<Object> list = new ArrayList<Object>();
		int a, b, indice = m;
		pais = new Taxo[2 * m - 1];
		nos = new int[2 * m - 2];
		
		for(int i = 0; i < m; i++){
			list.add(Filogenia.folhas[i]);
		}
		
		Random r = new Random();
		
		while(list.size() > 1){
			
			a = r.nextInt(list.size());
			Taxo taxoA = (Taxo) list.get(a);
			list.remove(a);
			
			b = r.nextInt(list.size());
			Taxo taxoB = (Taxo) list.get(b);
			list.remove(b);
			
			Taxo taxo = new Taxo();
			taxo.filhoDireita = a;
			taxo.filhoEsquerda = b;
			taxo.indice = indice;
			taxo.custo = 0;
			
			taxo.caracteristicas = new int[n];
			
			for(int i = 0; i < n; i++){
				if(taxoA.caracteristicas[i] + taxoB.caracteristicas[i] == 1){
					taxo.caracteristicas[i] = 2;
					taxo.custo += 1;
				}else{
					
					if(taxoA.caracteristicas[i] < taxoB.caracteristicas[i]){
						taxo.caracteristicas[i] = taxoA.caracteristicas[i];
					}else{
						taxo.caracteristicas[i] = taxoB.caracteristicas[i];	
					}
				}
			}
			
			pais[indice] = taxo;	
			nos[taxoA.indice] = indice;
			nos[taxoB.indice] = indice;
				
			indice++;
			list.add(taxo);
		}
			
	}
	
	
	//verifica se uma configura��o � vi�vel
	
	public boolean viavel(){
		int vetor[] = new int[this.pais.length]; 
		
		for(int i = this.nos.length - 2; i >= 0; i-- ){
			if(this.nos[i] <= i){
//				System.out.println("Erro de indice: " + i);
				return false;
			}
		}
		
		for (int i = 0; i < vetor.length; i++) {
			vetor[i]=0;
		}
		
		for (int i = 0; i < vetor.length - 1; i++) {
			vetor[this.nos[i]] += 1;
		}
		
		for (int i = this.nos.length; i < vetor.length; i++) {
			if(vetor[i] != 2){
//				System.out.println("Erro de quantidade: " + vetor[i]);
				return false;
			}
		}
		return true;
	}
	
	
	public void reconstruir() {
		
		for (int i = 0; i < Filogenia.folhas.length; i++) {
			this.pais[i] = Filogenia.folhas[i];
		}
	
		for(int i = 0; i < this.nos.length - 1; i ++){
			
			for (int j = i + 1; j < this.nos.length; j++) {
				
				if (this.nos[i] == this.nos[j]) {
					
					int indicePai = this.nos[i];
					this.pais[indicePai].custo = 0;
					this.pais[indicePai].filhoDireita = i;
					this.pais[indicePai].filhoEsquerda = j;

					for(int ix = 0; ix < this.pais[i].caracteristicas.length; ix++){						
						
						if(this.pais[i].caracteristicas[ix] + this.pais[j].caracteristicas[ix] == 1){
							this.pais[indicePai].caracteristicas[ix] = 2;
							this.pais[indicePai].custo += 1;
						}else{
							if(this.pais[i].caracteristicas[ix] < this.pais[j].caracteristicas[ix]){
								this.pais[indicePai].caracteristicas[ix] = this.pais[i].caracteristicas[ix];
							}else{
								this.pais[indicePai].caracteristicas[ix] = this.pais[j].caracteristicas[ix];	
							}
						}
					}
				}
			}
		}
	}
	
	public Configuracao copia() throws CloneNotSupportedException {
		return (Configuracao) this.clone();
	}
	
}	
	


