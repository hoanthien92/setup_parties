import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * PartyPlannerGUIDriver here.
 * Purpose: allow users to interact with driver graphically. Makes creating a party more interesting for users.
 * @author Thien Lai 
 * @11/24/2014
 */
public class PartyPlannerGUIDriver extends JFrame
{
    /** 
     * PartyPlanner instance variable 
     * purpose: to handle creating parties.
     */
    private PartyPlanner pp;
    /** GUI instance variable
     *  pnlButtons instance variable to handle Panel for Buttons
     *  pnlBottom instance variable to handle Panel at the Bottom.
     *  pnlRight instance variable to handle Panel Right
     *  pnlPP instance variable to handle Party Planner Panel.
     */
    private JPanel pnlButtons;
    private JPanel pnlBottom;
    private JPanel pnlRight;
    private JPanel pnlPP;
    private JTextArea infoArea; //TextAre Object to keep track of information input
    private JScrollPane taScroller; //Create scroll Panel object 
    /** Button Objects instance variable
     *  11 JButton instance variables (Status, Invite, Accept, Regret, Sort
     *  Help, add, Price, Who, Pay, Number, File) are created to handle parties.
     */
    private JButton btnStatus, btnInvite, btnAccept,btnRegret,
            btnSort, btnHelp, btnAdd, btnPriceAdjust, btnWho, btnPay,btnNumber,btnFile;
    /** Label Objects instance variable
     *  9 JLabel instance variables are created to handle user inputs on the right
     *  (Guess Name, PartyName, date, Host, Location, Price, Sort Field, Sort Algorithm)
     */        
    private JLabel  lblGuess, lblPartyName, lblDate,lblHost,
                    lblLocation,lblMax,lblPrice,lblSort,lblAlg;
    /** TextField Objects instance variable
     *  9 blank Text Fiels are created associated with 9 JLabel instance variables to handle inputs
     */
    private JTextField txtGuess, txtPartyName,txtDate,txtHost,
                       txtLocation, txtMax,txtPrice, txtSort,txtAlg;
    /** CheckBox  Objects instance variable
     *  4 JCheckBox instance variables are created to handle optional choices.
     *  (Per Party? invite all parties? Check for Write? or Use Object Files?
     */                   
    private JCheckBox   ckPer,ckAll,ckWrite,ckisObject;                                        
    
    /**
     * PartyPlannerGUIDriver Constructor to create sample parties by passing to another constructor
     * Precondition: file exists to be passed in
     * Postcondition: if file exists, pass it into another constructor to process creating parties.
     * Throws : PartyPlannerException if when creating PartyPlanner, it gets some exceptions
     */
    public PartyPlannerGUIDriver() throws PartyPlannerException
    {
        this("parties.txt", false);
    }
   
    /**
     * PartyPlannerGUIDriver Constructor to create GUI parties 
     * Precondition: file passed in correct
     * Postcondition: Create a GUI Panels and if fileName exists, create some parties. 
     * Catch PartyPlannerException if when creating PartyPlanner, it gets some exceptions.
     */
    public PartyPlannerGUIDriver(String fileName, boolean isObject) 
    {
        super("PartyPlannerGUIDriver");
        this.setLayout(new BorderLayout());
        infoArea = new JTextArea();
        taScroller = new JScrollPane(infoArea); //to make text area scrollable
        this.add(taScroller, BorderLayout.CENTER);
        pnlRight = new JPanel();
        pnlRight.setLayout(new GridLayout(1,1)); //create grid with 1 row 1 column
        initPlannerPanel();
        pnlRight.add(pnlPP);
        pnlBottom = new JPanel();
        pnlBottom.setLayout(new GridLayout(2, 1)); //grid with 2 rows, 1 column
        initButtons();
        pnlBottom.add(pnlButtons);
        this.add(pnlRight, BorderLayout.EAST);
        this.add(pnlBottom, BorderLayout.SOUTH);
        //Try-catch blocks to handle exceptions while creating parties from a sample file
        try{
            pp = new PartyPlanner(fileName,isObject);
        }
        catch(PartyPlannerException io)
        {
            infoArea.append("There is no Parties\n");
        }
        this.setSize(1200,480);//set the size
        this.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) { System.exit(0);}});

    }
    /** initPlannerPanel method
     * preCondition: null
     * PostCondition: Create initial Planner Panel with label Objects and text Object on the right
     * JLabel and JTextField objects are created in this initPlannerPanel to intereact with users
     * JCheckBox object is also created to provide more options to users while they handle managing parties.
     */
    private void initPlannerPanel()
    {
        pnlPP = new JPanel();
        pnlPP.setLayout(new GridLayout(11,2));
        lblGuess = new JLabel("Guess Name ");
        txtGuess = new JTextField();
        lblPartyName = new JLabel("Party Name ");
        txtPartyName = new JTextField();
        lblDate = new JLabel("Date ");
        txtDate = new JTextField();
        lblHost = new JLabel("Host ");
        txtHost = new JTextField();
        lblLocation = new JLabel("Location ");
        txtLocation = new JTextField();
        lblMax = new JLabel("Max Guests ");
        txtMax = new JTextField();
        lblPrice = new JLabel("Price/%/# Months ");
        txtPrice = new JTextField();
        lblSort = new JLabel("Sort Field ");
        txtSort = new JTextField();
        lblAlg = new JLabel("Sort Algorithm ");
        txtAlg = new JTextField();
        ckPer = new JCheckBox("Per Party? ");
        ckAll = new JCheckBox("All Party?");
        ckWrite = new JCheckBox("Check for Write ");
        ckisObject = new JCheckBox("Use Object File? ");
        /**  Start adding each objects to the Panel. The 
         *   The objects are not shown until we add the codes below.
         */
        pnlPP.add(lblGuess);
        pnlPP.add(txtGuess);
        pnlPP.add(lblPartyName);
        pnlPP.add(txtPartyName);
        pnlPP.add(lblDate);
        pnlPP.add(txtDate);
        pnlPP.add(lblHost);
        pnlPP.add(txtHost);
        pnlPP.add(lblLocation);
        pnlPP.add(txtLocation);
        pnlPP.add(lblMax);
        pnlPP.add(txtMax);
        pnlPP.add(lblPrice);
        pnlPP.add(txtPrice);
        pnlPP.add(lblSort);
        pnlPP.add(txtSort);
        pnlPP.add(lblAlg);
        pnlPP.add(txtAlg);
        pnlPP.add(ckPer);
        pnlPP.add(ckAll);
        pnlPP.add(ckWrite);
        pnlPP.add(ckisObject);
    }
    /**
     * initButtons method
     * Precondition: null
     * Postcondition: create initial Buttons Panel with all buttons at the bottom of main Panel
     * JPanel, JButton objects are created. 
     * Each Button Object has their own Listener Classes.
     */
    
    public void initButtons()
    {
        /**
         * Status Button Object
         * Create a status button object and create its StatusListener class
         */
        pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout());
        btnStatus = new JButton("Status");
        btnStatus.addActionListener(new StatusListener());
        pnlButtons.add(btnStatus);
        
        
        /**
         * Invite Button Object
         * Create a invite button object and create its InviteListener class
         */
        btnInvite = new JButton("Invite");
        btnInvite.addActionListener(new InviteListener());
        pnlButtons.add(btnInvite);

        /**
         * Accept Button Object
         * Create a accept button object and create its AcceptListener class
         */        
        btnAccept = new JButton("Accept");
        btnAccept.addActionListener(new AcceptListener());
        pnlButtons.add(btnAccept);
        
        
        /**
         * Regret Button Object
         * Create a Regret button object and create its RegretListener class
         */
        btnRegret = new JButton("Regret");
        btnRegret.addActionListener(new RegretListener());
        pnlButtons.add(btnRegret);
        
        
        /**
         * Sort Button Object
         * Create a Sort button object and create its SortListener class
         */
        btnSort = new JButton("Sort");
        btnSort.addActionListener(new SortListener());
        pnlButtons.add(btnSort);
        
        
        /**
         * Help Button Object
         * Create a Help button object and create its HelpListener class
         */
        btnHelp = new JButton("Help");
        btnHelp.addActionListener(new HelpListener());
        pnlButtons.add(btnHelp);
        
        
        /**
         * Add Button Object
         * Create a Add button object and create its AddListener class
         */
        btnAdd = new JButton("+");
        btnAdd.addActionListener(new AddListener());
        pnlButtons.add(btnAdd);
        
        
        /**
         * Price Button Object
         * Create a Price button object and create its priceAdjustListener class
         */
        btnPriceAdjust = new JButton("Prices");
        btnPriceAdjust.addActionListener(new priceAdjustListener());
        pnlButtons.add(btnPriceAdjust);
        
        
        /**
         * Who Button Object
         * Create a who button object and create its WhoListener class
         */
        btnWho = new JButton("Who");
        btnWho.addActionListener(new WhoListener());
        pnlButtons.add(btnWho);
        
        
        /**
         * Pay Button Object
         * Create a Pay button objects and create its PayListener class
         */
        btnPay = new JButton("Pay");
        btnPay.addActionListener(new PayListener());
        pnlButtons.add(btnPay);
        
        
        /**
         * Guest Button Object
         * Create a Guest button objects and create its GuestListener class
         */
        btnNumber = new JButton("#Guests");
        btnNumber.addActionListener(new GuestListener());
        pnlButtons.add(btnNumber);
        
        
        /**
         * File Button Object
         * Create a File button objects and create its FileListener class
         */
        btnFile = new JButton("File");
        btnFile.addActionListener(new FileListener());
        pnlButtons.add(btnFile);
    }
    /**
     * FileListener method implements ActionListener interface
     * Precondition: File Button is clicked
     * PostCondition: process read or write object or text files.
     * catch PartyPlannerException if it has some errors while reading or writing on object or text files.
     */
    private class FileListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            boolean useObject = ckisObject.isSelected();
            boolean isObject;
            boolean isWrite = ckWrite.isSelected();
            JFrame frame = new JFrame("File Dialog");
            //If the "Check for Write" is checked
            if (isWrite)
            {
                infoArea.append("\nWriting information to a file.");
                if(useObject) //If the "use Object" check box is checked
                {
                    try{
                        String fileName = JOptionPane.showInputDialog(frame,"Please enter a file name: ","Output File",JOptionPane.QUESTION_MESSAGE);
                        pp.writeToObjectFile(fileName);                
                        infoArea.append("\nWrote to " + fileName + " successfully.");
                        infoArea.append("\n " + fileName + " is an object.");
                    }
                    catch(PartyPlannerException pe) //Catch PartyPlannerException if it has some problem writing the file
                    {
                        JOptionPane.showMessageDialog(frame, "Problem writing information to file.","Problem with the file.", JOptionPane.INFORMATION_MESSAGE);                    
                    }
                }
                else
                {
                    try{
                        String fileName = JOptionPane.showInputDialog(frame,"Please enter a file name: ","Output File",JOptionPane.QUESTION_MESSAGE);                
                        pp.writeToFile(fileName);
                        infoArea.append("\nWrote to " + fileName + " successfully.");
                    }
                    catch(PartyPlannerException pe)
                    {
                        JOptionPane.showMessageDialog(frame, "File not found.","Problem with the file.", JOptionPane.INFORMATION_MESSAGE);                    
                    }
                }
            }
            else //If users want to read the file. (Check for write is not checked)
            {
                infoArea.append("\nReading from File");
                if(useObject)
                {
                    try{
                        String fileName = JOptionPane.showInputDialog(frame,"Please input file name: ", "Input File", JOptionPane.QUESTION_MESSAGE);
                        pp = new PartyPlanner(fileName,true);
                        infoArea.append("\nRead from " + fileName +  " successfully.");
                        infoArea.append("\n " + fileName + " is an object.");
                    }
                    catch(PartyPlannerException pe)
                    {
                        JOptionPane.showMessageDialog(frame, "File not found.","Problem with the file.", JOptionPane.INFORMATION_MESSAGE);                   
                    }
                }
                else
                {
                    try{
                        String fileName = JOptionPane.showInputDialog(frame,"Please input file name: ", "Input File", JOptionPane.QUESTION_MESSAGE);   
                        pp = new PartyPlanner(fileName, false);
                        infoArea.append("\nRead from " + fileName +  " successfully.");
                    }
                    catch(PartyPlannerException pe)
                    {
                        JOptionPane.showMessageDialog(frame, "File not found.","Problem with the file.", JOptionPane.INFORMATION_MESSAGE);                   
                    }
                }
            }
        }
    }
    /**
     * GuestListener method implements ActionListener interface
     * Precondition: #Guests Button is clicked and PartyName & Date Text Fields are filled
     * Postcondition: Display information of # of guests, attendees, regretters and unknowns
     */
    private class GuestListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            infoArea.append("\nDetermining numbers of guests");
            String what,when;
            what = txtPartyName.getText();
            when = txtDate.getText();
            //If either Party Name or Date Text Fields are missing, display error msg
            if(what.equals("") || when.equals("")){
                infoArea.append("\n Please enter a non-blank party name and date.");
            }
            else // If all inputs are valid
            {
                infoArea.append("\nThe max # of guests for party " + what + " on " + when + " is " + pp.getMaxGuests(what, when));
                infoArea.append("\nThe # of attendees for party " + what + " on " + when + " is " + pp.getNumAttending(what, when));
                infoArea.append("\nThe max # of regretters for party " + what + " on " + when + " is " + pp.getNumNotAttending(what, when));
                infoArea.append("\nThe max # of Unknowns for party " + what + " on " + when + " is " + pp.getNumUnknown(what, when));                
                clearAllTextFields();
            }
        }
    }
    /**
     * PayListener method implements ActionListener interface
     * Precondition: Pay Button is clicked and PartyName and date TextFields are filled
     * Postcondition: process the payment to pay for the specific party
     * catch NumberFormatException when invalid value is input for price Text Field.
     */
    private class PayListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            infoArea.append("\nProcessing Payment....");
            String what,when;
            int numMonth;
            what = txtPartyName.getText();
            when = txtDate.getText();
            
            if(what.equals("") || when.equals(""))
                    infoArea.append("\nPlease enter a non-blank party name and date.");                           
            else{
                //If the Text Price Field is not filled then put default value 1 for numMonth
                if(txtPrice.getText().equals("")){
                    numMonth=1;
                    infoArea.append("\n"+pp.pay(what, when,numMonth));
                }
                else{
                    //Using try-catch blocks to handle invalid input for Number of Months
                    try
                    {
                        numMonth = Integer.parseInt(txtPrice.getText());
                    }
                    catch(NumberFormatException nfe)
                    {
                        infoArea.append("\nInvalid number of months entered.");
                        txtPrice.setText("");                     
                    }
                    numMonth=Integer.parseInt(txtPrice.getText());
                    infoArea.append("\n"+pp.pay(what, when,numMonth));
                }
                clearAllTextFields();
            }
                
        }
    }
    /**
     * WhoListener Method implements ActionListener interface
     * Precondition: Who button is clicked and PartyName and Date Text Fields are filled
     * PostCondition: Display who is invited for a specific party then clear all the Text Fields.
     */
    private class WhoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            infoArea.append("\nProcess who's invited....");
            String what, when;
            what = txtPartyName.getText();
            when = txtDate.getText();
            if(what.equals("") || when.equals(""))
                infoArea.append("\nPlease enter a non-blank party name and date.");
            else{
                infoArea.append("\n" + pp.getWhosInvited(what, when));
                clearAllTextFields();
            }
        }
    }
    /**
     * priceAdjustListener method implements ActionListener interface
     * Precondition: the price TextField is filled and PartyName and Date Text Fields are not blank
     * Postcondition: adjust the price of a party based on %
     * catch NumberFormatException for invalid values input for price TextField.
     */
    private class priceAdjustListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {        
            infoArea.append("\nProcessing price adjustment...");
            int price;
            boolean success;
            String what, when;
            what = txtPartyName.getText();
            when = txtDate.getText();   
            try
            {
                price = Integer.parseInt(txtPrice.getText());
            }
            catch(NumberFormatException nfe)
            {
                infoArea.append("\nInvalid % entered.");
                txtPrice.setText("");                
            }
            //If the price TextField is not blank check if partyname and date Text Fields condition
            if(!txtPrice.getText().equals(""))
            {
                if(what.equals("") || when.equals(""))
                    infoArea.append("\nPlease enter a non-blank party name and date.");
                else
                {
                    price = Integer.parseInt(txtPrice.getText());
                    success = pp.updatePrice(what,when,price);
                    if(success)
                    {
                        infoArea.append("\nThe price was adjusted.");
                        clearAllTextFields();
                    }
                    else{
                        infoArea.append("\nThe price was NOT adjusted.");
                        clearAllTextFields();
                    }
                }
            }
                
        }
    }
    /**
     * SortListener method implements ActionListener
     * Precondition: Sort Button is clicked
     * Postcondition: if sort Field and Algorithm are blank then default values for both are 1.
     */
    private class SortListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {         
            infoArea.append("\nAttempting to sort....");
            int sortField;
            int sortAlg;
            //If the sort Text Field is blank or bigger than 5 or negative, set default value to 1
            if(txtSort.getText().equals("") ||  Integer.parseInt(txtSort.getText()) >5 || Integer.parseInt(txtSort.getText()) < 1 )
                sortField = 1;
            else
                sortField = Integer.parseInt(txtSort.getText());
            //If the Algorithm Text Field is blank, bigger than 3 or negative, set default value to 1    
            if(txtAlg.getText().equals("") || Integer.parseInt(txtAlg.getText()) > 3 || Integer.parseInt(txtAlg.getText()) < 1)
                sortAlg = 1;
            else
                sortAlg = Integer.parseInt(txtAlg.getText());
            String result = pp.sort(sortField,sortAlg);
            infoArea.append("\nSort results sorting on field " + sortField + " \nusing sorting " + sortAlg + " \nCompleted sort.");
            infoArea.append("\n " + result);
            clearAllTextFields();
        }
    }
    /**
     * AddListener method implements ActionListener interface
     * Precondition: Add Button is clicked and PartyName,Date,Host,Location Text Fields are filled.
     * Postcondition: Create a party if input information is correct
     * Catch NumberFormatException for price and guest Text Fields if invalid inputs are used.
     * catch PartyPlannerException if it has duplicated party or other errors.
     */
    private class AddListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {         
            infoArea.append("\nAttempting to add a party.");
            String what,when,where,host;
            int max;
            boolean perParty=ckPer.isSelected(),success;
            double price;
            
            what = txtPartyName.getText();
            when = txtDate.getText();
            where= txtLocation.getText();
            host = txtHost.getText();
            if(what.equals("") || when.equals("") || where.equals("") || host.equals(""))
            {
                infoArea.append("\nPlease enter a non-blank party name, host name, date, and location");
            }
            else {
                try
                {
                    max = Integer.parseInt(txtMax.getText());
                }
                catch(NumberFormatException nfe)
                {
                    infoArea.append("\nPlease enter a whole number for the maximum # of guests.");
                    txtMax.setText("");
                }            
                try
                {
                    price = Double.parseDouble(txtPrice.getText());
                }
                catch(NumberFormatException nfe)
                {
                    infoArea.append("\nPlease enter a number for price.");
                    txtPrice.setText("");
                }            
            }
            
            max = Integer.parseInt(txtMax.getText());
            price = Double.parseDouble(txtPrice.getText());
            if(perParty=ckPer.isSelected())
                perParty = true;
            else
                perParty = false;
            //if all input are valid
            if(!what.equals("")&& !when.equals("") && !where.equals("") && !host.equals("")&& !txtMax.getText().equals("") && !txtPrice.getText().equals("")){    
                try
                {
                    success = pp.addToParties(what,host,when,where,max,perParty,price);
                    if(success)
                    {
                        infoArea.append("\nParty added successfully");
                    }
                    else
                    {
                        infoArea.append("\nParty not added.");
                    }
                }
                catch(PartyPlannerException E)
                {
                    infoArea.append("\n" + E.getMessage());
                    infoArea.append("Party not added.");
                }
            }
            clearAllTextFields();
            ckPer.setSelected(false);
        }
    }
    /**
     * HelpListener method implements ActionListener interface
     * preCondition: Help Button is clicked
     * PostCondition: display information to help users understand how Sort Button works.
     */
    private class HelpListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            infoArea.append("\n1. host(asc)\n2.date(desc)\n3. cost(asc)\nSort Algorithm: \n1.Selection Sort\n2.Insertion sort");
        }
    }
    /**
     * StatusListener method implements ActionListener Interface
     * precondition: Status button is clicked.
     * Postcondition: print status of parties.
     */
    private class StatusListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {         
            infoArea.append("\nThe status of the Parties: \n");
            infoArea.append(pp.toString() + "\n");
        }
    }
    /**
     * InviteListener method implements ActionListener interface
     * Precondition: invite Button is clicked and thre are no blank fields in Guess Name, Party name and date Text Fields.
     * Postcondition: invite a person into all parties if ckAll check box is selected regarless of party name and date.
     *                if ckall check box isn't selected, invite a person into a specific party and display whether they are invited or not
     *                (if a person is already invited, he/she can't be invited again).
     */
    private class InviteListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            infoArea.append("\nProcessing invite...");
            String what, when, who;
            boolean inviteAll = ckAll.isSelected();
            what = txtPartyName.getText();
            when = txtDate.getText();
            who = txtGuess.getText();
            
            if(who.equals(""))
                infoArea.append("\nPlease enter a non-blank Guess name.");       
            if(!who.equals(""))
            {  
                if(inviteAll){
                    infoArea.append("\n" + pp.inviteToAll(who));
                    clearAllTextFields();
                    ckAll.setSelected(false);
                }
                else if( what.equals("") || when.equals(""))
                    infoArea.append("\nPlease enter a non-blank Party name and Date.");       
            }
            if(!who.equals("") && !when.equals("") && !what.equals("")){
                if(pp.invite(who,what,when))
                {
                    infoArea.append("\n"+ who + " invited to party " + what + " on " + when);
                    clearAllTextFields();
                }
                else
                {
                    infoArea.append("\n"+ who + " not invited to party " + what + " on " + when);
                    clearAllTextFields();
                }
            }
         
        }
    }
    /**
     * AcceptListener method implements ActionListener interface
     * precondition: Accept button is clicked and guess name, party name and date Text Fields are not blank
     * postcondition: Accept an invited person for a specific party and display whether it's successful or not.
     */
    private class AcceptListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {         
            infoArea.append("\nProcessing Accepting....");
            String who, what, when;
            who = txtGuess.getText();
            what = txtPartyName.getText();
            when = txtDate.getText();
            //If any Fields are missing, display error
            if(who.equals("") || when.equals("") || what.equals(""))
                infoArea.append("\nPlease enter a non-blank Guess name, party name and date.");           
            //if all input are valid
            if(!who.equals("") && !when.equals("") && !what.equals("")){
                if(pp.takeAccept(who,what,when)){
                    infoArea.append("\nAccept successful for " + who + " for party " + what +
                               " on " + when);
                    clearAllTextFields();           
                }
                else
                {
                    infoArea.append("\nAccept unsuccessful for " + who + " for party " + what +
                               " on " + when);
                    clearAllTextFields();           
                }
                               
            }
        }
    }
    /**
     * RegretListener method implements ActionListener interface
     * precondition: Regret button is clicked and Guess name, Party name and date Text Fields are not blank
     * Postcondition: regret an invited person and display whether it's successful regreting or not.
     */
    private class RegretListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String who,what,when;
            who = txtGuess.getText();
            what = txtPartyName.getText();
            when = txtDate.getText();
            //If any Fields are missing, display error msg
            if(who.equals("")||when.equals("") || what.equals(""))
                infoArea.append("\nPlease enter a non-blank Guess name, party name and date."); 
            //if all inputs are valid
            if(!who.equals("") && !when.equals("") && !what.equals("")){ 
                if(pp.takeRegret(who,what,when)){
                    infoArea.append("\nRegret successful for " + who + " for party " + what +
                               " on " + when);
                    clearAllTextFields();           
                }
                else
                {
                    infoArea.append("\nRegret unsuccessful for " + who + " for party " + what +
                               " on " + when);
                    clearAllTextFields();                                          
                }
                               
            }
        }
        
    }
    /**
     * clearAllTextFields method
     * precondition: null
     * Postcondition: clear all text Fields
     */
    private void clearAllTextFields()
    {
        txtGuess.setText("");
        txtPartyName.setText("");
        txtDate.setText("");
        txtHost.setText("");
        txtLocation.setText("");
        txtMax.setText("");
        txtPrice.setText("");
        txtSort.setText("");
        txtAlg.setText("");
       
    }
    /**
     * Main Method
     * Precondition: Depends on how many arguments are input in order to handle files in arguments of line commands. 
     * Postcondition: Create party based on arguments in command line.
     * 1) Create parties based on Object files if arguments are     File true
     * 2) Create parties based on Text file if arguments are    File false
     * 3) Create parties based on sample file.
     * Catch PartyPlannerException to handle exceptions while creating parties.
     */
    public static void main(String[] args)
    {
        PartyPlannerGUIDriver gui;
        try
        {
            if (args.length >= 2)
            {
                gui = new PartyPlannerGUIDriver(args[0], Boolean.parseBoolean(args[1]));
                
            }
            else if (args.length == 1)
            {
                gui = new PartyPlannerGUIDriver(args[0], false);
                
            }
            else
            {
                gui = new PartyPlannerGUIDriver();
                
            }
            gui.setVisible(true);
        }
        catch (PartyPlannerException e)
        {
            System.out.println("Problem creating PartyPlanner - exiting program");
            return;
        }    
    }
}
