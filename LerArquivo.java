package teste;

import java.io.*;  

public class LerArquivo{  
   public static void main (String[]args) throws IOException{  
       
        
         BufferedReader br = new BufferedReader(new FileReader("C:/Users/altino/workspace/BuscaLocal/src/teste/arquivo.txt"));  
  
         while(br.ready()){  
            String linha = br.readLine();
            char[] testes = linha.toCharArray();  
            
            for (int i = 0; i < testes.length; i++) {
            	System.out.print(testes[i]);  				
			}
            System.out.println();
         }  
         br.close();  
      
   }  
}  