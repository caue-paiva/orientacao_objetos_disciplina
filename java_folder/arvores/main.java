import java.util.Scanner;

public class main {

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int len = 5000;
        AVL avl = new AVL(len);
        ArvoreBin ab = new ArvoreBin(len);
        ABB abb = new ABB(len);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.isEmpty())
                break;
            String command = String.valueOf(input.charAt(0));
            String restOfString = input.substring(2);

            switch (command) {
                case "i":
                    avl.Insert(restOfString);
                    abb.Insert(restOfString);
                    ab.Insert(restOfString);
                    break;
                case "d":
            
                    avl.Remove(restOfString);
                    abb.Remove(restOfString);
                    ab.Remove(restOfString);
                default:
                    break;
            }
        }
        System.out.println(ab.toString()); //ordem correta do runcodes
        System.out.println();
        System.out.println(abb.toString());
        System.out.println();
        System.out.println(avl.toString());
        System.out.println();
        scanner.close();
    }
    
   }

