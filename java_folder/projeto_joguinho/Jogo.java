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
     
      int novaColuna;
      int novaLinha;
      switch (movimento) { //switch no movimento
        
         case "U": 
            novaLinha = linhaWhitespace-1;
            if (novaLinha < 0)
                novaLinha = this.tamanho -1;//caso formas para cima na primeira linha, vamos para a ultima

            if((!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,novaLinha,colunaWhitespace))){
               System.out.println("Não é possivel subir");
               return;
            }  //verifica se o movimento é possivel (ex: não pode subir se estiver no topo do tabuleiro) e se a mudanca de posicoes teve sucesso        
            
            break;
         case "D":
            novaLinha= (linhaWhitespace+1)%this.tamanho; //caso o comando for pra descer na ultima linha, vamos pra linha 0
            
            if((!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,novaLinha,colunaWhitespace))){
               System.out.println("Não é possivel descer");
               return;
            }   
            break;
         case "L":
            novaColuna = colunaWhitespace-1;
            if (novaColuna < 0)
                novaColuna = this.tamanho-1; //caso formos para esquerda na primeira coluna, vamos para a ultima coluna

            if((!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace, novaColuna))){
               System.out.println("Não é possivel ir para esquerda");
               return;
            }   
            break;
         case "R":
            novaColuna = (colunaWhitespace+1)%this.tamanho; //caso o comando for pra ir pra direita na ultima coluna, vamos pra coluna 0
           
            if((!this.Tabuleiro.trocaPosicoes(linhaWhitespace,colunaWhitespace,linhaWhitespace,novaColuna))){
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
