/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import java.sql.Connection;

import server.IGameAccess;
import server.IPersistenceFactory;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class DBFactory implements IPersistenceFactory
{
	//Got most of this from my 240 project - Laura
	
	public static void initialize()
	{
		try {
			final String driver = "org.sqlite.JBDC";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DBUserAccess dbUserAccess;
	private DBGameAccess dbGameAccess;
	private Connection connect;
	
	public DBFactory()
	{
		dbUserAccess = new DBUserAccess(this);
		dbGameAccess = new DBGameAccess();
		connect = null;
		initialize();
	}
	// Calling All DAOs-------------------
	@Override
	public IUserAccess getUserAccessObject() 
	{
		return dbUserAccess;
	}

	@Override
	public IGameAccess getGameAccessObject() 
	{
		return dbGameAccess;
	}
	
	public Connection getConnect()
	{
		return connect;
	}
	//------------------------------------
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
