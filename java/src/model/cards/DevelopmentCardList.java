package model.cards;

/**
 * Contains the amount of development cards a player/bank has.
 * @author Scott Hampton
 */
public class DevelopmentCardList
{
    /**
     * Amount of monopoly cards
     */
    private int monopoly;

    /**
     * Amount of monument cards
     */
    private int monument;

    /**
     * Amount of roadBuilding cards
     */
    private int roadBuilding;

    /**
     * Amount of soldier cards
     */
    private int soldier;

    /**
     * Amount of yearOfPlenty cards
     */
    private int yearOfPlenty;

    /**
     * Constructor for DevelopmentCardList
     * @param monopoly
     * @param monument
     * @param roadBuilding
     * @param soldier
     * @param yearOfPlenty
     */
    public DevelopmentCardList(monopoly, monument, roadBuilding, soldier, yearOfPlenty)
    {
        this.monopoly = monopoly;
        this.monument = monument;
        this.roadBuilding = roadBuilding;
        this.soldier = soldier;
        this.yearOfPlenty = yearOfPlenty;
    }

    /**
     * Obvious
     * @return monopoly
     */
    public int getMonopoly() {
        return monopoly;
    }

    /**
     * Obvious
     * @param monopoly
     */
    public void setMonopoly(int monopoly) {
        this.monopoly = monopoly;
    }

    /**
     * Obvious
     * @return monument
     */
    public int getMonument() {
        return monument;
    }

    /**
     * Obvious
     * @param monument
     */
    public void setMonument(int monument) {
        this.monument = monument;
    }

    /**
     * Obvious
     * @return roadBuilding
     */
    public int getRoadBuilding() {
        return roadBuilding;
    }

    /**
     * Obvious
     * @param roadBuilding
     */
    public void setRoadBuilding(int roadBuilding) {
        this.roadBuilding = roadBuilding;
    }

    /**
     * Obvious
     * @return soldier
     */
    public int getSoldier() {
        return soldier;
    }

    /**
     * Obvious
     * @param soldier
     */
    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }

    /**
     * Obvious
     * @return yearOfPlenty
     */
    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    /**
     * Obvious
     * @param yearOfPlenty
     */
    public void setYearOfPlenty(int yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
    }
}