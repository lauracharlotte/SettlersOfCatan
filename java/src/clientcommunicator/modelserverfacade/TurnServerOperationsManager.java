/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.FinishTurnRequest;
import clientcommunicator.operations.RobPlayerRequest;
import clientcommunicator.operations.RollNumberRequest;

/**
 *
 * @author Michael
 */
public class TurnServerOperationsManager implements IServerOperationsManager
{

    @Override
    public void setServer(IServerProxy serverToUse)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param request 
     */
    void rollNumber(RollNumberRequest request)
    {
        
    }
    
    /**
     * 
     * @param request 
     */
    void finishTurn(FinishTurnRequest request)
    {
        
    }
    
    /**
     * 
     * @param request 
     */
    void robPlayer(RobPlayerRequest request)
    {
        
    }
}
