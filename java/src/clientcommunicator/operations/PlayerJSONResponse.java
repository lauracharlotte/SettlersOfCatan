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
    
    public CatanColor getColor()
    {
        return color;
    }

    public String getName()
    {
        return name;
    }

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
