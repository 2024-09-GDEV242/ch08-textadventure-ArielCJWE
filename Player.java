import java.util.ArrayList;
import java.util.List;
/**
 * A player class to hold the inventory of a player.
 *
 * @author Ariel Wong-Edwin
 * @version 11.07.24
 */
public class Player
{
    // instance variables - replace the example below with your own
    private List<Items> inventorySpace;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        inventorySpace = new ArrayList<>();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void addItem(Items item)
    {
        // put your code here
        inventorySpace.add(item);
    }
    public void removeItem(Items item)
    {
        // put your code here
        inventorySpace.remove(item);
    }
    public boolean hasItem(String itemName){
        for (Items item : inventorySpace) {
            if (item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }
    
    public List<Items> getInventory() {
        return inventorySpace;
    }
    public void showInventory() {
        if(inventorySpace.isEmpty()) {
            System.out.println("You open your wallet and there are flies escaping");
        }
        else {
            System.out.println("Your inventory holds: ");
            for (Items item : inventorySpace){
                System.out.println(item.getName() + " - " + item.getDescription());
            }
        }
    }
}
