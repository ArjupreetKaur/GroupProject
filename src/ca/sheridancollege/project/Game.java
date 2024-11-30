package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private GroupOfCards deck;
    private List<Player> players;

    public Game(int numPlayers) {
        deck = new GroupOfCards();
        deck.shuffle();
        players = new ArrayList<>();

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        // Deal 5 cards to each player
        for (Player player : players) {
            for (int j = 0; j < 5; j++) {
                player.addCardToHand(deck.drawCard());
            }
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOngoing = true;

        while (gameOngoing) {
            for (Player currentPlayer : players) {
                if (isGameOver()) {
                    declareWinner();
                    gameOngoing = false;
                    break;
                }

                System.out.println("\n" + currentPlayer.getName() + "'s turn:");
                System.out.println("Your hand: " + currentPlayer.getHand());
                System.out.print("Ask for a rank: ");
                String rank = scanner.nextLine().trim();

                if (!isValidRank(rank)) {
                    System.out.println("Invalid rank. Try again.");
                    continue;
                }

                boolean foundCard = false;
                for (Player opponent : players) {
                    if (!opponent.equals(currentPlayer) && opponent.hasCardOfRank(rank)) {
                        List<Card> givenCards = opponent.giveCardsOfRank(rank);
                        currentPlayer.getHand().addAll(givenCards);
                        System.out.println(opponent.getName() + " gave " + givenCards.size() + " cards to " + currentPlayer.getName());
                        foundCard = true;
                    }
                }

                if (!foundCard) {
                    System.out.println("Go Fish!");
                    Card drawnCard = deck.drawCard();
                    if (drawnCard != null) {
                        System.out.println(currentPlayer.getName() + " drew " + drawnCard);
                        currentPlayer.addCardToHand(drawnCard);
                    } else {
                        System.out.println("Deck is empty!");
                    }
                }
            }
        }
        scanner.close();
    }

    private boolean isValidRank(String rank) {
        String[] validRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String validRank : validRanks) {
            if (validRank.equalsIgnoreCase(rank)) {
                return true;
            }
        }
        return false;
    }

    private boolean isGameOver() {
        return deck.isEmpty() || players.stream().allMatch(p -> p.getHand().isEmpty());
    }

    private void declareWinner() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getSets(), p2.getSets())).orElse(null);
        System.out.println("\nGame Over! The winner is " + winner.getName() + " with " + winner.getSets() + " sets!");
    }
}
