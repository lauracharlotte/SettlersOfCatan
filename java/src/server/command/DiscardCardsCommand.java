package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
/**
 * Executes the Discard Cards request.
 * @author Scott
 */
public class DiscardCardsCommand implements ICommand{

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
