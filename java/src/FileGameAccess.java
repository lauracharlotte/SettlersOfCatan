/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.ClientModel;
import model.player.User;
import org.apache.commons.io.FilenameUtils;
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
        ArrayList<ClientModel> models = new ArrayList<ClientModel>();
        int gameId = 0;
        Object o = this.loadSerializable(gameId+".catanmodel");
        while(o!=null)
        {
            models.add((ClientModel)o);
            gameId++;
            o = this.loadSerializable(gameId+".catanmodel");
        }
        return models;
    }

    @Override
    public boolean saveGame(ClientModel game, int gameId) 
    {
        return this.saveSerialize(game, System.getProperty("user.dir")+File.separator+gameId+".catanmodel");
    }
    
    @SuppressWarnings("resource")
	private Object loadSerializable(String fName)
    {
        File file = new File(System.getProperty("user.dir")+File.separator+fName);
        if(!file.exists())
            return null;
        FileInputStream fis;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        ObjectInputStream objectinputstream = null;
        try 
        {
            objectinputstream = new ObjectInputStream(fis);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try
        {
            Object newObject = objectinputstream.readObject();
            fis.close();
            return newObject;
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @SuppressWarnings("resource")
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
            fout.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public Collection<ICommand> getAllCommands(int gameId)
    {
        ArrayList<ICommand> commands = new ArrayList<>();
        Collection<ICommand> o = (Collection<ICommand>)this.loadSerializable(gameId+".commands");
        if(o != null)
            commands.addAll(o);
        return commands;
    }
    
    private boolean saveAllCommands(Collection<ICommand> commands, int gameId)
    {
        return this.saveSerialize((Serializable)commands, System.getProperty("user.dir")+File.separator+gameId+".commands");
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
        File currentCommandFile = new File(System.getProperty("user.dir")+File.separator+gameId+".commands");
        boolean exists = currentCommandFile.exists();
        try
        {
            if(exists)
                Files.delete(currentCommandFile.toPath());
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //boolean delete = currentCommandFile.delete();
        return exists;
    }
    
    @Override
    public void clearPersistance()
    {
        File folder = new File(System.getProperty("user.dir"));
        File[] filesInFolder = folder.listFiles();
        List<User> users = new ArrayList<>();
        for(File f : filesInFolder)
            if(FilenameUtils.getExtension(f.getName()).equals("commands") || FilenameUtils.getExtension(f.getName()).equals("catanmodel"))
                try {
                    Files.delete(f.toPath());
        }
        catch (IOException ex) {
            Logger.getLogger(FileGameAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
