package java_folder.projeto_joguinho;

import java.util.Collections;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tabuleiro {

    static final int WHITESPACE = -1; 

    public int[][] tabuleiro;
    public final int tamanho;
    public  int[] posicaoWhiteSpace;
    private List<Integer> numeros;

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(4);
        tabuleiro.encheTabuleiro();

        tabuleiro.printTabuleiro();

        tabuleiro.trocaPosicoes(0, 0, 1, 0);

        tabuleiro.printTabuleiro();

        tabuleiro.estadoVitoria();
    }


    Tabuleiro(final int tamanho){
          this.tamanho = tamanho;
          this.tabuleiro = new int[tamanho][tamanho];
    }

    public boolean trocaPosicoes(final int i1, final int j1 , final int i2 ,  final int j2){
        if (i1> tamanho -1 || j1 > tamanho -1 || i2 > tamanho -1 ||  j2 > tamanho -1 ) //caso a posicao esteja fora do escopo do tabuleiro, indexado por 0
            return false;
        
        if (i1 == i2 && j1 == j2) //caso seja pedido para mover a mesma posicao
            return false;
        
        int valPrimeiraPosi = this.tabuleiro[i1][j1];

        this.tabuleiro[i1][j1] = this.tabuleiro[i2][j2];
        this.tabuleiro[i2][j2] = valPrimeiraPosi;

        return true;
    }

    //verifica se o tabuleiro esta no estado de vitoria do jogo (primeira casa em branco e todas casas subsequentes em ordem)  
    public boolean estadoVitoria(){
        if (this.tabuleiro[0][0] !=  WHITESPACE) //whitespace precisa estar no comeco
           return false;

        int numero_ordem = 1; //vamos contar os numeros e ver se eles estao em ordem
      
        for (int i = 0; i < this.tamanho; i++){            
           for (int j = 0; j < this.tamanho; j++){
                if (i ==0 && j == 0)  //pular o primeiro valor que deve ser whitespace
                    continue;
            
                if (this.tabuleiro[i][j] != numero_ordem)
                   return false;
                numero_ordem ++;
           }
        }
           
        return true;
    }

    public void printTabuleiro(){
        for (int i = 0; i < this.tamanho; i++) {
            System.out.print("+---");
        }
        System.out.println("+");
    
        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                System.out.print("|");
                int val = this.tabuleiro[i][j];
                if (val ==  WHITESPACE)
                    System.out.print("   "); 
                else
                    System.out.printf("%3d", val); 
            }
            
            System.out.println("|");
    
          
            for (int j = 0; j < this.tamanho; j++) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }

    private void encheTabuleiro(){
        
        final int tamanho_lista = this.tamanho * this.tamanho;
        List<Integer> numeros = IntStream.rangeClosed(1, tamanho_lista).boxed().collect(Collectors.toList());
        
        Collections.shuffle(numeros);
        this.numeros = numeros;

        Random rand = new Random();
        int index_remover = rand.nextInt(tamanho_lista);
          
        System.out.println(numeros);
        numeros.remove(index_remover);

        int index_atual = 0; //guarda o index da lista que vamos acessar
        loop_externo:
        for (int i = 0 ; i < this.tamanho; i++) {
            for (int j = 0 ; j < this.tamanho; j++) {
                if (index_atual == index_remover){ //se o index atual for o index do numero que foi removido
                     this.tabuleiro[i][j] = WHITESPACE; //seta ele como -1 para identificacao de whitespace
                     this.posicaoWhiteSpace = new int[]{i, j};
                     index_atual += 1;
                     continue;
                }
                if (index_atual >= tamanho_lista -1) //caso estivermos no ultimo indice
                    break loop_externo;
                this.tabuleiro[i][j] = numeros.get(index_atual);
                index_atual +=1;
            }
        }
    }


}
