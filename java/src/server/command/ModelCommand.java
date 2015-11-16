package server.command;

import server.facade.GameFacade;
import server.facade.IGameFacade;
import server.facade.IModelFacade;
import server.facade.MockGameFacade;
import server.model.GameManager;
import server.model.UserManager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONParser;
import clientcommunicator.modelserverfacade.JSONSerializer;
import model.ClientModel;
import model.player.User;
import server.ServerException;
/**
 * This class gives the model of the game.
 * @author LaurasAdventurePC
 * 
 */
public class ModelCommand implements ICommand 
{
	/**
	 * The constructor
	 */
	public ModelCommand()
	{
		
	}
	
	/**
	 * Gives the Model for that game.
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
        {
		// TODO Auto-generated method stub //facade to Game Model
		IGameFacade gameFacade = (IGameFacade) facade;
		
		int gameNumber = currentCookie.getGameNumber();
		ClientModel curModel = gameFacade.model(gameNumber);
		JSONSerializer serializeThis = new JSONSerializer();
		String answerSoFar = serializeThis.SerializeModel(curModel);
		//System.out.println(answerSoFar);
		
		return answerSoFar;
	}
	/*public static void main(String[] args)
	{
		System.out.println("Hello");
		UserManager testUser = new UserManager();
		GameManager testGame = new GameManager();
		List<User> testUserList = new ArrayList<User>();
		List<ClientModel> testGameList = new ArrayList<ClientModel>();
		MockGameFacade testGameFacade = new MockGameFacade(testUserList, testGameList);
		String testString = "";
		Cookie testCookie = new Cookie();
		testCookie.setGameNumber(0);
		String result = "";
		try {
			ModelCommand testCommand = new ModelCommand();
			result = testCommand.execute(testGameFacade, testString, testCookie);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		System.out.println("How are you?");
		
		JSONParser testParser = new JSONParser();
		ClientModel testParsedModel = new ClientModel();
		try {
			System.out.println("I'm in California dreaming about who we use to be");
			testParsedModel = testParser.fromJSONToModel(result);
			System.out.println("When we were younger");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("And free.");
		System.out.println(testParsedModel.toString());
		System.out.println("Hello from the outside!!!");
		
		//
		
		
	}*/

}
