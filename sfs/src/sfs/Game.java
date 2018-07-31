/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.NaturalCLI;
import org.naturalcli.ParseResult;

import sfs.entities.Player;
import sfs.items.Item;

/**
 *
 * @author Max
 */
public class Game {
    
    private Set<Command> cs = new HashSet<Command>();
    
    private Player player;
    
    public Game(Player player)
    {
        this.player=player;
        Command goCommand;
        Command inspectCommand;
        Command pickUpCommand;
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
             inspectCommand=new Command("inspect", "command to inspect Tile the player is on",
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
                     
             // add the command goCommand to the total list of commands
             cs.add(goCommand);
             cs.add(inspectCommand);
             cs.add( pickUpCommand );
        }
        catch (InvalidSyntaxException e)
        {
                e.printStackTrace();
        }
   
    }
    
    //starts the game
    public void start()
    {
        // creates the executor and scanner to take input from command line an interpret it
        NaturalCLI natcli =new NaturalCLI(cs);
        Scanner scanner =new Scanner(System.in);
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
            
        }
    }
            
   
    
}
