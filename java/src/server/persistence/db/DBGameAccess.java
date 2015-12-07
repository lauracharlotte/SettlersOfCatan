/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.ClientModel;
import server.IGameAccess;
import server.command.ICommand;

/**
 *
 * @author Michael
 */
public class DBGameAccess implements IGameAccess
{

    private DBFactory myFactory;
    
    public DBGameAccess(DBFactory factory)
    {
        myFactory = factory;
    }
    
    @Override
    public Collection<ClientModel> getGames() 
    {
            // TODO Auto-generated method stub
        String query = "SELECT model " +
            "FROM Game order by ID;";
        return null;
    }

    @Override
    public boolean saveGame(ClientModel game, int gameId) 
    {
        String query = "UPDATE Game SET model=newModel\n" +
                "WHERE ID= ? ;";
        return true;
    }

    @Override
    public boolean saveCommand(ICommand command, int gameId) 
    {
        String commandNumberQuery = "SELECT MAX(commandNumber) "+"FROM Command " +
        "WHERE gameNumber= ? ;";
        
        String insertQuery = "INSERT INTO Command (gameNumber, commandNumber, command)\n" +
            "VALUES (?, ?, ?);";
        PreparedStatement pstmt;
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(commandNumberQuery);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        ResultSet rs;
        try
        {
            rs = pstmt.executeQuery();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        int commandNumber = 0;
        
        try
        {
            if(rs.next())
            {
                commandNumber = rs.getInt(1);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
	//  How to store blob
	//pstmt.setObject(2, objectToSerialize);
        
        return false;
    }

    @Override
    public int getCommandAmount(int gameId) 
    {
        String query = "SELECT COUNT(*)\n" +
        "FROM Command\n" +
        "WHERE gameNumber= ?;";
        return 0;
    }
    
    @Override
    public boolean deleteGameCommands(int gameId) {
        String query = "DELETE FROM Command\n" +
        "WHERE gameNumber= ?;";
        return true;
    }

    @Override
    public Collection<ICommand> getAllCommands(int gameId)
    {
        String query = "SELECT *\n" +
            "FROM Command\n" +
            "WHERE gameNumber=gameId\n" +
            "ORDER BY commandNumber";
        return null;
    }
    
}
