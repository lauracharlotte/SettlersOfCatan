/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.SendChatRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import model.ClientModel;
import model.ClientModelSupplier;

/**
 *
 * @author Michael
 */
public class ModelServerFacadeFactory
{

    private IServerProxy currentServer;
    
    /**
     * @post Same as setServerProxy()
     * @param server An IServerProxy that can respond to requests by this class and operationsManagers classes.
     */
    public ModelServerFacadeFactory(IServerProxy server)
    {
       this.currentServer = server; 
    }
    
    /**
     * This function should also make sure that replacing the current model is necessary by comparing version numbers
     * @pre The parameter has valid JSON for ClientModel contained in it
     * @post The current ClientModel object is replaced by the model represented by the JSON
     * @param newModelJSON contains valid JSON for client model
     */
    public void updateModel(String newModelJSON)
    {
        ClientModel model = JSONParser.fromJSONToModel(newModelJSON);
        if (!model.equals(ClientModelSupplier.getInstance().getModel()))
        {
            ClientModelSupplier.getInstance().setModel(model);
        }
    }
    
    /**
     * @pre The chat request is valid
     * @post The chat has been posted to the server
     * @param bodyInformation contains sender and message information
     */
    public void sendChat(SendChatRequest bodyInformation) throws ClientException
    {
        this.currentServer.sendChat(JSONParser.toJSON(bodyInformation));
    }
    
    /**
     * @pre The IServerProxy is not null--and is a valid IServerProxy
     * @post Will use the passed in "serverToUse" for future requests--will also give created operationsManagers this server.
     * @param server The IServerProxy that this class should use to send requests to.
     */
    public void setServerProxy(IServerProxy server) throws ClientException
    {
        if(server == null)
            throw new IllegalArgumentException("Cannot set server to null.");
        this.currentServer = server;
    }
    
    /**
     * 
     * Use of this method--assuming we have a ModelServerFacadeFactory, f, instantiated somewhere else and its server was set prior
     * 
     * DevCardServerOperationsManager devManager = (DevCardServerOperationsManager)f.getOperationsManager(DevCardServerOperationsManager.Class);
     * 
     * now the devManager is instantiated and has a reference to the ModelServerFacadeFactory's IServerProxy so now can do things like:
     * 
     * devManager.playMonopoly(new MonopolyRequest(...)); 
     * 
     * for example.
     * 
     * @pre The operationsClass object is the class of an IServerOperationsManager implementation.  A server proxy has been set here.
     * @param operationsClass The class object of the desired operations manager
     * @return An IServerOperationsManager of the class given as a parameter.  The server of the returned object is set to the server of this class.
     * @throws NoSuchMethodException Throws when the class has no default constructor
     * @throws InstantiationException Throws when the class is abstract
     * @throws IllegalAccessException The default constructor has a restrictive access modifier
     * @throws InvocationTargetException The default constructor threw an exception
     */
    public IServerOperationsManager getOperationsManager(Class operationsClass) 
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        IServerOperationsManager manager;
        Constructor<?> constructor;
        constructor = operationsClass.getConstructor();
        manager = (IServerOperationsManager)constructor.newInstance();
        manager.setServer(this.currentServer);
        return manager;
    }
   
}
