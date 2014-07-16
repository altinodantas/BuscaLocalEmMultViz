package teste;

import java.util.Random;

public class combinar {
	public static Random r = new Random(); 
	public static double percentual = 0.3;
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int n = 18-1;
		int rand = r.nextInt(n);
		int razao = (int) (n * percentual);
		int limiteSuperior, limiteInferior;
		
		if(rand <= (n - razao)){
			limiteSuperior = rand + razao;
			limiteInferior = rand;
		}else{
			limiteSuperior = rand;
			limiteInferior = rand - razao;
		}

		System.out.println("limete S = "+ limiteSuperior + "| Limite I = "+limiteInferior);
	}

}
