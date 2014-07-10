package teste;

import java.util.ArrayList;
import java.util.Random;

public class Configuracao {
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
		
		while(list.size() > 0){
			
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
			
			System.out.println(indice + " " + taxoA.indice + " " + taxoB.indice);
			pais[indice] = taxo;	
			nos[taxoA.indice] = indice;
			nos[taxoB.indice] = indice;
				
			indice++;
			list.add(taxo);
			if(list.size() == 1){
				break;
			}
		}
		
	}	
	

}
