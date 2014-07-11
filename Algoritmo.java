package teste;

import java.util.Random;

// Processo Evolucionario - Autor: Altino
public class Algoritmo {
	
	public Configuracao[] configuracoes;
	public Filogenia filogenia;
	public Random random = new Random();

	/**
	 * @param m
	 * @param n
	 * @param p
	 * @throws CloneNotSupportedException
	 */
	/**
	 * @param m
	 * @param n
	 * @param p
	 * @throws CloneNotSupportedException
	 */
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
		
		
		for (int i = 0; i < 100*m; i++) {
			Configuracao confX = new Configuracao();
			
			do {
				confX = perturbar();
			} while (!confX.viavel());
			
			//confX.reconstruir();
			filogenia.avaliar(confX);
			
			if (confX.parcimonia < configuracoes[piorConfiguracao()].parcimonia) {
				//configuracoes[piorConfiguracao()] = confX;
			}
		}
		
		System.out.println("PARCIMONIA FINAL");
		for (int i = 0; i < configuracoes.length; i++) {
			System.out.println("Parcimonia: " + i + " >> " + configuracoes[i].parcimonia);
		}
		
		melhor = melhorConfiguracao();
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
		
		if (eleito1.parcimonia <= eleito2.parcimonia) {
//			escolhido = eleito1;
			escolhido = eleito1.copia();
		} else {
//			escolhido = eleito2;
			escolhido = eleito2.copia();
		}
		
		return escolhido;
	}
	
	public Configuracao perturbar() throws CloneNotSupportedException {
		Configuracao eleito = new Configuracao();
		eleito = selecao();
		
		int no1 = random.nextInt((eleito.nos.length - 1));
		int no2 = random.nextInt((eleito.nos.length - 1));
		
		int aux = eleito.nos[no1];
		eleito.nos[no1] = eleito.nos[no2];
		eleito.nos[no2] = aux;
		
		return eleito;
	}

}
