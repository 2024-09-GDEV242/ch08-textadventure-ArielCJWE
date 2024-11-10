
/**
 * This class is to hold the items that can be found in various rooms
 * that way the player can reuse each item and then reuse it.
 *
 * @author Ariel Wong-Edwin
 * @version 11/05/24
 */
public class Items
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private double weight;

    /**
     * Constructor for objects of class Items
     */
    public Items(String name, String description, double weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return the name of the item
     */
    public String getName()
    {
        return name;
    }
    /**
     * @return the description of the item
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * @return the name of the item
     */
    public double getWeight()
    {
        return weight;
    }
    
    public String toString(){
        return name + ": " + description + " (Weight: " + weight + "kg)" ;
    }
}
