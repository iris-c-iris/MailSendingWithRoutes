
/**
 * manipulates data about letters/packages needing to be loaded on 3 new mail coaches
 * @author (Iris Carrigg)
 * @version (9/28/2022)
 * 
 */
public class MailCoach
{
    private String route;
    private int capacity;
    private int letters;
    private int packages;
    private boolean dispatched;

    /**
     * standard constructor for objects of class MailCoach
     * initializes instance variables, creates dummy object with no data
     * @param no parameters
     * @return nothing
     */
    public MailCoach()
    {
        this.route = "";
        this.capacity = 0;
        this.letters = 0;
        this.packages = 0;
        this.dispatched = false;
    }

    /**
     * constructor for objects of class MailCoach
     * intializes, sets instance variables
     * @param String route & int capacity
     * @return nothing
     */
    public MailCoach(String route, int capacity)
    {
        this.route = route;
        this.capacity = capacity;
        this.letters = 0;
        this.packages = 0;
        this.dispatched = false;

    }

    /**
     * gets the value of variable this.route 
     * @param no parameters
     * @return String
     */
    public String getRoute()
    {
        return this.route;
    }

    /**
     * gets the value of variable this.capacity
     * @param no parameters
     * @return int
     */
    public int getCapacity()
    {
        return this.capacity;
    }

    /**
     * gets the value of variable this.letters
     * @param no parameters
     * @return int
     */
    public int getLetters()
    {
        return this.letters;
    }

    /**
     * gets the value of variable this.packages
     * @param no parameters
     * @return int
     */
    public int getPackages()
    {
        return this.packages;
    }

    /**
     * gets the value of variable this.dispatched 
     * @param no parameters
     * @return boolean
     */
    public boolean getDispatched()
    {
        return this.dispatched;
    }

    /**
     * tries to accommodate the letters the user is asking to ship
     * @param int lettersToLoad
     * @return int lettersNotLoaded
     */
    public int setLetters(int lettersToLoad)
    {
        int lettersNotLoaded = 0;
        int lettersLoaded = 0;
        for(int i = 1; i <= lettersToLoad; i++)
        {
            if(this.capacity - 1 >= 0)
            {
                lettersLoaded += 1;
                this.capacity -= 1;                    
            }        
        }
        
        this.letters += lettersLoaded;
        lettersNotLoaded = lettersToLoad - lettersLoaded;

        if(this.capacity == 0)
            this.dispatched = true;
            
        return lettersNotLoaded;
    }

    /**
     * tries to accommodate the packages the user is asking to ship
     * @param int packagesToLoad
     * @return int packagesNotLoaded
     */
    public int setPackages(int packagesToLoad)
    {
        int packagesNotLoaded = 0;
        int packagesLoaded = 0;
        for(int i = 1; i <= packagesToLoad; i++)
        {
            if(this.capacity - 20 >= 0)
            {
                packagesLoaded += 1;
                this.capacity -= 20;  
            }
        }
        
        this.packages += packagesLoaded;
        packagesNotLoaded = packagesToLoad - packagesLoaded;

        if(this.capacity == 0)
            this.dispatched = true;
            
        return packagesNotLoaded;
    }

}
