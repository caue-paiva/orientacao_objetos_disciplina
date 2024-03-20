package java_folder.projeto_joguinho;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tabuleiro {

    static final int WHITESPACE = -1; 

    public int[][] tabuleiro;
    public final int tamanho;
    public  int[] posicaoWhiteSpace; //index 0 = linha do whitespace, index 1 = coluna do whitespace

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(3, new int[]{11,8,0,1,2,6,4,5,9,7,8});
        tabuleiro.printTabuleiro();
    
    }
    //constructor onde o usar passar apenas o tamanho do tabuleiro
    Tabuleiro(final int tamanho){
          this.tamanho = tamanho;
          this.tabuleiro = new int[tamanho][tamanho];
          this.encheTabuleiro();
    }

    //constructor onde o usar passa o array de elementos
    Tabuleiro(final int tamanho, int[] numeros)  {
        if (numeros.length != tamanho * tamanho) {
            System.out.println("Tamanho do vetor numeros tem que ser igual ao tamanho ao quadrado");
            System.exit(0);
        }
        boolean whiteSpaceExiste = Arrays.stream(numeros).anyMatch(num -> num == WHITESPACE);
        if (!whiteSpaceExiste){
            System.out.println("Um dos elementos do vetor numeros deve ser -1, representando whitespace");
            System.exit(0);
        }
        
        this.tamanho = tamanho;
        this.tabuleiro = new int[tamanho][tamanho];
        int index_atual = 0; 
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++){
                this.tabuleiro[i][j] = numeros[index_atual];
                index_atual++;
            }
        }
    }
  
    public boolean trocaPosicoes(final int i1, final int j1 , final int i2 ,  final int j2){
        if (i1> tamanho -1 || j1 > tamanho -1 || i2 > tamanho -1 ||  j2 > tamanho -1 ) //caso a posicao esteja fora do escopo do tabuleiro, indexado por 0
            return false;
        if (i1 < 0|| j1  < 0 || i2 < 0|| j2   < 0 ) //caso a posicao esteja fora do escopo do tabuleiro, indexado por 0
            return false;
        
        if (i1 == i2 && j1 == j2) //caso seja pedido para mover a mesma posicao
            return false;
        
        int valPrimeiraPosi = this.tabuleiro[i1][j1]; //troca posicoes

        this.tabuleiro[i1][j1] = this.tabuleiro[i2][j2];
        this.tabuleiro[i2][j2] = valPrimeiraPosi;


        if(i1 == this.posicaoWhiteSpace[0] && j1 == this.posicaoWhiteSpace[1]){ //caso uma das casa modificados forem a posicao do whitespace, vamos mudar ela
            this.posicaoWhiteSpace[0] = i2;
            this.posicaoWhiteSpace[1] = j2;
        }else if(i2 == this.posicaoWhiteSpace[0] && j2 == this.posicaoWhiteSpace[1]){
            this.posicaoWhiteSpace[0] = i1;
            this.posicaoWhiteSpace[1] = j1;
        }

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
        
        final int tamanho_lista = this.tamanho * this.tamanho; //numero de casos no tabuleiro
        List<Integer> numeros = IntStream.rangeClosed(0, tamanho_lista-1).boxed().collect(Collectors.toList()); //gera uma lista de numeros com as casas do tabuleiro, de 0 ate num_casas -1
        
        Collections.shuffle(numeros); //embaralha essa lista

        int index_atual = 0; //guarda o index da lista que vamos acessar
        for (int i = 0 ; i < this.tamanho; i++) {
            for (int j = 0 ; j < this.tamanho; j++) {
                int numero_add = numeros.get(index_atual); //numero que vamos add
                
                if (numero_add == 0){ //o zero nao vai aparecer no tabuleiro e no seu lugar vai ter o whitespace
                     this.tabuleiro[i][j] = WHITESPACE; //seta ele como -1 para identificacao de whitespace
                     this.posicaoWhiteSpace = new int[]{i, j}; //guarda a posicao do whitespace
                     index_atual += 1;
                     continue;
                }
                this.tabuleiro[i][j] = numeros.get(index_atual); //coloca o valor atual no tabuleiro
                index_atual +=1;     //incrementa o index, para acessar o proximo num      
            }
        }
   }

}
