package teste;

import java.util.Random;

import dot.ArquivoDot;

// Processo Evolucionario - Autor: Altino
public class AlgoritmoB {

	public Configuracao[] configuracoes;
	public Filogenia filogenia;
	public Random random = new Random();
	public boolean Revalidar = false;

	public void execute(Filogenia filogenia, int p, long MAXTEMPO,
			int FatorItera) throws CloneNotSupportedException {
		configuracoes = new Configuracao[p];
		int m = filogenia.m;
		int n = filogenia.n;

		// Geracao da Populacao Inicial
		for (int i = 0; i < p; i++) {
			Configuracao conf = new Configuracao();
			conf.inicializar(m, n);
			filogenia.avaliar(conf);
			configuracoes[i] = conf;
		}

		long t0 = System.currentTimeMillis();
		long t1 = System.currentTimeMillis();

		Configuracao solucao = new Configuracao();
		solucao = configuracoes[melhorConfiguracao()];

		for (int i = 0; i < configuracoes.length - 1; i++) {

			for (int j = 1; j < configuracoes.length; j++) {

				if ((System.currentTimeMillis() - t0) > MAXTEMPO) {
					System.out.println("Duração:"
							+ (System.currentTimeMillis() - t0));
					break;
				}

				Configuracao conf1 = new Configuracao();
				Configuracao conf2 = new Configuracao();
				Configuracao selecionado = new Configuracao();

				conf1 = clone(configuracoes[i]);
				conf2 = clone(configuracoes[j]);
				selecionado = combinar(conf1, conf2, 0.35);
				selecionado.reconstruir();
				filogenia.avaliar(selecionado);

				Configuracao melhorCorrente = clone(selecionado);
				Configuracao corrente = new Configuracao();

				for (int j1 = 0; j1 < 1000; j1++) {
					corrente = clone(selecionado);
					corrente = perturbar(corrente, 1);
					corrente.reconstruir();
					filogenia.avaliar(corrente);

					if (corrente.parcimonia < melhorCorrente.parcimonia) {
						melhorCorrente = corrente;
					}

				}

				if (melhorCorrente.parcimonia < solucao.parcimonia) {
					solucao = melhorCorrente;
					System.out.println(solucao.parcimonia);
				}

			}

		}
	
		// PROCESSO DE INTENSIFICAÇÃO
		
		Configuracao otimaAux = new Configuracao();
		otimaAux.nos = new int[solucao.nos.length];

		for (int i = 0; i < solucao.nos.length; i++) {
			otimaAux.nos[i] = solucao.nos[i];
		}

		for (int i = 0; i < otimaAux.nos.length - 2; i++) {
			for (int j = 1; j < otimaAux.nos.length - 1; j++) {
				if (otimaAux.nos[i] != otimaAux.nos[j]) {

					this.swapSimples(otimaAux, i, j);
					if (otimaAux.viavel()) {

						otimaAux.reconstruir();
						filogenia.avaliar(otimaAux);

						if (otimaAux.parcimonia < solucao.parcimonia) {
							solucao = otimaAux;
						} else {
							this.swapSimples(otimaAux, i, j);
						}
					} else {
						this.swapSimples(otimaAux, i, j);
					}
				}

			}
		}

		System.out.println(solucao.parcimonia);
		System.out.println("Duração:" + (System.currentTimeMillis() - t0));

		for (int i = 0; i < configuracoes.length; i++) {
			for (int j = 0; j < configuracoes[0].nos.length; j++) {
				System.out.print(configuracoes[i].nos[j] + " ");
			}
			System.out.println();
		}

		// call to class that make the image
		ArquivoDot dot = new ArquivoDot();
		dot.Escrever(solucao, Filogenia.instancia);

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
		Configuracao eleito1 = configuracoes[random
				.nextInt(configuracoes.length)];
		Configuracao eleito2 = configuracoes[random
				.nextInt(configuracoes.length)];

		escolhido.nos = new int[eleito1.nos.length];

		if (eleito1.parcimonia <= eleito2.parcimonia) {
			// escolhido = eleito1.copia();
			for (int i = 0; i < eleito1.nos.length; i++) {
				escolhido.nos[i] = eleito1.nos[i];
			}

			escolhido.parcimonia = eleito1.parcimonia;

		} else {
			for (int i = 0; i < eleito2.nos.length; i++) {
				escolhido.nos[i] = eleito2.nos[i];
			}

			escolhido.parcimonia = eleito1.parcimonia;
			// escolhido = eleito2.copia();
		}

		return escolhido;
	}

	public Configuracao perturbar(Configuracao eleito, int k)
			throws CloneNotSupportedException {

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

			// rand only between folhas
			no1 = random.nextInt(((eleito.nos.length + 2) / 2) - 1);
			no2 = random.nextInt(((eleito.nos.length + 2) / 2) - 1);

			this.swap(eleito, no1, no2);

		default:
			break;
		}

		return eleito;
	}

	// Método de cruzamento guiado de características
	public Configuracao combinar(Configuracao ConfA, Configuracao ConfB,
			double percentual) {

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

			if (ConfB.nos[i] != ConfA.nos[i]) {

				for (int j = 0; j < ConfB.nos.length - 1; j++) {
					if (ConfB.nos[j] == ConfA.nos[i]) {
						this.swapSimples(ConfB, i, j);
						if (!ConfB.viavel()) {
							this.swapSimples(ConfB, i, j);
						}
						break;
					}

				}

			}

		}

		return ConfB;

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
