/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Collection;
import model.player.User;

/**
 *
 * @author Michael
 */
public interface IUserAccess
{
    public Collection<User> getUsers();
    public boolean saveNewUser(User newUser);
    public void deleteAllUsers();
}
