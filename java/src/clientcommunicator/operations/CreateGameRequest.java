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
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param name
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
     * @return
     */
    public boolean isRandomTiles()
    {
        return randomTiles;
    }

    /**
     *
     * @return
     */
    public boolean isRandomNumbers()
    {
        return randomNumbers;
    }

    /**
     *
     * @return
     */
    public boolean isRandomPorts()
    {
        return randomPorts;
    }

    /**
     *
     * @return
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
