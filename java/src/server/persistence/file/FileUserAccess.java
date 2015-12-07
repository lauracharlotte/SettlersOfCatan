/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.player.User;
import org.apache.commons.io.FilenameUtils;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class FileUserAccess implements IUserAccess
{

    @SuppressWarnings("resource")
	@Override
    public Collection<User> getUsers()
    {
        File folder = new File(System.getProperty("user.dir"));
        File[] filesInFolder = folder.listFiles();
        List<User> users = new ArrayList<>();
        for(File f : filesInFolder)
            if(FilenameUtils.getExtension(f.getName()).equals("userobj"))
            {
                FileInputStream streamIn;
            try {
                streamIn = new FileInputStream(f);
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
                ObjectInputStream objectinputstream = null;
            try {
                objectinputstream = new ObjectInputStream(streamIn);
            }
            catch (IOException ex) {
                Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            try
            {
                User newUser = (User)objectinputstream.readObject();
                users.add(newUser);
            }
            catch (IOException | ClassNotFoundException ex)
            {
                Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
            }   
        User[] finalCollectionOrder = new User[users.size()];
        for(User u: users)
            finalCollectionOrder[u.getPlayerId()] = u;
        return Arrays.asList(finalCollectionOrder);
    }

    @SuppressWarnings("resource")
	@Override
    public boolean saveNewUser(User newUser)
    {
        FileOutputStream fout;
        try
        {
            fout = new FileOutputStream(System.getProperty("user.dir")+File.separator+newUser.getUsername()+".userobj", false);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(fout);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try
        {
            oos.writeObject(newUser);
            oos.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public void deleteAllUsers()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
