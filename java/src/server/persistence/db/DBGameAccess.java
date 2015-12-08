/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        PreparedStatement pstmt;
        
        Collection<ClientModel> games = new ArrayList<>();
        ResultSet rs;
        
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next())
            {
               ObjectInputStream objectIn;
                byte[] buff = rs.getBytes(1);
                if (buff != null)
                {
                    try {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buff));
                        ClientModel game = (ClientModel) objectIn.readObject();
                        games.add(game);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            pstmt.close();
            rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return games;
    }

    @Override
    public boolean saveGame(ClientModel game, int gameId) 
    {
        // insert or replace into Game (Model, ID) values(?,?);
        String query = "INSERT or REPLACE into Game (Model, ID) \n" +
                "values(?,?) ;";
        
        PreparedStatement pstmt;
        int result;
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            pstmt.setObject(1, game);
            pstmt.setInt(2, gameId);
            result = pstmt.executeUpdate();
            
            pstmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return result > 0;
    }

    @Override
    public boolean saveCommand(ICommand command, int gameId) 
    {                
        String insertQuery = "INSERT INTO Command (gameNumber, commandNumber, command)\n" +
            "VALUES (?, ?, ?);";
        PreparedStatement pstmt;
        
        int commandNumber = getNextCommandNumber(gameId);
        int result;
        
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(insertQuery);
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, commandNumber);
            pstmt.setObject(3, command);
            result = pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return result > 0;
    }

    @Override
    public int getCommandAmount(int gameId) 
    {
        String query = "SELECT COUNT(*)\n" +
        "FROM Command\n" +
        "WHERE gameNumber= ?;";
        
        PreparedStatement pstmt;
        ResultSet rs;
        int result = 0;
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            pstmt.setInt(1, gameId);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                result = rs.getInt(1);
            }
            pstmt.close();
            rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        return result;
    }
    
    @Override
    public boolean deleteGameCommands(int gameId) {
        String query = "DELETE FROM Command\n" +
        "WHERE gameNumber= ?;";
        
        PreparedStatement pstmt;
        int result;
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            pstmt.setInt(1, gameId);
            result = pstmt.executeUpdate();
            
            pstmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return result > 0;
    }

    @Override
    public Collection<ICommand> getAllCommands(int gameId)
    {
        String query = "SELECT *\n" +
            "FROM Command\n" +
            "WHERE gameNumber= ? \n" +
            "ORDER BY commandNumber";
        PreparedStatement pstmt;
        
        Collection<ICommand> commands = new ArrayList<>();
        ResultSet rs;
        
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            pstmt.setInt(1, gameId);
            rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                ObjectInputStream objectIn;
                byte[] buff = rs.getBytes(1);
                if (buff != null)
                {
                    try {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buff));
                        ICommand command = (ICommand) objectIn.readObject();
                        commands.add(command);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            pstmt.close();
            rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return commands;
    }
    
    @Override
    public void clearPersistance()
    {
        String query = "DELETE FROM Game, Command";
        PreparedStatement pstmt;
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getNextCommandNumber(int gameId)
    {
        String commandNumberQuery = "SELECT MAX(commandNumber) "+"FROM Command " +
        "WHERE gameNumber= ? ;";
        PreparedStatement pstmt;
        
        try
        {
            pstmt = myFactory.getConnect().prepareStatement(commandNumberQuery);
            pstmt.setInt(1, gameId);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        ResultSet rs;
        try
        {
            rs = pstmt.executeQuery();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        int commandNumber = 0;
        
        try
        {
            if(rs.next())
            {
                commandNumber = rs.getInt(1) + 1;
            }
            pstmt.close();
            rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBGameAccess.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        return commandNumber;
    }
    
}
