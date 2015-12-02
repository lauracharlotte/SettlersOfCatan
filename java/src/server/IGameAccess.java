/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Collection;

import model.ClientModel;
import server.command.ICommand;

/**
 *
 * @author Michael
 */
public interface IGameAccess
{
    public Collection<ClientModel> getGames();
    public boolean saveGame(ClientModel game);
    public boolean saveCommand(ICommand command, int gameId);
}
