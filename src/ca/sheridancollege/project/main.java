package ca.sheridancollege.project;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to Go Fish!");

        Scanner scanner = new Scanner(System.in);
        int numPlayers;

        do {
            System.out.print("Enter the number of players (2-4): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        } while (numPlayers < 2 || numPlayers > 4);

        Game game = new Game(numPlayers);
        game.startGame();
    }
}
