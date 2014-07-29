package teste;

import java.util.Random;

import dot.ArquivoDot;

// Processo Evolucionario - Autor: Altino
public class Algoritmo {

	public Configuracao[] configuracoes;
	public Filogenia filogenia;
	public Random random = new Random();
	public boolean Revalidar = false;

	public void execute(Filogenia filogenia, int p, long MAXTEMPO,
			int FatorItera, int HCITER) {
		configuracoes = new Configuracao[p];
		int m = filogenia.m;
		int n = filogenia.n;

		// Geracao da Populacao Inicial
		for (int i = 0; i < p; i++) {
			Configuracao conf = new Configuracao();
			conf.inicializar(m, n);
			// filogenia.avaliar(conf);
			configuracoes[i] = conf;
		}

		int indiceDoPior = piorConfiguracao();
		int HCCOUNT = 0;

		filogenia.upperbound = configuracoes[indiceDoPior].parcimonia;

		long t0 = System.currentTimeMillis();

		for (int i = 0; i < m * FatorItera; i++) {

			indiceDoPior = piorConfiguracao();
			filogenia.upperbound = configuracoes[indiceDoPior].parcimonia;

			if ((System.currentTimeMillis() - t0) > MAXTEMPO) {
				System.out.println("Duração:"
						+ (System.currentTimeMillis() - t0));
				break;
			}

			Random rand = new Random();

			Configuracao confX = new Configuracao();

			// executa % de cruzamento guiado e % de mutação
			if (rand.nextDouble() < 0.01) {

				Configuracao confXY = new Configuracao();
				Configuracao confR = new Configuracao();

				confXY = selecionar();
				confX = selecionar();
				confR = combinar(confXY, confX, 0.35);
				confX.reconstruir();
				// filogenia.avaliar(confX);

			} else {

				Configuracao selecionado = selecionar();
				confX = clone(selecionado);
				Configuracao corrente = new Configuracao();

				for (int j = 0; j < 6; j++) {
					corrente = clone(selecionado);
					perturbar(corrente, rand.nextInt(2) + 1);
					corrente.reconstruir();
					// filogenia.avaliar(corrente);

					if (corrente.parcimonia < confX.parcimonia) {
						confX = corrente;
					}

				}

			}

			if (confX.parcimonia < configuracoes[indiceDoPior].parcimonia) {
				System.out.println(confX.parcimonia);
				configuracoes[indiceDoPior] = confX;
			}

			HCCOUNT++;
			if (HCCOUNT == FatorItera) {
				Configuracao selecionado = selecionar();
				Configuracao melhorCorrente = clone(selecionado);
				Configuracao corrente = new Configuracao();

				for (int j = 0; j < HCITER; j++) {
					corrente = clone(selecionado);
					perturbar(corrente, 1);
					corrente.reconstruir();
					// filogenia.avaliar(corrente);

					if (corrente.parcimonia < melhorCorrente.parcimonia) {
						melhorCorrente = corrente;
						selecionado = clone(melhorCorrente);
					}

				}

				System.out.println("->" + melhorCorrente.parcimonia);

				if (melhorCorrente.parcimonia < configuracoes[indiceDoPior].parcimonia) {
					configuracoes[indiceDoPior] = melhorCorrente;
				}

				HCCOUNT = 0;

			}
		}

		int melhor = melhorConfiguracao();
		System.out.println("MELHOR CONFIGURAÇÃO - FINAL [" + melhor + "]");

		System.out.println(configuracoes[melhor].parcimonia);

		// PROCESSO DE INTENSIFICAÇÃO
		Configuracao otima = configuracoes[melhor];
		Configuracao otimaAux = new Configuracao();
		otimaAux.nos = new int[otima.nos.length];

		for (int i = 0; i < otima.nos.length; i++) {
			otimaAux.nos[i] = otima.nos[i];
		}

		for (int i = 0; i < otimaAux.nos.length - 2; i++) {
			for (int j = 1; j < otimaAux.nos.length - 1; j++) {

				if (otimaAux.nos[i] != otimaAux.nos[j]) {

					this.swapSimples(otimaAux, i, j);

					if (otimaAux.viavel()) {

						otimaAux.reconstruir();
						// filogenia.avaliar(otimaAux);

						if (otimaAux.parcimonia < otima.parcimonia) {
							otima = clone(otimaAux);
							otima.reconstruir();
							// filogenia.avaliar(otima);

						} else {
							this.swapSimples(otimaAux, i, j);
						}
					} else {
						this.swapSimples(otimaAux, j, i);
					}

				}

			}
		}

		System.out.println(otima.parcimonia);
		System.out.println("Duração:" + (System.currentTimeMillis() - t0));

		// call to class that make the image
		ArquivoDot dot = new ArquivoDot();
		dot.Escrever(otima, Filogenia.instancia);

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

	public Configuracao selecionar() {
		Configuracao escolhido = new Configuracao();
		Configuracao eleito1 = configuracoes[random
				.nextInt(configuracoes.length)];
		Configuracao eleito2 = configuracoes[random
				.nextInt(configuracoes.length)];

		escolhido.nos = new int[eleito1.nos.length];

		if (eleito1.parcimonia <= eleito2.parcimonia) {
			for (int i = 0; i < eleito1.nos.length; i++) {
				escolhido.nos[i] = eleito1.nos[i];
			}

			escolhido.parcimonia = eleito1.parcimonia;

		} else {
			for (int i = 0; i < eleito2.nos.length; i++) {
				escolhido.nos[i] = eleito2.nos[i];
			}

			escolhido.parcimonia = eleito1.parcimonia;
		}

		return escolhido;
	}

	public void perturbar(Configuracao eleito, int k) {

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

			// rand only between father's leaf
			no1 = random.nextInt(((eleito.nos.length + 2) / 2) - 1);
			no2 = random.nextInt(((eleito.nos.length + 2) / 2) - 1);

			this.swap(eleito, no1, no2);

		default:
			break;
		}

	}

	// guided crossover
	public Configuracao combinar(Configuracao ConfA, Configuracao ConfB,
			double percentual) {

		Configuracao filho = new Configuracao();
		filho = clone(ConfB);

		int n = ConfA.nos.length - 1;
		int rand = random.nextInt(n);
		int razao = (int) (n * percentual);
		int limiteSuperior, limiteInferior;

		if (rand <= (n - razao)) {
			limiteSuperior = rand + razao;
			limiteInferior = rand;
		} else {
			limiteSuperior = rand;
			limiteInferior = rand - razao;
		}

		for (int i = limiteInferior; i <= limiteSuperior; i++) {

			if (filho.nos[i] != ConfA.nos[i]) {

				for (int j = 0; j < ConfB.nos.length - 1; j++) {
					if (filho.nos[j] == ConfA.nos[i]) {
						this.swapSimples(filho, i, j);
						if (!ConfB.viavel()) {
							this.swapSimples(filho, i, j);
						}
						break;
					}

				}

			}

		}

		return filho;

	}

	public Configuracao clone(Configuracao C) {
		Configuracao escolhido = new Configuracao();

		escolhido.nos = new int[C.nos.length];
		for (int i = 0; i < C.nos.length; i++) {
			escolhido.nos[i] = C.nos[i];
		}

		escolhido.parcimonia = C.parcimonia;
		return escolhido;

	}

	// faz o swap de duas posições no vetor NOS garantindo a viabilidade;
	public void swap(Configuracao c, int i, int j) {
		int aux;

		aux = c.nos[i];
		c.nos[i] = c.nos[j];
		c.nos[j] = aux;

		if (!c.viavel()) {
			this.Revalidar = true;
			this.swap(c, j, i);
		} else {
			if (this.Revalidar) {
				this.Revalidar = false;
				int n1 = random.nextInt((c.nos.length - 1));
				int n2 = random.nextInt((c.nos.length - 1));
				this.swap(c, n1, n2);
			}

		}
	}

	// executa o swap entre dois valores do vetor NOS sem garantir validade
	public void swapSimples(Configuracao c, int i, int j) {
		int aux;

		aux = c.nos[i];
		c.nos[i] = c.nos[j];
		c.nos[j] = aux;
	}

}
