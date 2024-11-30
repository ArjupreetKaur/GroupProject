package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private List<Card> hand;
    private int sets;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.sets = 0;
    }

    public void addCardToHand(Card card) {
        if (card != null) {
            hand.add(card);
            checkAndRemoveSets();
        }
    }

    public boolean hasCardOfRank(String rank) {
        for (Card card : hand) {
            if (card.getRank().equalsIgnoreCase(rank)) {
                return true;
            }
        }
        return false;
    }

    public List<Card> giveCardsOfRank(String rank) {
        List<Card> cardsToGive = new ArrayList<>();
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (hand.get(i).getRank().equalsIgnoreCase(rank)) {
                cardsToGive.add(hand.remove(i));
            }
        }
        return cardsToGive;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public void checkAndRemoveSets() {
        Map<String, Integer> rankCount = new HashMap<>();
        for (Card card : hand) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 4) {
                sets++;
                System.out.println(name + " completed a set of " + entry.getKey() + "s!");
                hand.removeIf(card -> card.getRank().equalsIgnoreCase(entry.getKey()));
            }
        }
    }
}
