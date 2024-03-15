package java_folder.projeto_joguinho;

import java.util.Scanner;

public class Jogo {

   private Tabuleiro Tabuleiro;
   private final int tamanho;
   private final String movimentosPossiveis = "UDLR";

   public static void main(String[] args) {
      Jogo jogo = new Jogo(2);
      while(!jogo.Tabuleiro.estadoVitoria()){
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
      String inputUsuario = scanner.nextLine().toUpperCase();
      System.out.println("Seu movimento: " + inputUsuario);
     
      if (!this.movimentosPossiveis.contains(inputUsuario)) {
          System.out.println("Esse movimento não é suportado");
          return "";
      }
      return inputUsuario;
   }

   public void jogar(){
      this.Tabuleiro.printTabuleiro();
      System.out.println("Posicao do whitespace " + this.Tabuleiro.posicaoWhiteSpace[0] + this.Tabuleiro.posicaoWhiteSpace[1] + "\n");

      String movimento = this.inputUsuario();
      if (movimento.equals(""))
          return;
      executaComandos(movimento);
   }

   private void executaComandos(final String movimento){
      int linhaWhitespace = this.Tabuleiro.posicaoWhiteSpace[0];
      int colunaWhitespace = this.Tabuleiro.posicaoWhiteSpace[1];
      

       switch (movimento) {
         case "U":
            if( linhaWhitespace == 0 || (!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace-1,colunaWhitespace))){
               System.out.println("Não é possivel subir");
               return;
            }          
            
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
