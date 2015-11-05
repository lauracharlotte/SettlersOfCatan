/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import org.json.JSONException;

/**
 *
 * @author Michael
 */
public interface IJSONSerializable 
{
    
    /**
     * @pre The object has all the necessary, valid data to serialize to JSON
     * @return String that represents object as JSON.
     */
    public String serialize();
    
    public void deserialize(String JSON) throws JSONException;
}
