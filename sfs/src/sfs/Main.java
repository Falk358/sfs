/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;
import java.util.HashSet;
import java.util.Set;
import  org.naturalcli.*;

import sfs.entities.Player;
import sfs.entities.enemies.BaseEnemy;
import sfs.items.weapons.BaseWeapon;
 
/*
 * @author Max
 */
public class Main {

    /**
     * @param args the command line arguments
     * 
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
        BaseEnemy be = new BaseEnemy(100, 20, tile3);
        BaseWeapon bw = new BaseWeapon( 50, null );
        tile2.addItemToTile( bw );
        Game game=new Game(player);
        game.start();
    }
    
}
