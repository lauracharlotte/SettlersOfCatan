/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

/**
 *
 * @author Michael
 */
public class CreateGameRequest implements IJSONSerializable
{
    private boolean randomTiles;
    private boolean randomNumbers;
    private boolean randomPorts;
    private String name;
    
    /**
     *
     * @param randomTiles If the tiles should be placed randomly
     * @param randomNumbers If the numbers should be placed on the tiles randomly
     * @param randomPorts If the ports should be placed randomly
     * @param name The name of the game
     */
    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }
    
    /**
     *
     * @return If the tiles should be placed randomly
     */
    public boolean isRandomTiles()
    {
        return randomTiles;
    }

    /**
     *
     * @return If the numbers should be placed on the tiles randomly
     */
    public boolean isRandomNumbers()
    {
        return randomNumbers;
    }

    /**
     *
     * @return If the ports should be placed randomly
     */
    public boolean isRandomPorts()
    {
        return randomPorts;
    }

    /**
     *
     * @return The name of the game
     */
    public String getName()
    {
        return name;
    }
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
