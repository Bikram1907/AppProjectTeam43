package sample.model;

/**
 * This class is a card class that contains details about the card.
 * @author Team43
 */
public class Card {

    private String territoryName;
    private String cardType;

    /**
     * Constructor with empty parameters.
     * @param territoryName
     * @param cardType
     */
    public Card(String territoryName, String cardType) {
        this.territoryName = territoryName;
        this.cardType = cardType;
    }

}
