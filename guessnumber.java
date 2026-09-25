import java.util.Scanner;

public class guessnumber {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int secretNumber = 7;
        int guess;

        System.out.println("=== Guess the Number Game ===");

        while (true) {
            System.out.print("Enter a number between 1 and 10: ");
            guess = sc.nextInt();

            if (guess < secretNumber) {
                System.out.println("Too Low! Try Again.");
            } 
            else if (guess > secretNumber) {
                System.out.println("Too High! Try Again.");
            } 
            else {
                System.out.println("🎉 You Won! The secret number is 7.");
                break;
            }
        }

        sc.close();
    }
}