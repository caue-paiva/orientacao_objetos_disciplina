package java_folder.arvores;
import java.util.Scanner;

public class main {

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your lines. Type 'exit' to finish.");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
   
            String[] lines = input.split("(?<=\\bi )");
            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] words = line.split("\\s+");
                    if (words.length > 1) {
                        char firstChar = line.charAt(0);
                        String secondString = words[1];

                        System.out.println("First character: " + firstChar);
                        System.out.println("Second string: " + secondString);
                    }
                }

      }
    }
     scanner.close();
   }

}
