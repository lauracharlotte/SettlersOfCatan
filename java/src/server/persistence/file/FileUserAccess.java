/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.file;

import java.util.Collection;
import model.player.User;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class FileUserAccess implements IUserAccess
{

    @Override
    public Collection<User> getUsers()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveNewUser(User newUser)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
