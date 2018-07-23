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
     * this is the logic to execute the go command. Probabk´ly should be put into separate class
     */
    public static void main(String[] args) {
        Tile tile1 =new Tile("test room");
        Tile tile2 =new Tile("test room 2");
        Tile tile3 =new Tile("test room 3");
        tile1.setTile_north(tile2);
        tile2.setTile_south(tile1);
        tile1.setTile_east(tile3);
        tile3.setTile_west(tile1);
        Player player =new Player(tile1);
        Game game=new Game(player);
        game.start();
    }
    
}
