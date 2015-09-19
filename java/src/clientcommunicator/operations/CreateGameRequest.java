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
    
    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }
    
    public boolean isRandomTiles()
    {
        return randomTiles;
    }

    public boolean isRandomNumbers()
    {
        return randomNumbers;
    }

    public boolean isRandomPorts()
    {
        return randomPorts;
    }

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
