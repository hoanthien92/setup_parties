import java.io.*;
import java.util.HashMap;
import java.util.Comparator;
/**
 * Party Planner Class
 * 
 * Thien Lai  
 * 
 */
public class PartyPlanner implements Statable, Serializable
{
    private HashMap<String, Party> theParty = new HashMap<String,Party>(); // Create HashMap variable theParty
    private String planner;
    /**
     * Creates a new Party Planner using the default manager name and a Party with default values
     */
    public PartyPlanner()
    {
        theParty = new HashMap<String,Party>(); // Create new hashMap theParty object
        setPlannerName("Tom Lai"); //set the default Planner
        loadParties(); //Load default parties
    }
    
    /**
     * Create a new Party Planner by passing to another contructor using loadParties method to process if it's from text or object
     * Throws : PartyPlannerException if when creating PartyPlanner, it gets some exceptions
     */
    public PartyPlanner(String fileName) throws PartyPlannerException
    {
        this(fileName,false);
    }
    /**
     * Create a new Party Planner using loadParties method
     * throws : PartyPlannerException if when creating PartyPlanner, it gets some exceptions
     */
    public PartyPlanner(String fileName, boolean isObjectFile) throws PartyPlannerException
    {
        loadParties(fileName, isObjectFile);
    }
    
    /**
    *   LoadParties method without return type. Create a party by default values.
    */
    private void loadParties()
    {
        Party p1 = new Party("TTU Party", "Tom Lai", "12/12/12", "Tntech",8, true,2000);
        Party p2 = new Party("TTU Graduation", "Thien", "12/12/14", "My apartment",20, false,200);
        Party p3 = new Party("Wedding", "Toan", "10/11/12", "Los Angeles",1, false,300);
        theParty.put(p1.getKey(), p1);
        theParty.put(p2.getKey(), p2);
        theParty.put(p3.getKey(), p3);   
    }
    /**
     * Method to create party based on their file type
     * throws : PartyPlannerException if when creating PartyPlanner, it gets some exceptions
     */
    private String loadParties(String fileName, boolean isObjectFile)throws PartyPlannerException
    {
        if (isObjectFile)
        {
            return loadObjectParties(fileName);
        }
        else // read from text file and process each line and add each party 
        {
            return loadTextParties(fileName);
        }
    }
    /**
     * Create Party based on Text file
     * Throws: PartyPlannerException if when creating a party, it gets some exceptions
     */
    private String loadTextParties(String fileName) throws PartyPlannerException
    {
        String result;
        String[] token;
        String planner;
        String partyName, host,date, location;
        int maxGuests;
        boolean perParty, isPaid;
        double price;
        int numAttending, numNotAttending, numUnknown;
        int i;
        FileIO fis = null;
        try
        {
            fis = new FileIO(fileName, FileIO.FOR_READING);
            planner = fis.readLine();
            setPlannerName(planner);
            result = fis.readLine();    
            while(result != null )
            {
                if(result.equals("")) //if the last line has a blank line, then break the while loop
                    break;
                token = result.split(","); //Divide a string into different parts separated by ,
                partyName=token[0];
                host = token[1];
                date = token[2];
                location = token[3];
                maxGuests = Integer.parseInt(token[4]);
                price = Double.parseDouble(token[5]);
                perParty = Boolean.parseBoolean(token[6]);
                isPaid = Boolean.parseBoolean(token[7]);
                Party party = new Party(partyName, host, date, location,maxGuests, perParty,price);
                addToParties(party);
                numAttending = Integer.parseInt(token[8]);                
                for (i=9; i<= 8+numAttending;i++)
                {                  
                        party.invite(token[i].toString());
                        party.takeAccept(token[i]);                    
                }    
                numNotAttending = Integer.parseInt(token[9+numAttending]);
                for(i=10+numAttending;i <= (9+numAttending+numNotAttending);i++)
                {                   
                        party.invite(token[i]);
                        party.takeRegret(token[i]);                    
                 }
                numUnknown = Integer.parseInt(token[10+numAttending+numNotAttending]);
                for(i=11+numAttending+numNotAttending; i <= (10+numAttending+numNotAttending + numUnknown);i++)
                {
                        
                        party.invite(token[i]);                    
                }           
            
                result = fis.readLine();
            }
        }
        catch(FileIOException e)
        {
            throw new PartyPlannerException("Problem reading information from file " +  fileName);
        }  
        finally
            {
                try
                {
                    if(fis != null)
                        fis.close();
                }
                catch(FileIOException e)
                {
                    throw new PartyPlannerException("File " + fileName + " cannot be closed");
                }
            }
        return result;
    }
    /**
     * Create Party based on Onject file
     * Throws: PartyPlannerException if when creating a party, it gets some exceptions
     */
    private String loadObjectParties(String fileName) throws PartyPlannerException
    {
            String result = "";
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try
            {
                fis = new FileInputStream(fileName);
                ois = new ObjectInputStream(fis);
                setPlannerName((String) ois.readObject());
                theParty = (HashMap<String,Party>) ois.readObject();
                result = "Read object file " + fileName + " successfully";
            }
            catch(FileNotFoundException e)
            {
                throw new PartyPlannerException ("File " + fileName + " not found");
            }
            catch(ClassNotFoundException e)
            {
                throw new PartyPlannerException("Class not found");
            }
            catch(IOException e)
            {
                throw new PartyPlannerException("Problem reading information from file " +  fileName);
            }
            finally
            {
                try
                {
                    if(ois != null)
                        ois.close();
                }
                catch(IOException e)
                {
                    throw new PartyPlannerException("File " + fileName + " cannot be closed");
                }
            }
            return result;
    }
    
    /** updatePrice method to update the Price of the Party
    * if the party is not null then update the price and return true, otherwise, return false
    */
    public boolean updatePrice(String name,String date, int percentage)
    {
        Party party = findParty(name,date);
        if(party != null)
            return party.updatePrice(percentage);
        else    
            return false;
    }
    /**
    *  setPlannerName to update the name of the planner of the Party 
    */
    public void setPlannerName(String planr)
    {
        planner = planr;
    }
    /**
     * The getPlannerName method gets the name of the planner of the Party and return it as the String
     */
    public String getPlannerName()
    {
        return planner;
    }
    /**
     * Add new Party Object into PartyPlanner Object using HashMap
     * Precondition: Party's Name and Date you try to add are not the same as the current Parties
     * Postcondition: Create new party if name and date are different otherwese show error
     * Throws: PartyPlannerException if it has some exception during the time it adds
     */
    private void addToParties(Party party)
    {
            theParty.put(party.getKey(), party);
    }
    /**
     * Add new Party Object into PartyPlanner Object using HashMap
     * Precondition: parameters are not null 
     * Postcondition: return true if it is succesfully executed and false if not. 
     * Throws: PartyPlannerException if it has some exception during the time it adds
     */
    public boolean addToParties(String party, String host, String date, String location, int maxGuests, 
                                boolean perParty, double price) throws PartyPlannerException
    {
        try
        {
            Party theParty = new Party(party,host,date,location,maxGuests,perParty,price);
            if(findParty(party,date)!=null)
                return false;
            else
            {
                addToParties(theParty);
                return true;
            }   
            
        }
        catch(PartyException e)
        {
            throw new PartyPlannerException("Invalid Party.");
        }    
    }
    /**
     * Method to find a Party based on its specific Name and Date
     * PostCondition: parameter not null
     * PostCondition: return the correct Party
     */
    private Party findParty(String partyName, String date)
    {
        String key = partyName + date;
        Party party = theParty.get(key);
        return party;
    }
    /**
     * Method to get the average Cost per Person
     * Return the cost for per person in Double Type
     * Condition:   if the party is not Paid then the average would be zero.
     *              Only calculate when the Party is paid
     */
    public double getAverageCostPerPerson()
    {
            double total=0;
            int totalAttendant=0;
            for(Party party : theParty.values())
            {
                if (party.isPaid())
                {
                    total += party.getCost();
                    totalAttendant += party.getNumAttending();
                }
            }
            if(totalAttendant != 0)
                return total/totalAttendant;
            else
                return 0.0;
    }    
    
    /**
     * Method to get the state of all parties
     * Precondition: return the string of all parties + planner.
     */
    public String getState()
    {
        String result = planner;
        for(Party party : theParty.values())
            result += "\r\n" + party.getState();
        return result;
    }
    /**
     * Method to get number of people who are attending
     * postcodition: not null
     * precondition: return the integer number of the total people who are attending
     */
    public int getNumAttending(String name, String date)
    {
        int total =-1;
        Party party = findParty(name,date);
        if(party != null)
        {
            total =0;
            total += party.getNumAttending();
        }
        return total;
            
    }
    /**
     * Method to get Number of people who are not attending
     * Postcondition: not null
     * Precondition: return the integer total of people who are not attending
     * 
     */
    public int getNumNotAttending(String name, String date)
    {
        int total = 0;
        Party party = findParty(name,date);
        if(party != null)
            total += party.getNumNotAttending();
        
        return total;
    }
    /**
     *Method to get number of unknown people
     *Postcodition: not null
     *Precondition: return integer of total unknown
     */
    public int getNumUnknown(String name, String date)
    {
        int total = 0;
        Party party = findParty(name,date);
        if(party != null)
            total += party.getNumUnknown();
        
        return total;
    }
    
    /**
     *  Method to get Parties that are paid
     *  Precondition: not null
     *  Postcondition: return the paid Parties in String.
     */
    public String getPaidParties()
    {
        String result = "";
        for(Party party : theParty.values())
            if(party.isPaid())
                result += party.getHost() + "'s party on " + party.getDate() + "\r\n";
        return result;
    }
    /**
     *  Method to get Parties that are unpaid
     *  Precondition: not null
     *  Postcondition: return the unpaid Parties in String.
     */
    public String getUnpaidParties()
    {
    String result = "";
    for(Party party : theParty.values())
    {     
        if(!party.isPaid())
            result += party.getHost() + " 's party on " + party.getDate() + "\r\n";
    }
    return result;        
    }
    /**
     * Method to get who is invited
     * Precondition: not null
     * Postcondition: return the  people who are invited in String
     */
    public String getWhosInvited(String name, String date)
    {
       Party party = findParty(name,date);
       if(party != null)
        return party.getWhosInvited();
       else
       return "Invalid Party";
      
    }
    /**
     * Method to invite a person to a specific date and party's Name
     * Precondition: party is found and not null
     * Postcondition: return true if invite successfully and false if not
     */
    public boolean invite(String who, String partyName, String date)
    {
       Party party = findParty(partyName,date);
       if(party != null)
        return party.invite(who);
       else
        return false;

    }
    /**
     * Method to invite a person to all parties
     * precondition: parties are not null and the person is not already invited
     * Postcondition: return an appropriate message in string whethere invite to all successfully or not with party's name and date
     */
    public String inviteToAll(String who) 
    {
        String invited="";
        boolean invite=false;
        for(Party party : theParty.values())
        {
            if(party.invite(who))
            {
                invite = true;
                invited += "invited to party " + party.getName() + " on " + party.getDate() + " ? " + invite + "\r\n";
            }
            else
            {
                invite = false;
                invited += "invited to party " + party.getName() + " on " + party.getDate() + " ? " + invite + "\r\n";
            }    
                
        }
        return invited;
    }
    /**
     * Method to pay for a party
     * precondition: not null and a party is not paid
     * postcondition: return a string to determine how much it costs
     */
    public String pay(String name, String date)
    {
        String result="";
        Party party = findParty(name,date);
        if(party != null && !party.isPaid())
        {
            return result = party.pay();
        }
        else    
            return result;
    }
    /**
     * Metod to pay for a party per month 
     * (the cost per month depends on how many months are input 
     *  precondition: not null 
     *  Postcondition: return a string to determin how much it costs per month
     */
    public String pay(String name, String date, int numMonths)
    {
        String result="";
        Party party = findParty(name,date);
        if(party != null)
           return result += party.pay(numMonths);
        else
        {     
                return result;    
        }
    }

    /**
    *   Print out the Planner's name and Party's name
    */
    public String toString()
    {
        String result = "Party Planner: " + planner + "\r\nwith the following: \r\n";
        for(Party party : theParty.values())
        {
             result += " \r\n********\r\n" + party + "\r\n";
        }
        return result;
    }
    /**
    *   SetPaymentStatus method to set up the payment status as users input
    */
    public void setPaymentStatus(String name, String date, boolean isPaid)
    {
        Party party = findParty(name,date);
        party.setPaid(isPaid);
    }
    /**
     * Sorts the ArrayList of Party objects:
     * -> by name in ascending order if the input variable field is 1;
     * -> by DATE in descending order if the input variable field is 2;
     * -> by COST of Party if the input variable field is 3.
     * 
     * The sorting algorithm can be choosen by the input variable alg, in which:
     * -> 1 is the Selection Sorting Algorithm;
     * -> 2 is the Insertion Sorting Algorithm;
     * 
     * Preconditions: field end ald parameters must be in [1, 3] range.<br>
     * Posconditions: The ArrayList of Party object is sorted by name, date or the total cost.
     */
    public String sort(int field, int alg)
    {
        String result="";
        Party[] party = new Party[theParty.size()];
        party = theParty.values().toArray(party);
        final int NAME = 1;
        Comparator<Party> comp = null;
        final int DATE = 2;
        final int COST = 3;
        switch(field)
        {
            case NAME:
                comp = new PartyHostComparator();
                break;
            case DATE:
                comp = new PartyDateComparator();
                break;
            case COST:
                comp = new PartyCostComparator();
                break;
        }       
        final int SELECTION = 1;
        final int SE=2;
        final int INSERTION = 3;
        
        switch (alg)
        {
            case SELECTION:
                Utilities.selectionSort(party, comp);
                break;
            case SE:
                Utilities.selectionSort(party,comp);
                break;
            case INSERTION:
                Utilities.insertionSort(party, comp);
                break;
          
        }
        for(Party p : party)
        {   
            result += p;
            result = result.concat("\r\n******************************\r\n");
            
        }       
        return result;
    }
    /**
     * Method to write parties's status into a text file
     * throws: PartyPlannerException if it has some exception during the time it writes
     */
    public void writeToFile(String fileName) throws PartyPlannerException
    {
        FileIO file = null;
        try
        {
            file = new FileIO(fileName, FileIO.FOR_WRITING);
            file.writeLine(getState());
        }
        catch(FileIOException e)
        {
            throw new PartyPlannerException("Problem writing the file " + fileName);
        }
        finally
        {
            try
            {
                file.close();
            }
            catch(FileIOException e)
            {
                throw new PartyPlannerException("File " + fileName + " cannot be closed.");
            }
        }
    }
    /**
    * Method to write parties's status into a object file
    * throws: PartyPlannerException if it has some exception during the time it writes
    */
    public void writeToObjectFile(String fileName) throws PartyPlannerException
    {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try
        {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(planner);
            oos.writeObject(theParty);
        }
        catch(IOException e)
        {
            throw new PartyPlannerException("Can't write Object information " + fileName + " successfully" );
        }
        finally
        {
            try
            {
                oos.close();
            }
            catch(IOException e)
            {
                throw new PartyPlannerException("File " + fileName + " can't be close");
            }
        }
    }
    /**
     * Method to get Max guests 
     * Precondition: not null
     * Post condition return the max number of guests in the party
     */
    public int getMaxGuests(String name, String date)
    {
        int maxGuest =0;
        Party party = findParty(name,date);
        if (party != null)
        {
            maxGuest = party.getMaxGuests();
        }
        return maxGuest;
    }
    /**
     * takeAccept method
     * Return true if a person is accepted, otherwise if a person is not accepted or party is null, return false
     */
    public boolean takeAccept(String who, String partyName, String date)
    {
     
        Party party = findParty(partyName,date);
        if(party != null)
            return party.takeAccept(who);
        else
            return false;
    }
    /**
     * takeRegret method
     * Return false if the party is null or none regrets, otherwise, return true.
     */
    public boolean takeRegret(String who, String partyName, String date)
    {
       Party party = findParty(partyName,date);
       if(party != null)
            return party.takeRegret(who);
        else       
            return false;
    }
}
