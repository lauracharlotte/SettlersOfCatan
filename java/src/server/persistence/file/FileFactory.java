/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.file;

import server.IGameAccess;
import server.IPersistenceFactory;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class FileFactory implements IPersistenceFactory
{

    @Override
    public IUserAccess getUserAccessObject()
    {
        return new FileUserAccess();
    }

    @Override
    public IGameAccess getGameAccessObject() 
    {
        return new FileGameAccess();
    }

    @Override
    public void beginTransaction() 
    {
        return;
    }

    @Override
    public void endTransaction() 
    {
        return;	
    }

    @Override
    public void wipe()
    {
        this.getGameAccessObject().clearPersistance();
        this.getUserAccessObject().deleteAllUsers();
    }
    
}
