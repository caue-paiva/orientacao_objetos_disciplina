package java_folder.projeto_joguinho;

import java.util.Scanner;

public class Jogo {

   private Tabuleiro Tabuleiro;
   private final int tamanho;
   static private final String movimentosPossiveis = "UDLR"; //variavel estatica de classe para os comandos permitidos no jogo

   public static void main(String[] args) {
      Jogo jogo = new Jogo(2);
      while(!jogo.Tabuleiro.estadoVitoria()){ //loop ate que o estado de vitoria seja atingido
         jogo.jogar();
      }
      System.out.println("Parabens, você venceu !!");
  
   }
  
   Jogo(final int tamanho){
       this.tamanho = tamanho;
       this.Tabuleiro = new Tabuleiro(tamanho);
   }

   private String inputUsuario(){
      Scanner scanner = new Scanner(System.in);
      System.out.print("Proximo movimento U/D/L/R: ");
      String inputUsuario = scanner.nextLine().toUpperCase(); //pega input do user e coloca ele como uppercase
      System.out.println("Seu movimento: " + inputUsuario);
     
      if (!Jogo.movimentosPossiveis.contains(inputUsuario)) { //caso o movimento nao esteja entre os permitidos
          System.out.println("Esse movimento não é suportado");
          return "";
      }
      return inputUsuario;
   }

   public void jogar(){
      this.Tabuleiro.printTabuleiro(); //printa o tabuleiro na tela
     // System.out.println("Posicao do whitespace " + this.Tabuleiro.posicaoWhiteSpace[0] + this.Tabuleiro.posicaoWhiteSpace[1] + "\n");

      String movimento = this.inputUsuario(); //pega input do usuario
      if (movimento.equals("")) //input invalido
          return;
      executaComandos(movimento);  //executa o comando do user
   }

   private void executaComandos(final String movimento){
      int linhaWhitespace = this.Tabuleiro.posicaoWhiteSpace[0]; //posicao do whiteSpace
      int colunaWhitespace = this.Tabuleiro.posicaoWhiteSpace[1];
      

       switch (movimento) { //switch no movimento
         case "U": 
            if( linhaWhitespace == 0 || (!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace-1,colunaWhitespace))){
               System.out.println("Não é possivel subir");
               return;
            }  //verifica se o movimento é possivel (ex: não pode subir se estiver no topo do tabuleiro) e se a mudanca de posicoes teve sucesso        
            
            break;
         case "D":
            if(linhaWhitespace == this.tamanho-1 ||(!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace+1,colunaWhitespace))){
               System.out.println("Não é possivel descer");
               return;
            }   
            break;
         case "L":
            if(colunaWhitespace == 0 || (!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace, colunaWhitespace-1))){
               System.out.println("Não é possivel ir para esquerda");
               return;
            }   
            break;
         case "R":
            if(colunaWhitespace == this.tamanho-1 || (!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace,colunaWhitespace+1))){
               System.out.println("Não é possivel ir para direita");
               return;
            }  
            break;
      
         default:
            System.out.println("Movimento nao valido");
            break;
       }

   }
 
}
