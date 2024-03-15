package java_folder.dado;
import java.util.Random;

public class Dado {

   public final int numeroLados;
   public int ultimoResultado;

   public static void main(String[] args) {
      Dado dado = new Dado(6);
      int resultado = dado.jogarDado();
      System.out.println("O resultado do dado foi: " + resultado);
   }

   Dado(final int numeroLados){
       this.numeroLados = numeroLados;
   }

   public int jogarDado(){
      Random rand = new Random();

      int resultado = rand.nextInt(this.numeroLados); //se o dado tiver 6 lados vai gerar um int entre 0 e 5
      resultado++; //vamos add 1 para que os resultados estejam entre 1 e num_lados

      this.ultimoResultado = resultado;
      return resultado;
   }
}
