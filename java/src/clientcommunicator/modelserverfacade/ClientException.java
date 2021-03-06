package clientcommunicator.modelserverfacade;

/**
 * This is a generic Client Exception
 * @author Scott
 */
@SuppressWarnings("serial")
public class ClientException extends Exception
{
    public ClientException() { }
    
    public ClientException(String message)
    {
        super(message);
    }
    
    public ClientException(Throwable throwable)
    {
        super(throwable);
    }
    
    public ClientException(String message, Throwable throwable)
    {
        super(message, throwable);
        System.out.println(message);
    }
}
