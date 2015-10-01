

/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package clientcommunicator.operations;

import java.lang.reflect.Type;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.InstanceCreator;

import com.google.gson.JsonElement;

import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;

import com.google.gson.JsonSerializationContext;

import com.google.gson.JsonSerializer;

import model.cards.ResourceCards;

import model.player.PlayerIdx;

/**

 *

 * @author Michael

 */

public class DiscardCardsRequest implements IJSONSerializable

{

    private PlayerIdx playerIndex;

    private ResourceCards discardedCards;

    /**

    *

    * @return The index of the player that is discarding

    */

    public PlayerIdx getPlayerIndex()

    {

        return playerIndex;

    }

    /**

    *

    * @return The resources that the player is discarding

    */

    public ResourceCards getDiscardedCards()

    {

        return discardedCards;

    }

    /**

    *

    * @param playerIndex The index of the player that is discarding

    * @param discardedCards The resources that the player is discarding

    */

    public DiscardCardsRequest(PlayerIdx playerIndex, ResourceCards discardedCards)

    {

        this.playerIndex = playerIndex;

        this.discardedCards = discardedCards;

    }

       

    @Override

    public String serialize()

    {

     String longString = "{type: \"discardCards\", playerIndex: " + playerIndex.getIndex() +", discardedCards: {brick:"+discardedCards.getBrick()+", "

     +"ore:"+discardedCards.getOre() +", sheep:"+discardedCards.getWool() + ", wheat:"+discardedCards.getGrain()+", wood:"

     +discardedCards.getLumber()+"}}";

     return longString;

    }

    

    public static void main(final String[] args)

    {

     ResourceCards rec = new ResourceCards (3,3,3,3,3);

     PlayerIdx index = new PlayerIdx(2);

     DiscardCardsRequest thisDiscard = new DiscardCardsRequest(index, rec);

     String work = thisDiscard.serialize();

     System.out.println(work);

    }

}



