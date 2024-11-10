import java.util.ArrayList;
import java.util.List;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Ariel Wong-Edwin
 * @version 2024.11.07
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private List<Room> userRoomHistory;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
        userRoomHistory = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        //Adding items to the rooms
        outside.addItems(new Items("A bird's feather", "A bird must have dropped it!", 0.01));
        theater.addItems(new Items("A half burnt mask", "It looks like someone forgot it after a play", 0.2));
        pub.addItems(new Items("Half eaten basket of chicken wings", "It looks like someone forgot to eat the rest, Disgusting!", 0.5));
        lab.addItems(new Items("A broken vial of unknown substance", "Looks dangerous", 0.06));
        office.addItems(new Items("A bloodied axe", "It has dried blood on it", 3.0));

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case PICK:
                pickUpItems(command);
                break;
                
            case DROP:
                dropTheItems(command);
                break;
                
            case BACK:
                goBack(command);
                break;
            }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            userRoomHistory.add(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        
    }
    
    private void pickUpItems(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Huh? What is it you would like to pick up?");
            return;
        }
        String itemName = command.getSecondWord();
        List<Items> items = currentRoom.getItems();
        for (Items item : items) {
            player.addItem(item);
            currentRoom.removeItems(item);
            System.out.println("Oh! You've picked up:" + item);
            return;
        }
        
        System.out.println("The item you requested has not been found!");
    }
    private void dropTheItems(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("What!? What is it you would like to drop?");
            return;
        }
        String itemName = command.getSecondWord();
        if(!player.hasItem(itemName)) {
            System.out.println("You have nothing but air in your inventory");
            return;
        }
        for (Items item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)){
            player.removeItem(item);
            currentRoom.addItems(item);
            System.out.println("Oh No! You've dropped up:" + item);
            return;
        }
        }
        
        System.out.println("The item you requested is no longer in your inventory!");
    }
    
    private void goBack(Command command) {
        if (userRoomHistory.isEmpty()){
            System.out.println("You've only just started, there is no going back now!");
        }
        else{
            currentRoom = userRoomHistory.remove(userRoomHistory.size() - 1);
            System.out.println("You've retraced your steps and ended up in: " + currentRoom.getLongDescription());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
