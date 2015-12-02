/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.file;

import java.util.Collection;

import model.ClientModel;
import server.IGameAccess;
import server.command.ICommand;

/**
 *
 * @author Michael
 */
public class FileGameAccess implements IGameAccess
{

	@Override
	public Collection<ClientModel> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveGame(ClientModel game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveCommand(ICommand command, int gameId) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
