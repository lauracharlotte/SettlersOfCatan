/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.IJSONSerializable;
import java.util.Collection;

/**
 *
 * @author Michael
 */
public class JSONParser 
{

    /**
     *
     * @param serializable
     * @return
     */
    public static String toJSON(IJSONSerializable serializable)
    {
        return serializable.Serialize();
    }
    
    /**
     *
     * @param modelJSON
     * @return
     */
    public static ClientModel fromJSONToModel(String modelJSON)
    {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
    
    /**
     *
     * @param gameListJSON
     * @return
     */
    public static Collection<GameJSONResponse> fromJSONToGameCollection(String gameListJSON)
    {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
    
    /**
     *
     * @param gameJSON
     * @return
     */
    public static GameJSONResponse fromJSONToGame(String gameJSON)
    {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
}