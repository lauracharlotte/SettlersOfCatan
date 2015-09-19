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

/**
 *
 * @author Michael
 */
public class ModelServerFacadeFactory
{

    /**
     *
     * @param server
     */
    public ModelServerFacadeFactory(IServerProxy server)
    {
        
    }
    
    public void updateModel(String newModelJSON)
    {
        
    }
    
    /**
     *
     * @param bodyInformation
     */
    public void sendChat(SendChatRequest bodyInformation) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @param server
     */
    public void setServerProxy(IServerProxy server)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @param operationsClass
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
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
    
    private IServerProxy currentServer;
}
