
/**
 * Class PartyPlannerException.
 * 
 * Thien Lai program 2
 * Class PartyPlannerException to prevent bad inputs from users
 * 
 */
public class PartyPlannerException extends Exception
{
    /**
     * Create a new PartyPlannerException with a default message
     */
    public PartyPlannerException()
    {
        this("General Party Planner Exception.");
    }
    
    /**
     * Create a new PartyPlannerException with message is the input
     * parameter
     */
    public PartyPlannerException(String message)
    {
        super(message);
    }
}
