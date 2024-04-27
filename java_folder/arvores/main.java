import java.util.Scanner;

public class main {

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int len = 1000;
        AVL avl = new AVL(len); //declara todas as arvores
        ArvoreBin ab = new ArvoreBin(len);
        ABB abb = new ABB(len);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine(); //le linha de input
            if (input.isEmpty())
                break;
            String command = String.valueOf(input.charAt(0)); //pega comando
            String restOfString = input.substring(2);

            switch (command) {
                case "i": //caso de inserir
                    avl.Insert(restOfString);
                    abb.Insert(restOfString);
                    ab.Insert(restOfString);
                    break;
                case "d": //caso de remover
                    avl.Remove(restOfString);
                    abb.Remove(restOfString);
                    ab.Remove(restOfString);
                default:
                    break;
            }
        }
        System.out.println(ab.toString()); //print na ordem correta do runcodes
        System.out.println();
        System.out.println(abb.toString());
        System.out.println();
        System.out.println(avl.toString());
        System.out.println();
        scanner.close();
    }
    
   }

