/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private final DBUserAccess dbUserAccess;
	private final DBGameAccess dbGameAccess;
	private Connection connect = null;
	
	public DBFactory()
	{
            initialize();
            dbUserAccess = new DBUserAccess(this);
            dbGameAccess = new DBGameAccess(this);
            connect = null;
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
            final String dbname = "Catan.sqlite";
            String URL = "jdbc:sqlite:"+"db"+File.separator+dbname;
            try
            {
                this.connect = DriverManager.getConnection(URL);
                this.connect.setAutoCommit(false);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	@Override
	public void endTransaction() 
	{
            try
            {
                this.connect.commit();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                try
                {
                    this.connect.close();
                    this.connect = null;
                }
                catch(SQLException ex)
                {
                    this.connect=null;
                }
            }
		
	}
    
}
