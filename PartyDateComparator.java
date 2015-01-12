import java.util.Comparator;

/**
 * This class is used to compare two Party objects by their date.
 * 
 * @author Thien Lai
 * @version 10/25/2014
 */
public class PartyDateComparator implements Comparator<Party>
{
  /**
   * Compares two Party Objects by their dates.
   * 
   * Preconditions: party1 and party2 can't be null.
   * Postconditions: Returns an integer to tell how different they are.
   */
  public int compare(Party party1, Party party2)
  {
      String partyDate1 = party1.getDate();
      String partyDate2 = party2.getDate();
      return partyDate2.compareTo(partyDate1);  //Return an integer less than,equal than or greater than 0 depends whether 
                                                //partyDate2 >, = or < partyDate1, compare partyDate2 and partyDate1 to get descending order 
  }
}   
