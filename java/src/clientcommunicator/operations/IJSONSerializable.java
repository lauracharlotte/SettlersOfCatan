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
    
    /**
     * 
     * This method populates the data in the IJSONSerializable object with
     * the information from the JSON parameter
     * @param JSON The JSON representing the request object
     * @throws JSONException
     */
    public void deserialize(String JSON) throws JSONException;
}
