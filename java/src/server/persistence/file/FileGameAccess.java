/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public Collection<ClientModel> getGames() 
    {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public boolean saveGame(ClientModel game, int gameId) 
    {
        return this.saveSerialize(game, System.getProperty("user.dir")+File.separator+gameId+".catanmodel");
    }
    
    private boolean saveSerialize(Serializable obj, String fName)
    {
        FileOutputStream fout;
        try
        {
            fout = new FileOutputStream(fName, false);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(fout);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        try
        {
            oos.writeObject(obj);
            oos.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public Collection<ICommand> getAllCommands(int gameId)
    {
        return new ArrayList<ICommand>();
    }
    
    private boolean saveAllCommands(Collection<ICommand> commands, int gameId)
    {
        return true;
    }
    

    @Override
    public boolean saveCommand(ICommand command, int gameId) 
    {
        Collection<ICommand> allCommands = this.getAllCommands(gameId);
        allCommands.add(command);
        return this.saveAllCommands(allCommands, gameId);
    }

    @Override
    public int getCommandAmount(int gameId) 
    {
        return this.getAllCommands(gameId).size();
    }

    @Override
    public boolean deleteGameCommands(int gameId) 
    {
        return false;
    }
    
}
