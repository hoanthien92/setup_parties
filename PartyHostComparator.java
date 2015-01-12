import java.util.Comparator;
import java.text.Collator;
/**
 * This class is used to compare two Party Objects by their name.
 * 
 * @author Thien Lai 
 * @version 10/25/2014
 */
public class PartyHostComparator implements Comparator<Party>
{
  /**
   * Compares two Party objects by their name
   * 
   * Preconditions: party1 and party 2 cannot be null
   * Postconditions: Returns an integer to represent the difference of them.
   */
  public int compare(Party party1, Party party2)
  {
      Collator comp = Collator.getInstance(); //initialize the Collator variable comp
      String partyHost1 = party1.getHost(); //get the party1's host name
      String partyHost2 = party2.getHost(); //get the party2's host name
      
      //Return an integer less than, equal than or greater than 0 depends whether partyName1 >, = or < partyName2
      return comp.compare(partyHost1,partyHost2);
  }
}
