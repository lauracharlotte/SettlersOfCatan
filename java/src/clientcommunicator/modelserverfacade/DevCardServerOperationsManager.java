/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.MonopolyRequest;
import clientcommunicator.operations.MonumentRequest;
import clientcommunicator.operations.PlaySoldierRequest;
import clientcommunicator.operations.RoadBuildingCardRequest;
import clientcommunicator.operations.YearOfPlentyRequest;

/**
 *
 * @author Michael
 */
public class DevCardServerOperationsManager implements IServerOperationsManager
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
    public void yearOfPlenty(YearOfPlentyRequest request)
    {
        
    }
    
    /**
     *
     * @param request
     */
    public void playRoadBuilding(RoadBuildingCardRequest request)
    {
        
    }
    
    /**
     *
     * @param request
     */
    public void playSoldier(PlaySoldierRequest request) 
    {
        
    }
    
    /**
     *
     * @param request
     */
    public void playMonopoly(MonopolyRequest request)
    {
        
    }
    
    /**
     *
     * @param request
     */
    public void playMonument(MonumentRequest request)
    {
        
    }
    
}