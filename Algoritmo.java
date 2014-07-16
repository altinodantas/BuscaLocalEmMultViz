package teste;

import java.util.Random;

// Processo Evolucionario - Autor: Altino
public class Algoritmo {
	
	public Configuracao[] configuracoes;
	public Filogenia filogenia;
	public Random random = new Random();
	public boolean Revalidar = false;

	public void execute(int m, int n, int p) throws CloneNotSupportedException {
		configuracoes = new Configuracao[p];
		filogenia = new Filogenia();
		
		// Geracao da Populacao Inicial
		for (int i = 0; i < p; i++) {
			Configuracao conf = new Configuracao();
			conf.inicializar(m,n);
			filogenia.avaliar(conf);
			configuracoes[i] = conf;
		}
		
		System.out.println("PARCIMONIA INICIAL");
		for (int i = 0; i < configuracoes.length; i++) {
			System.out.println("Parcimonia: " + i + " >> " + configuracoes[i].parcimonia);
		}
		
		
		for (int i = 0; i < m*20000; i++) {
			Random rand = new Random();			
			
			Configuracao confX = new Configuracao();
			
			// executa 90% de cruzamento guiado e 10% de mutação
			if(rand.nextDouble() <= 0.95){
				
				Configuracao confXY = new Configuracao();
				
				Configuracao confR = new Configuracao();
				
				confXY = selecao();
				confX = selecao();
				confR = combinar(confXY, confX, 0.3);
				confX.reconstruir();
				filogenia.avaliar(confX);
				
			}else{
				
				confX = selecao();	
				if(i % 2 == 0){
					confX = perturbar(confX,1);
				}else{
					confX = perturbar(confX,2);
				}
				confX.reconstruir();
				filogenia.avaliar(confX);
				
			}
			
			
			if (confX.parcimonia < configuracoes[piorConfiguracao()].parcimonia) {
				System.out.println(confX.parcimonia);
				configuracoes[piorConfiguracao()] = confX;
			}
			
		
		}
		
		
		System.out.println("PARCIMONIA FINAL");
		for (int i = 0; i < configuracoes.length; i++) {
			System.out.println("Parcimonia: " + i + " >> " + configuracoes[i].parcimonia);
		}
		
		for (int j = 0; j < configuracoes[0].nos.length; j++) {
			System.out.print(configuracoes[melhorConfiguracao()].nos[j] + " ");
		}
		System.out.println();
		
		int melhor = melhorConfiguracao();
		System.out.println("MELHOR CONFIGURAÇÃO - FINAL ["+ melhor + "]");
		
		for (int i = m; i < configuracoes[melhor].pais.length; i++) {
			System.out.println(configuracoes[melhor].pais[i].indice + " | " + configuracoes[melhor].pais[i].custo);
			for (int j = 0; j < configuracoes[melhor].pais[i].caracteristicas.length; j++) {
				System.out.print(configuracoes[melhor].pais[i].caracteristicas[j] + " ");				
			}
			System.out.println();
		}
		
		System.out.println(configuracoes[melhor].parcimonia);
		
	}
	
	public int piorConfiguracao() {
		int maior = this.configuracoes[0].parcimonia;
		int indice = 0;
		
		for (int i = 1; i < configuracoes.length; i++) {
			if (configuracoes[i].parcimonia > maior) {
				maior = configuracoes[i].parcimonia;
				indice = i;
			}
		}
		
		return indice;
	}
	
	public int melhorConfiguracao() {
		int melhor = this.configuracoes[0].parcimonia;
		int indice = 0;
		
		for (int i = 1; i < configuracoes.length; i++) {
			if (configuracoes[i].parcimonia < melhor) {
				melhor = configuracoes[i].parcimonia;
				indice = i;
			}
		}
		
		return indice;
	}
	
	public Configuracao selecao() throws CloneNotSupportedException {
		Configuracao escolhido = new Configuracao();
		Configuracao eleito1 = configuracoes[random.nextInt(configuracoes.length)];
		Configuracao eleito2 = configuracoes[random.nextInt(configuracoes.length)];
		
		escolhido.nos = new int[eleito1.nos.length];
		
		if (eleito1.parcimonia <= eleito2.parcimonia) {
//			escolhido = eleito1.copia();
			for (int i = 0; i < eleito1.nos.length; i++) {
				escolhido.nos[i] = eleito1.nos[i];
			}
			
			escolhido.parcimonia = eleito1.parcimonia;
			
		} else {
			for (int i = 0; i < eleito2.nos.length; i++) {
				escolhido.nos[i] = eleito2.nos[i];
			}
		
			escolhido.parcimonia = eleito1.parcimonia;
//			escolhido = eleito2.copia();
		}
		
		return escolhido;
	}
	
	public Configuracao perturbar(Configuracao eleito, int k) throws CloneNotSupportedException {
		
		int no1, no2, no3;
		
		switch (k) {
		case 1:
			
			no1 = random.nextInt((eleito.nos.length - 1));
			no2 = random.nextInt((eleito.nos.length - 1));
			
			this.swap(eleito, no1, no2);
			
			break;
			
		case 2:
			
			no1 = random.nextInt((eleito.nos.length - 1));
			no2 = random.nextInt((eleito.nos.length - 1));
			no3 = random.nextInt((eleito.nos.length - 1));
			
			this.swap(eleito, no1, no2);
			this.swap(eleito, no2, no3);
			
			break;
			
		case 3:
			
			no1 = random.nextInt((eleito.nos.length - 1));
			no2 = random.nextInt((eleito.nos.length - 1));
			

		default:
			break;
		}
		
		
		return eleito;
	}
	
	// Método de cruzamento guiado de características
	public Configuracao combinar(Configuracao ConfA, Configuracao ConfB, double percentual){
				
		int n = ConfA.nos.length-1;
		int rand = random.nextInt(n);
		int razao = (int) (n * percentual);
		int limiteSuperior, limiteInferior;
		
		if(rand <= (n - razao)){
			limiteSuperior = rand + razao;
			limiteInferior = rand;
		}else{
			limiteSuperior = rand;
			limiteInferior = rand - razao;
		}
		
		for (int i = limiteInferior; i <= limiteSuperior; i++) {
			
			if(ConfB.nos[i] != ConfA.nos[i]){				
				
				for (int j = 0; j < ConfB.nos.length - 1; j++) {
					if(ConfB.nos[j] == ConfA.nos[i]){
						this.swapSimples(ConfB, i, j);
						if(!ConfB.viavel()){
							this.swapSimples(ConfB, i, j);
						}
						break;
					}
				
				}
			
			}
			
		}
		
		return ConfB;
		
	}
	
	// faz o swap de duas posições no vetor NOS garantindo a viabilidade;
	public void swap(Configuracao c, int i, int j){
		int aux;
		
		aux = c.nos[i];
		c.nos[i] = c.nos[j];
		c.nos[j]=aux;
		
		if(!c.viavel()){
			this.Revalidar = true;
			this.swap(c, j, i);
		}else{
			if(this.Revalidar){
				this.Revalidar = false;
				int n1 = random.nextInt((c.nos.length - 1));
				int n2 = random.nextInt((c.nos.length - 1));
				this.swap(c, n1, n2);
			}
			
		}
	}
	
	// executa o swap entre dois valores do vetor NOS sem garantir validade
	public void swapSimples(Configuracao c, int i, int j){
		int aux;
		
		aux = c.nos[i];
		c.nos[i] = c.nos[j];
		c.nos[j]=aux;
	}

}
