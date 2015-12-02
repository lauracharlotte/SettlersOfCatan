/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import server.IGameAccess;
import server.IPersistenceFactory;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class DBFactory implements IPersistenceFactory
{

	@Override
	public IUserAccess getUserAccessObject() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGameAccess getGameAccessObject() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginTransaction() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTransaction() 
	{
		// TODO Auto-generated method stub
		
	}
    
}
