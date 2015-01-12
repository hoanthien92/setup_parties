
/**
 * This class represents a Party and its important features.
 * 
 * @author Martha Kosa
 * @version Oct. 2, 2014
 */
import java.text.*;
import java.io.*;
import java.util.*;

public class Party implements Serializable, Statable
{
    private String name;
    private String host;
    private String date;
    private String location;
    private int maxGuests;
    private int numInvited;
    private ArrayList<String> whosInvited;
    private ArrayList<String> whosAttending;
    private ArrayList<String> whosNotAttending;
    private boolean isPerParty;
    private boolean isPaidFor;
    private double price;
    public static final double DEFAULT_PRICE = 200;
    public static final double DEFAULT_PP_PRICE = 21.75;
    public static final int DEFAULT_GUESTS = 20;
    public static final double NO_SHOW_FEE = 5.75;
    public static final int MAX_MONTHS = 12;
    private static final DecimalFormat FMT = new DecimalFormat("$#,##0.00");
    private static int comparisonField = 1;
    /**
     * Party Constructor
     * Creates a new Party object using the input data from PartyPlanner class
     * Throws: PartyException to catch problems during the time it creates Party Object
     */

    public Party(String theName, String who, String theDate, String place, int maxToInvite,
                 boolean perParty, double thePrice) throws PartyException
    {
        if (theName == null || theName.equals("") || who == null || who.equals("") || theDate == null || place == null || place.equals(""))
        {
            throw new PartyException("Null/empty string passed in");
        }
        if (Utilities.invalidDate(theDate))
        {
            throw new PartyException("Invalid date passed in");
        }
        name = theName;
        host = who;
        date = theDate;
        location = place;
       
        isPerParty = perParty;
        isPaidFor = false;
        whosInvited = new ArrayList<String>();
        whosAttending = new ArrayList<String>();
        whosNotAttending = new ArrayList<String>();
        numInvited = 0;
        if (maxToInvite <= 0)
        {
            maxGuests = DEFAULT_GUESTS;
        }
        else
        {
            maxGuests = maxToInvite;
        }
        if (thePrice <= 0)
        {
            if (isPerParty)
            {
                price = DEFAULT_PRICE;
            }
            else
            {
                price = DEFAULT_PP_PRICE;
            }
        }
        else
        {
            price = thePrice;
        }
    }
    /**
     * Method to set a party Paid or not passing a boolean value of whether a party is paid or not
     * Postcondition: nothing returned, change the party's payment status
     */
    
    public void setPaid(boolean paid)
    {
        isPaidFor = paid;
    }
    /**
     * Method to get the name of the Party
     * Postcondition: return the name of the Party in String
     */
    
    public String getName()
    {
        return name;
    }
    /**
     * Method to get the host's name of the Party
     * Postcondition: return the host name of the party in String
     */
    
    public String getHost()
    {
        return host;
    }
    /**
     * Method to get the date of the Party
     * Postcondition: return the date of the party in String
     */
    
    public String getDate()
    {
        return date;
    }
    /**
     * Method to get the location of the Party
     * Postcondition: return the location of the party in String
     */
    
    public String getLocation()
    {
        return location;
    }
    /**
     * Method to get max Guests of the Party
     * Postcondition: return the max Guests of the party in Integer
     */    
    public int getMaxGuests()
    {
        return maxGuests;
    }
    /**
     * Method to get who is invited of the Party
     * Postcondition: return the invited people in String
     */    
    public String getWhosInvited()
    {
        return whosInvited.toString();
    }
    /**
     * Method to get who is attending of the Party
     * Postcondition: return the attendees of the party in String
     */    
    public String getWhosAttending()
    {
        return whosAttending.toString();
    }
    /**
     * Method to get the number of attendee of the Party
     * Postcondition: return the number of attendee of the party in Integer
     */
    
    public int getNumAttending()
    {
        return whosAttending.size();
    }
    /**
     * Method to get who is not attending of the Party
     * Postcondition: return the people who are not attending of the party in String
     */
    
    public String getWhosNotAttending()
    {
        return whosNotAttending.toString();
    }
    /**
     * Method to get the number of people who are not attending of the Party
     * Postcondition: return the number of people who are not attending of the party in Integer
     */
    
    public int getNumNotAttending()
    {
        return whosNotAttending.size();
    }
    /**
     * Method to get the unknown people (unsure) of the Party
     * Postcondition: return the unknown people in Array List
     */
    
    public ArrayList<String> getWhosUnknown()
    {
        ArrayList<String> result = new ArrayList<String>();
        for (String who: whosInvited)
        {
            if (!whosAttending.contains(who) && !whosNotAttending.contains(who))
            {
                result.add(who);
            }
        }
        return result;
    }
    
    /**
     * Method to get number of Unknown of the Party
     * Postcondition: return the number of Unknown of the party in Integer
     */
     
    public int getNumUnknown()
    {
        return getWhosUnknown().size();
    }
    /**
     * Method to get the number of invited people of the Party
     * Postcondition: return the number of invited people of the party in Integer
     */    
    public int getNumInvited()
    {
        return whosInvited.size();
    }
    /**
     * Method to print out a Party in String
     * Precondition: not null
     * Postcondition: return party information in string
     */
    
    public String toString()
    {
        return "Party: " + name + "\nHost : " + host + "\nDate: " + date +
        "\nLocation: " + location + "\nMax Guests: " + maxGuests + "\nAttending: " + whosAttending + "\nNot Attending: " +
        whosNotAttending + "\nUnsure: " + getWhosUnknown() + "\nPrice " + 
        FMT.format(price) + (isPerParty ? "" : " per person") + "\nCost " +
        FMT.format(getCost()) + "\nHas Paid? " + isPaidFor;
    }
    /**
     * Method to process to pay for a party with no Parameter (pay in Full)
     * Postcondition: return in string and call the pay method with parameter is 1 (for 1 month) to pay FULL
     */
    
    public String pay()
    {
        return pay(1);
    }
    /**
     * Method to get the cost of the Party
     * Postcondition: return the cost of the party in Double
     */
    
    public double getCost()
    {
        double result;
        if (isPerParty)
        {
            result = price;
        }
        else
        {
            result = price * getNumAttending() + NO_SHOW_FEE * getNumUnknown();
        }
        return result;
    }
    /**
     * Method to process to pay for a party with one Parameter (pay per month)
     * Postcondition: return in String to display an appropriate method after processing payment
     */
    
    public String pay(int numMonths)
    {
        String result;
        if (numMonths <= 0 || numMonths > MAX_MONTHS)
        {
            numMonths = MAX_MONTHS;
        }
        if (isPaidFor)
        {
            result = "Party " + name + " on " + date + " has already been paid for.";
        }
        else
        {
            result = "The cost of party " + name + " is " +
                     FMT.format(getCost() / numMonths);
            if (numMonths > 1)
            {
                result += " per month for " + numMonths + " months.";
            }
            isPaidFor = true;
        }
        return result;
    }
    /**
     * Method to invite to a party
     * Postcondition: return true if it invites successfully and false if i can't invite
     */
    
    public boolean invite(String who)
    {
        boolean result = false;
        if (!isPaidFor && numInvited < maxGuests)
        {
            if (!whosInvited.contains(who) && numInvited < maxGuests)
            {
                whosInvited.add(who);
                numInvited++; // added by MJK on 11/4/2014
                result = true;
            }
        }
        return result;
    }
    /**
     * Method to accept a person to a party
     * Postcondition: return true if it accepts successfully and false if i can't accept
     */
    
    public boolean takeAccept(String who)
    {
        boolean result = false;
        if (!isPaidFor)
        {
            if (whosInvited.contains(who))
            {
                result = true;
                if (!whosAttending.contains(who))
                {
                    result = true;
                    whosAttending.add(who);
                    int whoNotAttend = whosNotAttending.indexOf(who);
                    if (whoNotAttend != -1) // moving someone out of the not attending list
                    
                    {
                        whosNotAttending.remove(whoNotAttend);
                    } 
                }   
            }
        }
        return result;        
    }
    /**
     * Method to regret a person to a party
     * Postcondition: return true if it regrets successfully and false if i can't regret
     */
    
    public boolean takeRegret(String who)
    {
        boolean result = false;
        if (!isPaidFor)
        {
            if (whosInvited.contains(who))
            {
                if (!whosNotAttending.contains(who))
                {
                    result = true;
                    whosNotAttending.add(who);
                    int whoAttend = whosAttending.indexOf(who);
                    if (whoAttend != -1) // moving someone out of the attend list
                    {
                        whosAttending.remove(whoAttend);
                        numInvited--; // added by MJK on 11/4/2014
                    } 
                }                   
            }
        }
        return result;
    }
    /**
    * Method to check if a party is paid or not
    * Postcondition: return true if its paid and false if it is not paid 
    */    
    public boolean isPaid()
    {
        return isPaidFor;
    }
    /**
    * Method to update the price of the party
    * Postcondition: return true if its updated successfully and false if it can't update the price 
    */
    
    public boolean updatePrice(int percentage)
    {
        boolean result = false;
        if (!isPaidFor)
        {
            if (Math.abs(percentage) <= 20)
            {
                price = price * (1 + percentage / 100.0);
                result = true;
            }
        }
        return result;
    }
    /**
    * Method to format the file when it process writing the parties into File
    * Postcondition: return string of showing how the information looks like in the file 
    */
    
    public String toFileString()
    {
        String attending = whosAttending.toString();
        attending = attending.substring(1, attending.length() - 1);
        String notAttending = whosNotAttending.toString();
        notAttending = notAttending.substring(1, notAttending.length() - 1);
        String unknown = getWhosUnknown().toString();
        unknown = unknown.substring(1, unknown.length() - 1);
        int numAttending = getNumAttending();
        int numNotAttending = getNumNotAttending();
        int numUnknown = getNumUnknown();
        StringBuffer result = new StringBuffer("");
        result.append(name + "," + host + "," + date + "," + location + "," + maxGuests + "," +
                      price + "," + isPerParty + "," + isPaidFor + "," +
                      numAttending + ",");
        if (numAttending != 0)
        {
            result.append(attending + ",");
        }
        result.append(numNotAttending + ",");
        if (numNotAttending != 0)
        {
            result.append(notAttending + ",");
        }
        result.append(numUnknown + ",");
        if (numUnknown != 0)
        {
            result.append(unknown);
        }
        return result.toString();
    }
   /**
    * Method to get the state of the party
    * Postcondition: return string to show how party's info looks like 
    */
    public String getState()
    {
        return toFileString();
    }
    /**
    * Method to get the key of the party
    * Postcondition: return string with the information about party's name and date
    */    
    public String getKey()
    {
        return name + date;
    }
}
