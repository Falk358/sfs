/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;
import java.util.HashSet;
import java.util.Set;
import  org.naturalcli.*;
 
/*
 * @author Max
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tile tile1 =new Tile("test room");
        Tile tile2 =new Tile("test room 2");
        tile1.setTile_north(tile2);
        tile2.setTile_south(tile1);
        Player player =new Player(tile1);
        try
        {
        Command goCommand=new Command("go <direction:string>", "command to go to tile located in direction (has to be all caps) NORTH, SOUTH, EAST, WEST", 
        new ICommandExecutor ()
        {
          public void execute(ParseResult pr ) 
          { 
              String result =(String) pr.getParameterValue(0);
              Direction mydir;
              try
              {
                  mydir=Direction.valueOf(result);
              }
              catch (IllegalArgumentException e)
              {
                  System.err.println("parameter incorrect. please use one of the following: NORTH, SOUTH, EAST, WEST");
                  return;
              }
              if (player.changeRoom(mydir))
                  System.out.println("going " + result);
          }
        });
        Set<Command> cs = new HashSet<Command>();
        cs.add(goCommand);
        new NaturalCLI(cs).execute(args);
        }
        catch (InvalidSyntaxException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
    
     
}
