/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.BuildCityRequest;
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.BuyDevCardRequest;

/**
 *
 * @author Michael
 */
public class BuildItemServerOperationsManager implements IServerOperationsManager
{

    @Override
    public void setServer(IServerProxy serverToUse) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void buyDevCard(BuyDevCardRequest request)
    {
        
    }
    
    public void buildCity(BuildCityRequest request)
    {
        
    }
    
    public void buildSettlement(BuildSettlementRequest request)
    {
        
    }

    public void buildRoad(BuildRoadRequest request)
    {
        
    }
    
}
