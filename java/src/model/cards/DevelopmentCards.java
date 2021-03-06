package model.cards;

import java.io.Serializable;

/**
 * Contains the amount of development cards a player/bank has.
 * @author Scott Hampton
 */
@SuppressWarnings("serial")
public class DevelopmentCards implements Serializable
{
    /**
     * Amount of monopoly cards
     */
    private int monopoly = 0;

    /**
     * Amount of monument cards
     */
    private int monument = 0;

    /**
     * Amount of roadBuilding cards
     */
    private int roadBuilding = 0;

    /**
     * Amount of soldier cards
     */
    private int soldier = 0;

    /**
     * Amount of yearOfPlenty cards
     */
    private int yearOfPlenty = 0;

    /**
     * Constructor for DevelopmentCards
     * @param monopoly
     * @param monument
     * @param roadBuilding
     * @param soldier
     * @param yearOfPlenty
     */
    public DevelopmentCards(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty)
    {
        this.monopoly = monopoly;
        this.monument = monument;
        this.roadBuilding = roadBuilding;
        this.soldier = soldier;
        this.yearOfPlenty = yearOfPlenty;
    }

    public DevelopmentCards()
    {}

    public int getTotal()
    {
        return this.monopoly + this.monument + this.roadBuilding + this.soldier + this.yearOfPlenty;
    }
    
    /**
     * Obvious
     * @return monopoly
     */
    public int getMonopoly()
    {
        return monopoly;
    }

    /**
     * Obvious
     * @param monopoly
     */
    public void setMonopoly(int monopoly)
    {
        this.monopoly = monopoly;
    }

    /**
     * Obvious
     * @return monument
     */
    public int getMonument()
    {
        return monument;
    }

    /**
     * Obvious
     * @param monument
     */
    public void setMonument(int monument)
    {
        this.monument = monument;
    }

    /**
     * Obvious
     * @return roadBuilding
     */
    public int getRoadBuilding()
    {
        return roadBuilding;
    }

    /**
     * Obvious
     * @param roadBuilding
     */
    public void setRoadBuilding(int roadBuilding)
    {
        this.roadBuilding = roadBuilding;
    }

    /**
     * Obvious
     * @return soldier
     */
    public int getSoldier()
    {
        return soldier;
    }

    /**
     * Obvious
     * @param soldier
     */
    public void setSoldier(int soldier)
    {
        this.soldier = soldier;
    }

    /**
     * Obvious
     * @return yearOfPlenty
     */
    public int getYearOfPlenty()
    {
        return yearOfPlenty;
    }

    /**
     * Obvious
     * @param yearOfPlenty
     */
    public void setYearOfPlenty(int yearOfPlenty)
    {
        this.yearOfPlenty = yearOfPlenty;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("Development Cards:\n");
    	str.append("Monopoly: ");
    	str.append(monopoly);
    	str.append("\nMonument: ");
    	str.append(monument);
    	str.append("\nRoad Building: ");
    	str.append(roadBuilding);
    	str.append("\nSoldier: ");
    	str.append(soldier);
    	str.append("\nYear Of Plenty: ");
    	str.append(yearOfPlenty);
    	return str.toString();
    }
}