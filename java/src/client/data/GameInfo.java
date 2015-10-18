package client.data;

import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
    private int id;
    private String title;
    private List<PlayerInfo> players;

    public GameInfo()
    {
            setId(-1);
            setTitle("");
            players = new ArrayList<PlayerInfo>();
    }

    public int getId()
    {
            return id;
    }

    public void setId(int id)
    {
            this.id = id;
    }

    public String getTitle()
    {
            return title;
    }

    public void setTitle(String title)
    {
            this.title = title;
    }

    public void addPlayer(PlayerInfo newPlayer)
    {
            players.add(newPlayer);
    }

    public List<PlayerInfo> getPlayers()
    {
            return Collections.unmodifiableList(players);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final GameInfo other = (GameInfo) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if (!Objects.equals(this.title, other.title))
        {
            return false;
        }
        if (!Objects.equals(this.players, other.players))
        {
            return false;
        }
        return true;
    }
        
}

