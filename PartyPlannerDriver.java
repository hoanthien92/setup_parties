
/**
 * This class is a driver of party program which is used to process input from users.
 * 
 * @author Thien Lai 
 * @Date 10/25/2014
 */
public class PartyPlannerDriver
{
    private static PartyPlanner pp = new PartyPlanner();
    private Party party;
    /*
     * Main Method.
     */
    public static void main(String[] args)
    {
        boolean quit = false;
        try{
            if(args.length == 0)
                pp = new PartyPlanner();
            else if(args.length == 1)
                pp = new PartyPlanner(args[0]);
            else
                pp = new PartyPlanner(args[0], Boolean.parseBoolean(args[1]));
        }
        catch (PartyPlannerException e)
        {
            System.out.println(e);
        }
        System.out.println("Welcome to Party Planner");
        do
        {
            printMenu();
            String str = Keyboard.readString("Enter your choice: ");
            if(str.equals(""))
                continue;
            char choice = Character.toUpperCase(str.charAt(0));
            System.out.println("");
            switch (choice)
            {
                case 'S':
                    partyStatus();
                    break;
                case '+':
                    processAdd();
                    break;
                case 'N':
                    changePlanner();
                    break;
                case 'I':
                    inviteParty();
                    break;
                case '?':
                    whoInvited();
                    break;
                case 'A':
                    accept();
                    break;
                case 'R':
                    regret();
                    break;
                case 'P':
                    priceAdjust();
                    break;  
                case '$':
                    pay();
                    break;
                case 'D':
                    paidParty();
                    break;
                case 'U':
                    unpaidParty();
                    break;              
                case '#':
                    partyNumber();
                    break;
                case 'W':
                    writeToFile();
                    break; 
                case 'V':
                    averageCost(); //Test
                    break;
                case '*':
                    sort();
                    break;
                case 'Q':
                    System.out.println("Quiting...");
                    quit = true;
                    break;
                default:
                    System.out.println("You have entered invalid choice, Try again!");
                
            }
        }while(!quit);
    }
    /**
     * Displays the main menu.
     */
    private static void printMenu()
    {
        System.out.println( "\r\n\tS for status, \r\n" +
                            "\tN for planner name change,\r\n" +
                            "\tI for invite,\r\n" +
                            "\t? for who,\r\n" +
                            "\tA for accept,\r\n" +
                            "\tR for regret,\r\n" +
                            "\tP for price adjustment,\r\n" +
                            "\t$ for pay,\r\n" +
                            "\tD for paid,\r\n" +
                            "\tU for unpaid,\r\n" +
                            "\tV for avg. cost per person,\r\n" +
                            "\t# for party numbers,\r\n" +
                            "\t+ for add party,\r\n" +
                            "\t* for sort,\r\n" +
                            "\tW for write to File,\r\n" +
                            "\tQ for quit:\r\n");
    }
    /**
     * Processes Add when '+' is entered by the user.
     */    
    private static void processAdd()
    {
        boolean success = false;
        String answer = Keyboard.readString("Please enter Y or y to add a party, anything else to return to Main Menu: ");
        while(answer.equalsIgnoreCase("y"))
        {
            System.out.println("Processing add...");
            String name = Keyboard.readString("Please enter the name of the party you want to add: ");
            String host = Keyboard.readString("Please enter the host of the party: ");
            String date = Keyboard.readString("Please enter the date(yy/mm/dd): ");
            String location = Keyboard.readString("Please enter the location of the party: ");
            int maxGuests = Keyboard.readInt("Please enter maximum number of Guests: ");
            int cost = Keyboard.readInt("Please enter the price: ");
            int isWhole = Keyboard.readInt("Is the price for the whole party? (1 for yes, anything else for no) "); 
            boolean perParty = false;
            if (isWhole==1)
                perParty = true;
            try
            {
                success = pp.addToParties(name,host,date,location,maxGuests,perParty, cost);
            }
            catch(PartyException e)
            {
                System.out.println(e);
            }
            catch(PartyPlannerException e)
            {
                System.out.println(e);
            }            
            if(success)
                System.out.println("Add successful!!!");
            else
                System.out.println("Add unsuccessful!!!");
            answer = Keyboard.readString("Please enter Y or y to add a party, anything else to return to Main Menu: ");    
        }
    }
    
    /**
    * Display the Party Status when users input S or s.
    */
    private static void partyStatus()
    {
        System.out.println(pp);
    }
    /**
    * Method to change planner of the Party when users input N or n
    */ 
    private static void changePlanner()
    {
        String newPlanner = Keyboard.readString("Please enter new Planner name: ");
        pp.setPlannerName(newPlanner);
    }
    /**
     * Method to invite a person to the Party when users input I or i
     * if the person is already invited, shows an error.
     * If the users want to invite to ALL parties, shows what parties and display true or false if I can invite 
     */
    private static void inviteParty()
    {
        boolean success = false;
        Party party;
        System.out.println("Processing invite.....");
        String who = Keyboard.readString("Who would you like to invite? ");
        int inviteAll = Keyboard.readInt("To all parties? <1 for yes, anything else for no>");
        if(inviteAll==1)
        {
            System.out.println(pp.inviteToAll(who));
        }
        else
        {
            String partyName = Keyboard.readString("What is the name of the Party? ");
            String date = Keyboard.readString("When is the party? ");
            if(pp.invite(who,partyName,date))
            {
                System.out.println("Adding " + who +  " to the party " + partyName + " on " + date + " successfully");                
            }
            else
            {
                System.out.println("Unsuccessfully Adding " + who +  " to the party " + partyName + " on " + date + " !!!");
            }
        
        }
    }
    /**
     * Methods to display who are invited in a specific party with Party Name and Date provided
     */
    private static void whoInvited()
    {
        String partyName = Keyboard.readString("What is the name of the Party? ");
        String date = Keyboard.readString("When is the party? ");        
        System.out.println(pp.getWhosInvited(partyName,date));
    }
    /**
     * Method to display the Average Cost of attendee if the Party is paid 
     * and have at least one 1 person attended
     */
    private static void averageCost()
    {
        System.out.println("Processing average cost per person...");
        System.out.println("The average cost per attendee is " + Currency.formatCurrency(pp.getAverageCostPerPerson()));
    }
    /**
     * Method to accept an invited person in a specific Party with Party Name and Date provided
     */
    private static void accept()
    {
        System.out.println("Processing Accept.........");
        String who = Keyboard.readString("Who is attending? ");
        String partyName = Keyboard.readString("What is the name of the Party? ");
        String date = Keyboard.readString("When is the party? ");
        if(pp.takeAccept(who,partyName,date))
        {
            System.out.println("Accept successful for " + who + " for party " + partyName + " on " + date);
        }
        else
            System.out.println(who + " not invited for party " + partyName + " on " + date);   
    }
    /**
     * Method to process who is not attending and show appropriate message.
     */
    private static void regret()
    {
        System.out.println("Processing Regret.........");
        String who = Keyboard.readString("Who is not attending? ");
        String partyName = Keyboard.readString("What is the name of the Party? ");
        String date = Keyboard.readString("When is the party? ");
        if(pp.takeRegret(who,partyName,date))
        {
            System.out.println("Regret successful for " + who + " for party " + partyName + " on " + date);
        }
        else
            System.out.println("Regret unsuccessful for " + who + " for party " + partyName + " on " + date);   
    
    }
    /**
     * Method to process price adjustment based on the percentage that users input
     */
    private static void priceAdjust()
    {
        System.out.println("Adjustment processing .........");
        int percent = Keyboard.readInt("Please enter the % to adjust the price: ");
        String partyName = Keyboard.readString("What is the name of the Party? ");
        String date = Keyboard.readString("When is the party? ");
        if(pp.updatePrice(partyName,date,percent))
            System.out.println("Price was adjusted.");
        else
            System.out.println("Price was NOT adjusted.");    
    }
    /**
     * Method to process Party Payment
     */
    private static void pay()
    {
        System.out.println("Payment processing .........");     
        String partyName = Keyboard.readString("What is the name of the Party? ");
        String date = Keyboard.readString("When is the party? ");
        String isFull = Keyboard.readString("Pay in full? (Y for yes, otherwise for no) ");
        if(Character.toUpperCase(isFull.charAt(0)) == 'Y')
           System.out.println(pp.pay(partyName,date));
        else
        {
            int numMonth = Keyboard.readInt("# of Months? ");
            System.out.println(pp.pay(partyName,date,numMonth));
        }
    }
    /**
     * Method to process display of Paid Parties.
     */
    private static void paidParty()
    {
        System.out.println("Paid Parties: \r\n" + pp.getPaidParties() );
    }
    /**
     * Method to process display of Unpaid Parties
     */
    private static void unpaidParty()
    {
        System.out.println("Unpaid Parties: \r\n" + pp.getUnpaidParties());
    }
    /**
     * Method to process display numbers of Max Guest, Attending, Not Attending and Unknown
     */
    private static void partyNumber()
    {
        String answer = Keyboard.readString("Please enter M for max. guests, A for # attending, N for # not attending, and U for # unknown: ");
        switch(Character.toUpperCase(answer.charAt(0)))
        {
            case 'M':
                String partyName = Keyboard.readString("What is the name of the Party? ");
                String date = Keyboard.readString("When is the party? ");
                System.out.println("The max # of guests for party " + partyName + " on " 
                                    + date + " is " + pp.getMaxGuests(partyName,date));
                break;
            case 'A':
                partyName = Keyboard.readString("What is the name of the Party? ");
                date = Keyboard.readString("When is the party? ");
                System.out.println("The # of attending for party " + partyName + " on " 
                                    + date + " is " + pp.getNumAttending(partyName,date));
                break;
            case 'N':
                partyName = Keyboard.readString("What is the name of the Party? ");
                date = Keyboard.readString("When is the Party? ");
                System.out.println("The # of not regretter for party " + partyName + " on " 
                                    + date + " is " + pp.getNumNotAttending(partyName,date));
                break;
            case 'U':
                partyName = Keyboard.readString("What is the name of the Party? ");
                date = Keyboard.readString("When is the Party? ");
                System.out.println("The # of Unknown for party " + partyName + " on " 
                                    + date + " is " + pp.getNumUnknown(partyName,date));
                break;
        }
    }
    /**
     * Method to process Writing Parties to Object File or Text File
     */
   private static void writeToFile() 
   {
       
        System.out.println("Writer processing .........");
        String fileName = Keyboard.readString("Please enter the output File Name: ");
        String isObject = Keyboard.readString("Is it the Object File ((Y)es/(N)o)? ");
        if (isObject.equalsIgnoreCase("N"))
        {
            try{
                pp.writeToFile(fileName);
            }
            catch(PartyPlannerException e)
            {
                System.out.println(e);
            }
        }
        else if(isObject.equalsIgnoreCase("Y")) //Check if users want to write into Object or not
        {
            try{
                pp.writeToObjectFile(fileName);
                System.out.println("Write to Object File " + fileName + " successful");
            }
            catch(PartyPlannerException e)
            {
                System.out.println(e);
            }
        }
        else
        {
            System.out.println("Invalid choice for the file type to be written.");
        }
   }
   /**
    * Method to sort Parties based on Name, Date or Cost
    * Date will be in descending order while Name and Cost are sorted in ascending order
    */
   private static void sort()
   {
       System.out.println("Enter doSortWork");
       int field=0,alg = 0;
       do
       {
           System.out.println("\r\n1. host(asc) ");
           System.out.println("\r\n2. date(desc) ");
           System.out.println("\r\n3. cost(asc) ");
           field = Keyboard.readInt("\r\n Enter the sort field: ");
       }while(field < 1 || field >3 );
       do
       {
           System.out.println("\r\n1.Selection sort ");
           System.out.println("\r\n2. Insertion sort ");
           alg = Keyboard.readInt("\r\n Enter the Algorithm number: ");
           
       }while(alg <1 || alg >2);
       System.out.println(pp.sort(field,alg));
       System.out.println("\r\nLeaving doSortWork\r\n");
   }
}
