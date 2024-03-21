
import java.util.Scanner;

public class Jogo {

   private Tabuleiro tabulei;
   static private final String movimentosPossiveis = "UDLR"; //variavel estatica de classe para os comandos permitidos no jogo
   private static Scanner scan; //variavel estatica para o scanner do input do usuário

   public static void main(String[] args) {
      Scanner scan2 = new Scanner(System.in);
      String linhaNum = scan2.nextLine();
      String[] partesLinhaNum = linhaNum.split(" ");
        
      int[] numeros = new int[partesLinhaNum.length];
        
      for (int i = 0; i < partesLinhaNum.length; i++) {
            numeros[i] = Integer.parseInt(partesLinhaNum[i]);
      }

      String linhaComandos = scan2.nextLine();

      //System.out.println(linhaComandos);
      int tamTabu = (int) Math.sqrt(numeros.length);

      Jogo jogo = new Jogo(tamTabu, numeros);
      jogo.tabulei.printTabuleiro();
      System.out.println();

      if (jogo.testa_movimentos(linhaComandos))
          System.out.println("Posicao final: true");
      else
          System.out.println("Posicao final: false");
      
      
      scan2.close();
      
      /*Jogo jogo = new Jogo(2); //construtor apenas com tamanho do tabuleiro
     
      while(!jogo.Tabuleiro.estadoVitoria()){  //joga o jogo até o estado da vitória
         jogo.jogar();
      }
      jogo.Tabuleiro.printTabuleiro();
      System.out.println("Parabens, você venceu !!"); //msg de vitória
      scan.close(); //fecha o scanner*/
   }

  
   Jogo(final int tamanho){ //construtor onde apenas o tamanho é dado
       this.tabulei = new Tabuleiro(tamanho);
       scan = new Scanner(System.in);
   }

   Jogo (final int tamanho, final int[] numeros){
      this.tabulei = new Tabuleiro(tamanho, numeros);
   }

   private String inputUsuario(){
      System.out.print("Proximo movimento U/D/L/R: ");
      String inputUsuario = scan.nextLine().toUpperCase(); //pega input do user e coloca ele como uppercase
      System.out.println("Seu movimento: " + inputUsuario);
     
      if (!Jogo.movimentosPossiveis.contains(inputUsuario)) { //caso o movimento nao esteja entre os permitidos
          System.out.println("Esse movimento não é suportado");
          return "";
      }
      return inputUsuario;
   }

   public void jogar(){
      this.tabulei.printTabuleiro(); //printa o tabuleiro na tela

      String movimento = this.inputUsuario(); //pega input do usuario
      if (movimento.equals("")) //input invalido
          return;
      executaComandos(movimento);  //executa o comando do user
   }

   public boolean testa_movimentos(String movimentos){
      
      for (int i = 0; i < movimentos.length(); i++) {
          String movimento = String.valueOf(movimentos.charAt(i)).toUpperCase();
         // System.out.println("Movimento: "+ movimento+ " ");
          this.executaComandos(movimento);
          this.tabulei.printTabuleiro();
          System.out.println();
      }

      if (this.tabulei.estadoVitoria())
         return true;
      else
         return false;
   }

   private void executaComandos(final String movimento){
      int linhaWhitespace = this.tabulei.posicaoWhiteSpace[0]; //posicao do whiteSpace
      int colunaWhitespace = this.tabulei.posicaoWhiteSpace[1];
     
      switch (movimento) { //switch no movimento
        
         case "U": 

            if((!this.tabulei.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace+1,colunaWhitespace))){
              // System.out.println("Não é possivel subir");
               return;
            }  //verifica se o movimento é possivel (ex: não pode subir se estiver no topo do tabulei) e se a mudanca de posicoes teve sucesso        
            
            break;
         case "D":
            
            if((!this.tabulei.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace-1,colunaWhitespace))){
              // System.out.println("Não é possivel descer");
               return;
            }   
            break;
         case "L":
      
            if((!this.tabulei.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace, colunaWhitespace+1))){
              // System.out.println("Não é possivel ir para esquerda");
               return;
            }   
            break;
         case "R":
            
           
            if((!this.tabulei.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace,colunaWhitespace-1))){
              // System.out.println("Não é possivel ir para direita");
               return;
            }  
            break;
         default:
            //System.out.println("Movimento nao valido");
            break;
       }

   }
 
}
