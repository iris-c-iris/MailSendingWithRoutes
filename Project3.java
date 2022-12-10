/**
 * prints data reports about mail loaded on 3 new mail coaches
 * interacts with user to retrieve data about potential orders to be loaded
 * manipulates user input to determine order validity
 * @author (Iris Carrigg)
 * @version (9/28/2022)
 * 
 */
import java.util.Scanner;

public class Project3 {
    //DATE AND TIME YOU FIRST START WORKING ON THIS ASSIGNMENT (date AND time): <--------------------
    //ANSWER:  9/28/2022 3:15PM              <--------------------

    //DO NOT ALTER THE MAIN METHOD
    public static void main( String[] args ) {
        //use this method to test your entity class, comment it out when you've passed all tests
        //testEntityClass();

        MailCoach stolat = new MailCoach( "Stolat", 1200 );
        MailCoach borogravia = new MailCoach( "Borogravia", 750 );
        MailCoach quirm = new MailCoach( "Quirm", 366 );

        System.out.println( "Welcome to the Ankh-Morpork Post Office new routes mail coach dispatch!" );
        //print the initial menu
        printMenu( stolat, borogravia, quirm );
        //call go and load up as much mail as is possible
        go( stolat, borogravia, quirm );
        //print report when all mail coaches have been dispatched
        printReport( stolat, borogravia, quirm );
    } //DO NOT ALTER THE MAIN METHOD

    //DO NOT ALTER THE testEntityClass METHOD
    public static void testEntityClass() {
        MailCoach test1 = new MailCoach();

        assert test1.getRoute().equals( "" ) && 
        test1.getCapacity() + test1.getLetters() + test1.getPackages() == 0  && 
        !test1.getDispatched() : "standard constructor fail";

        MailCoach test2 = new MailCoach( "City", 134 );
        assert test2.getRoute().equals( "City" ) : "second constructor instance variable route not properly set";
        assert test2.getLetters() + test2.getPackages() == 0 : "second constructor instance variables letters and/or packages not properly set";
        assert test2.getCapacity() == 134 : "second constructor instance variables capacity not properly set";
        assert !test2.getDispatched() : "second constructor instance variables dispatched not properly set";

        assert test2.setLetters( 20 ) == 0 : "setLetters not correct return value";
        assert test2.getLetters() == 20 : "setLetters not correct updating letters loaded";
        assert test2.getCapacity() == 114 : "setLetters not correct updating capacity";
        assert test2.setLetters( 150 ) == 36 : "setLetters not correct return value";
        assert test2.getLetters() == 134 : "setLetters not correct updating letters loaded";
        assert test2.getCapacity() == 0 : "setLetters not correct updating capacity";
        assert test2.getDispatched() : "setLetters not correct updating dispatch status";

        MailCoach test3 = new MailCoach( "City", 134 );
        assert test3.setPackages( 2 ) == 0 : "setPackages not correct return value";
        assert test3.getPackages() == 2 : "setPackages not correct updating letters loaded";
        assert test3.getCapacity() == 94 : "setPackages not correct updating capacity";
        assert test3.setPackages( 10 ) == 6 : "setPackages not correct return value";
        assert test3.getPackages() == 6 : "setPackages not correct updating letters loaded";
        assert test3.getCapacity() == 14 : "setPackages not correct updating capacity";
        assert test3.setLetters( 15 ) == 1 : "setLetters not correct return value";
        assert test3.getCapacity() == 0 : "setPackages not correct updating capacity";
        assert test3.getDispatched() : "setPackages not correct updating dispatch status";

        System.out.print( "MailCoach class all tests passed.\n" );        
    } //DO NOT ALTER THE testEntityClass METHOD

    /**
     * interacts with user, collects and handles input about mail destination, type, and amount
     * @param 3 MailCoach objects
     * @return void
     */
    public static void go( MailCoach stolat, MailCoach borogravia, MailCoach quirm ) { 

        //interact with user while at least one coach is not dispatched
        while(stolat.getDispatched() == false || borogravia.getDispatched() == false || quirm.getDispatched() == false)
        {
            //declare & assign local variables
            int mailAmount = 0;
            String mailType = "";
            String mailAmountStr = "";

            //user inputs destination
            Scanner userInput = new Scanner(System.in);
            System.out.print("What is the destination? ");
            String destination = userInput.nextLine();
            destination = destination.trim().substring(0,1).toUpperCase() + destination.trim().substring(1).toLowerCase();

            //check if destination valid
            switch(destination)
            {
                case "Stolat":
                case "1":
                    if(stolat.getDispatched() == true)
                        System.out.printf("The mail coach Stolat has been dispatched. Try again tomorrow.%n%n");
                    else
                        destination = "Stolat";
                    break;
                case "Borogravia":
                case "2":
                    if(borogravia.getDispatched() == true)
                        System.out.printf("The mail coach Borogravia has been dispatched. Try again tomorrow.%n%n");
                    else
                        destination = "Borogravia";
                    break;
                case "Quirm":
                case "3":
                    if(quirm.getDispatched() == true)
                        System.out.printf("The mail coach Quirm has been dispatched. Try again tomorrow.%n%n");
                    else
                        destination = "Quirm";
                    break;
                default:
                    System.out.printf("We don't deliver mail to %s. Back of the line!%n%n", destination);
            }

            //if destination requested is valid, user inputs mail information (mailInfo)
            if(destination.equals("Stolat") || destination.equals("Borogravia") || destination.equals("Quirm"))
            {
                System.out.printf("What are you shipping to %s? ", destination);
                String mailInfo = userInput.nextLine();

                //iterate through mailInfo String to find digits and letters, builds mailAmountStr and mailType
                for(int i = 0; i < mailInfo.trim().length(); i++)
                {
                    if(Character.isDigit(mailInfo.trim().charAt(i)))
                    {
                        mailAmountStr = mailAmountStr.concat("" + mailInfo.trim().charAt(i));
                        //convert String to int using Integer class
                        mailAmount = Integer.parseInt(mailAmountStr);
                    }

                    if(Character.isLetter(mailInfo.trim().charAt(i)))
                        mailType = mailType.concat("" + mailInfo.trim().charAt(i));
                }

                //check for negative number input
                if(mailInfo.trim().charAt(0) == '-')
                {
                    mailAmountStr = "-" + mailAmountStr;
                    mailAmount = Integer.parseInt(mailAmountStr);
                }

                //check for lack of mailType, only mailAmount
                if(mailType.equals("") && mailAmount >= 0)
                    mailType = mailAmountStr;

                //with selected location and mailType, determine if there is room on the mail coach selected
                if(mailType.equals("package") || mailType.equals("letter") || mailType.equals("packages") || mailType.equals("letters"))
                {
                    switch(destination)
                    {
                        case "Stolat":
                            switch(mailType)
                            {
                                case "package":
                                case "packages":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(stolat.getCapacity() - 20 >= 0)
                                        loadUp(stolat, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d packages couldn't be dispatched today, bring them back tomorrow.%n%n", stolat.setPackages(mailAmount), mailAmount);
                                    break;
                                case "letter":
                                case "letters":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(stolat.getCapacity() - 1 >= 0)
                                        loadUp(stolat, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d letters couldn't be dispatched today, bring them back tomorrow.%n%n", stolat.setLetters(mailAmount), mailAmount);
                                    break;
                            }
                            break;
                        case "Borogravia":
                            switch(mailType)
                            {
                                case "package":
                                case "packages":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(borogravia.getCapacity() - 20 >= 0)
                                        loadUp(borogravia, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d packages couldn't be dispatched today, bring them back tomorrow.%n%n", borogravia.setPackages(mailAmount), mailAmount);
                                    break;
                                case "letter":
                                case "letters":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(borogravia.getCapacity() - 1 >= 0)
                                        loadUp(borogravia, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d letters couldn't be dispatched today, bring them back tomorrow.%n%n", borogravia.setLetters(mailAmount), mailAmount);
                                    break;
                            }
                            break;
                        case "Quirm":
                            switch(mailType)
                            {
                                case "package":
                                case "packages":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(quirm.getCapacity() - 20 >= 0)
                                        loadUp(quirm, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d packages couldn't be dispatched today, bring them back tomorrow.%n%n", quirm.setPackages(mailAmount), mailAmount);
                                    break;
                                case "letter":
                                case "letters":
                                    if(mailAmount <= 0)
                                        System.out.printf("This is not a valid amount. Back of the line!%n%n");
                                    else if(quirm.getCapacity() - 1 >= 0)
                                        loadUp(quirm, mailType, mailAmount);
                                    else
                                        System.out.printf("%d of your %d letters couldn't be dispatched today, bring them back tomorrow.%n%n", quirm.setLetters(mailAmount), mailAmount);
                                    break;
                            }
                    }
                }
                else
                    System.out.printf("We don't ship %s. Back of the line!%n%n", mailType);
            }

            //print menu if at least one coach is not dispatched
            if(stolat.getDispatched() == false || borogravia.getDispatched() == false || quirm.getDispatched() == false)
            {
                printMenu(stolat,borogravia,quirm);
            }
        }
    }

    /**
     * displays the 3 mail coach options with their destinations 
     * @param 3 MailCoach objects
     * @return void
     */    
    public static void printMenu( MailCoach stolat, MailCoach borogravia, MailCoach quirm ) 
    {
        System.out.println("Remaining mail coach capacity:");

        //check if Stolat is dispatched or not, print result
        if(stolat.getDispatched() == true)
            System.out.println("   Stolat - dispatched");
        if(stolat.getDispatched() == false)
            System.out.printf("   1. Stolat: remaining capacity %d%n", stolat.getCapacity());

        //check if Borogravia is dispatched or not, print result
        if(borogravia.getDispatched() == true)
            System.out.println("   Borogravia - dispatched");
        if(borogravia.getDispatched() == false)
            System.out.printf("   2. Borogravia: remaining capacity %d%n", borogravia.getCapacity());

        //check if Quirm is dispatched or not, print result
        if(quirm.getDispatched() == true)
            System.out.println("   Quirm - dispatched");
        if(quirm.getDispatched() == false)
            System.out.printf("   3. Quirm: remaining capacity %d%n", quirm.getCapacity());

    }

    /**
     * tells the user what has been loaded on the mail coach 
     * @param 1 MailCoach object, String type of mail to be loaded, int quantity of mail to be loaded
     * @return void
     */
    public static void loadUp( MailCoach d, String type, int quantity ) {

        //check if mail type is letter
        if(type.equals("letter") || type.equals("letters"))
        {
            //check if user is trying to load more than the coach capacity
            if(quantity > d.getCapacity())
                System.out.printf("%d of your %d letters couldn't be dispatched today, bring them back tomorrow.%n%n", d.setLetters(quantity), quantity);
            else
            {
                d.setLetters(quantity);
                if(quantity > 1 && !("" + type.charAt(type.length() - 1)).equals("s"))
                    type =  type.concat("s");
                System.out.printf("Your %d %s have been loaded for delivery.%n%n", quantity, type);
            }
        }

        //check if mail type is package
        else if(type.equals("package") || type.equals("packages"))
        {
            //check if user is trying to load more than the coach capacity
            if(quantity * 20 > d.getCapacity())
                System.out.printf("%d of your %d packages couldn't be dispatched today, bring them back tomorrow.%n%n", d.setPackages(quantity), quantity);
            else
            {
                d.setPackages(quantity);
                if(quantity > 1 && !("" + type.charAt(type.length() - 1)).equals("s"))
                    type =  type.concat("s");
                System.out.printf("Your %d %s have been loaded for delivery.%n%n", quantity, type);
            }

        }   

    }

    /**
     * prints the tally of everything loaded up on the mail coaches and dispatched to its destination 
     * @param 3 MailCoach objects
     * @return void
     */
    public static void printReport( MailCoach stolat, MailCoach borogravia, MailCoach quirm ) 
    {
        System.out.println();
        System.out.println("All mail coaches have been dispatched for the day.");
        System.out.printf("Dispatched: mail coach to %-10s %c %3d letters, %3d packages%nDispatched: mail coach to %-10s %c %3d letters, %3d packages%nDispatched: mail coach to %-10s %c %3d letters, %3d packages%n%nThank you for using the Ankh-Morpork Post Office. Goodbye!", stolat.getRoute(), '-',stolat.getLetters(), stolat.getPackages(), borogravia.getRoute(), '-', borogravia.getLetters(), borogravia.getPackages(), quirm.getRoute(), '-', quirm.getLetters(), quirm.getPackages());
        System.out.println();
    }
}
