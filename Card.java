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

    /**
     * To set the territory name of the card.
     * @param territoryName
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * To set the card type.
     * @param cardType
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
