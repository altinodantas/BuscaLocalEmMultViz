package teste;

import java.util.Random;

// Processo Evolucionario - Autor: Altino
public class Algoritmo {
	
	public Configuracao[] configuracoes;
	public Filogenia filogenia;
	public Random random = new Random();

	public void execute(int m, int n, int p) {
		configuracoes = new Configuracao[p];
		filogenia = new Filogenia();
		
		// Geracao da Populacao Inicial
		for (int i = 0; i < p; i++) {
			Configuracao conf = new Configuracao();
			conf.inicializar(m,n);
			filogenia.avaliar(conf);
			configuracoes[i] = conf;
		}
		
		Configuracao confX;
		
		do {
			confX = perturbar();
		} while (!confX.viavel());
		
		confX.reconstruir();
		filogenia.avaliar(confX);
		System.out.println("Parcimonia Reconstruido: " + confX.parcimonia);
	}
	
	public int piorConfiguracao() {
		int menor = this.configuracoes[0].parcimonia;
		int indice = 0;
		
		for (int i = 1; i < configuracoes.length; i++) {
			if (configuracoes[i].parcimonia < menor) {
				menor = configuracoes[i].parcimonia;
				indice = i;
			}
		}
		
		return indice;
	}
	
	public Configuracao selecao() {
		Configuracao escolhido = new Configuracao();
		Configuracao eleito1 = configuracoes[random.nextInt(configuracoes.length)];
		Configuracao eleito2 = configuracoes[random.nextInt(configuracoes.length)];
		
		if (eleito1.parcimonia <= eleito2.parcimonia) {
			escolhido = eleito1;
		} else {
			escolhido = eleito2;
		}
		
		return escolhido;
	}
	
	public Configuracao perturbar() {
		Configuracao eleito = selecao();
		System.out.println("Parcimonia Reconstruido: " + eleito.parcimonia);
		
		int no1 = random.nextInt((eleito.nos.length - 1));
		int no2 = random.nextInt((eleito.nos.length - 1));
		
		int aux = eleito.nos[no1];
		eleito.nos[no1] = eleito.nos[no2];
		eleito.nos[no2] = aux;
		
		return eleito;
	}

}
