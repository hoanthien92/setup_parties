import java.util.Comparator;
/**
 * This class is used to compare two Party Objects by their costs
 * 
 * @author Thien Lai 
 * @version 10/25/2014
 */
public class PartyCostComparator implements Comparator<Party>
{
    /**
     * Compares two Party Objects by their cost
     * 
     * Precondition: party1 and party2 can't be null.
     * Postcondition: Return interget to see how they are different
     */
    public int compare(Party party1, Party party2)
    {
        Double partyCost1 = party1.getCost();
        Double partyCost2 = party2.getCost();
        return partyCost1.compareTo(partyCost2);//Return an integer less than,equal than or greater than 0 depends whether 
                                                //partyCost1 >, = or < partyCost2
    }

}
