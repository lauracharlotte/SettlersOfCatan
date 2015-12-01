/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import model.ClientModel;
import model.player.User;
import server.IPersistenceFactory;

/**
 *
 * @author Michael
 */
public class UserManager
{
    private Collection<User> userList = Collections.synchronizedList(new ArrayList<User>());
    
    private IPersistenceFactory persistence;
    
    public UserManager(IPersistenceFactory persistence)
    {
        this.persistence = persistence;
    }
    
    public User getUserWithId(int id)
    {
        if(id < 0 || id >= userList.size())
            throw new IllegalArgumentException();
        Iterator<User> collItr = userList.iterator();
        for(int i=0; i<id; i++)
            collItr.next();
        return collItr.next();
    }
    
    public int addUser(User newUser)
    {
        if(this.getUserWithUsername(newUser.getUsername()) != null)
            return -1;
        this.userList.add(newUser);
        return this.userList.size() - 1;
    }
    
    public User getUserWithUsername(String username)
    {
        Iterator<User> itr = this.userList.iterator();
        while(itr.hasNext())
        {
            User nextUser = itr.next();
            if(nextUser.getUsername().equals(username))
                return nextUser;
        }
        return null;
    }
    
    /**
     * @param userToBeVerified the user you want to verify
     * @return true if the user is in the collection, false otherwise
     */
    public boolean verifyUser(User userToBeVerified)
    {
        return this.userList.contains(userToBeVerified);
    }
}
