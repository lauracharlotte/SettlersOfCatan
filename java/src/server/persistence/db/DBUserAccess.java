/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import model.player.User;
import server.IUserAccess;

/**
 *
 * @author Michael
 */
public class DBUserAccess implements IUserAccess
{
	//Got code mostly from Laura's 240 code.
	private DBFactory db; //is this correct or no because we really want just one db not a factory of them
	public DBUserAccess(DBFactory db)
	{
		this.db = db;
	}
    @Override
    public Collection<User> getUsers()
    {
    	//getAll Users
    	Collection<User> results = new ArrayList<User>();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	try {
        	String selectAllQuery = "SELECT * FROM User ORDER BY ID ASC;";
			stmt = db.getConnect().prepareStatement(selectAllQuery);
			rs = stmt.executeQuery();
			while(rs.next())//should this actually start at 0???
			{
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				results.add(new User(username, password));//do we need to change the constructor so it contains the id or does it not matter?????
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//Dont really understand this part so much, or at least why we have to use "finally"
    	finally
    	{
    		try {
    			stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return results;    	
    }

    @Override
    public boolean saveNewUser(User newUser)
    {
    	//add Users
    	PreparedStatement stmt = null;
    	
    	try {
        	String insertQuery = "INSERT INTO User(ID, Username, Password) VALUES(?,?,?)";
			stmt = db.getConnect().prepareStatement(insertQuery);
			stmt.setInt(1, newUser.getPlayerId());
			stmt.setString(2, newUser.getUsername());
			stmt.setString(3, newUser.getPassword());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally
    	{
    		try {
                    stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return true;//CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Override
    public void deleteAllUsers()
    {
    	//add Users
    	PreparedStatement stmt = null;
    	ResultSet keyRS = null;
    	
    	try {
        	String insertQuery = "DELETE FROM User";
			stmt = db.getConnect().prepareStatement(insertQuery);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally
    	{
    		try {
				stmt.close();
				keyRS.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    
}
