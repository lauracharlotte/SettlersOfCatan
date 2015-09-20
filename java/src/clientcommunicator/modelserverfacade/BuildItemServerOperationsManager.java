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
    
    /**
     * @pre The BuyDevCardRequest is valid--the user has the right number of resources. It's the user's turn.
     * @post The user has a new development card, and loses the resource cost.
     * @param request Request object to buy a development card
     */
    public void buyDevCard(BuyDevCardRequest request)
    {
        
    }
    
    /**
     * @pre The build city request must be valid--it is the user's turn, the user has a settlement on the desired location, and has the required resources(unless it is setup).
     * @post The city replaces the settlement.  The player loses the resource cost (unless it is setup).  The player loses a city.  The player gains a settlement.
     * @param request Request object to build a city.
     */
    public void buildCity(BuildCityRequest request)
    {
        
    }
    
    /**
     * @pre The build settlement request must be valid--it is the user's turn, the user has the required resources, and the desired location is next to a road the user owns (unless it is setup).
     * @post The settlement is placed on the vertex and the player loses the resource cost unless it is setup.
     * @param request Request object to build a settlement.
     */
    public void buildSettlement(BuildSettlementRequest request)
    {
        
    }

    /**
     * @pre The BuildRoadRequest is valid.  It is the user's turn, the user has the required resources, and the desired location is next to another road the user owns unless it is setup--then the location must be next to a settlement.
     * @param request Request object to build a road.
     */
    public void buildRoad(BuildRoadRequest request)
    {
        
    }
    
}
