/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;

/**
 *
 * @author Michael
 */
public class SendChatRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private String content;

    /**
     *
     * @return
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return
     */
    public String getContent()
    {
        return content;
    }

    /**
     *
     * @param playerIndex
     * @param content
     */
    public SendChatRequest(PlayerIdx playerIndex, String content)
    {
        this.playerIndex = playerIndex;
        this.content = content;
    }
    
    
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
