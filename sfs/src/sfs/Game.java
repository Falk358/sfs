/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;

import sfs.map.Direction;
import sfs.map.Tile;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.NaturalCLI;
import org.naturalcli.ParseResult;

import sfs.encounters.Encounters;
import sfs.entities.Player;
import sfs.items.Item;

/**
 *
 * @author Max
 */
public class Game {
    
    private Set<Command> cs = new HashSet<Command>();
    
    private Player player;
    private String commandlist=""; // initialized after commands have been created and added to collection
    
    public Game(Player player)
    {
        this.player=player;
        Command goCommand, inspectCommand, pickUpCommand, inventoryCommand, weapons, usables, helpCommand;
        try
        {
            /*initialize Commands with help messages and functionality and add them to the set of commands*/
             goCommand=new Command("go <direction:string>", "command to go to tile located in direction (has to be all caps) NORTH, SOUTH, EAST, WEST", 
                new ICommandExecutor ()
                {
                  // the code to be executed when command is entered
                  public void execute(ParseResult pr ) 
                  { 
                      // retrieve the parameter of the "go" command 
                      String result =(String) pr.getParameterValue(0);
                      //enum to buffer direction
                      Direction mydir;
                      try
                      {
                          //try to convert string to the corresponding enumeration value
                          mydir=Direction.valueOf(result);
                      }
                      catch (IllegalArgumentException e)
                      {
                          // tell the user the argument was faulty
                          System.err.println("parameter incorrect. please use one of the following: NORTH, SOUTH, EAST, WEST");
                          return;
                      }
                      // if changing the room was successfull, tell the user
                      if (player.changeRoom(mydir))
                          System.out.println("going " + result);
                  }
                });
             inspectCommand=new Command("inspect", "command to inspect Tile the player is on, returns information on",
                     new ICommandExecutor ()
                     {
                         public void execute(ParseResult pr)
                         {
                             //creation of buffer Tile variable which saves the details of current tile and then uses the printRoomInfo command to display room info
                             Tile myTile;
                             myTile=player.getLocation();
                             myTile.printRoomInfo();
                         }
                     });
             
             pickUpCommand = new Command("pickup", "command to pick up the first item from the tile the player is standing at.", 
            		 					r -> {
            		 						if( player.pickUpItem() )
            		 						{
            		 							/* the just picked up item. Short naming due to more readable next line. */
            		 							Item i = player.getLastItem();
            		 							System.out.println( "Successfully picked up " + i.getName() + "!\n" + i.getDescription() );
            		 						}
            		 						else
            		 						{
            		 							System.err.println( "There is no more item on this tile\n" );
            		 						}
            		 					} );
             
             inventoryCommand = new Command("inventory", "commnad to look at the items in your inventory", 
            		 r -> player.printInventoryItems()
            		 );
             
             weapons = new Command( "weapons", "shows all weapons in your inventory", 
 					r -> player.printWeaponsInInventory()
 			 );
 			
 			 usables = new Command( "usables", "shows all useables in your inventory",
 					r -> player.printUsablesInInventory()
 			 );
            
                     
             // add the command goCommand to the total list of commands
             Collections.addAll( cs, goCommand, inspectCommand, pickUpCommand, inventoryCommand, weapons, usables);
            for (Command command: cs)
            {
                commandlist= commandlist + command.getSyntax().toString()+ ", "; // fill commandlist with the names of commands
            }
        }
        catch (InvalidSyntaxException e)
        {
                e.printStackTrace();
        }
        /*try
        {
            helpCommand = new Command("help", "takes commandname as parameter, displays help message for the given command", 
                    new ICommandExecutor ()
                     {
                         public void execute(ParseResult pr)
                         {
                             String parameter = (String) pr.getParameterValue(0);
                            for (Command command: cs)
                            {
                                if (command.getSyntax().equals(parameter) || parameter.equals("go"))
                                {
                                    if (parameter.equals("go"))
                                        System.out.println(goCommand.getHelp());
                                    else
                                        System.out.println(command.getHelp());
                                }
                            }
                         }
                     });
            Collections.addAll(cs, helpCommand);
        }
        catch ()
        {
            
        }*/
   
    }
    
    //starts the game
    public void start()
    {
        // creates the executor and scanner to take input from command line an interpret it
        NaturalCLI natcli =new NaturalCLI(cs);
        Scanner scanner =new Scanner(System.in);
        
        
        System.out.println("Welcome to our text-based-adventure game. You have the following commands at your disposal: " + commandlist);
        while( player.getHealth() > 0 )
        {	
            // retrieve next argument entered by user
            String arg= scanner.nextLine();
            try
            {
                /*parse the argument and execute the correct code if defined in a Command*/
                natcli.execute(arg);
            }
            catch (ExecutionException e)
            {
                System.err.println("command not defined");
            }
            
            /* if the player has pending encounters we process them yet. */
        	if( Encounters.getNumberOfPendingEncounters() > 0 )
        		for( int i = 0; i < Encounters.getNumberOfPendingEncounters(); i++ )
        			Encounters.popFirstEncounter().start();
            
        }
    }
            
   
    
}
