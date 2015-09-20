/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class PlayerJSONResponse
{

    private CatanColor color;
    private String name;
    private int id;

    /**
     *
     * @param color The color of this player
     * @param name The name of this player
     * @param id The id of this player
     */
    public PlayerJSONResponse(CatanColor color, String name, int id)
    {
        this.color = color;
        this.name = name;
        this.id = id;
    }
    
    /**
     *
     * @return The color of this player
     */
    public CatanColor getColor()
    {
        return color;
    }

    /**
     *
     * @return The name of this player
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return The id of this player
     */ 
    public int getId()
    {
        return id;
    }

    /**
     *
     * @return
     */
    public boolean isEmptyPlayer()
    {
        throw new UnsupportedOperationException();
    }
}
