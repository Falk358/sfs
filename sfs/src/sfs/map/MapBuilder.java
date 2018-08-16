/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs.map;

import sfs.map.Tile;

/**
 *
 * @author Max
 */
public class MapBuilder {
    
    
    /* sets variables so that connection is displayed both ways in tiles
    if is_north_south: tile1 is the northern tile and tile2 the southern if is north_south is false: tile1 is western, tile2 eastern*/
    public static void linkByDirection(Tile tile1, Tile tile2, boolean is_north_south)
    {
        if (is_north_south)
        {
            tile1.setTile_south(tile2);
            tile2.setTile_north(tile1);
        }
        else
        {
            tile1.setTile_east(tile2);
            tile2.setTile_west(tile1);
        }
    }
    
    
    //creates a map shaped like a 2x2 square. Returns starting tile.
    public static Tile squareDummyMap()
    {
        Tile starting =new Tile("test room starting tile");
        Tile tile2 =new Tile("test room 2");
        Tile tile3 =new Tile("test room 3");
        Tile tile4 =new Tile("test room 4");
        linkByDirection(starting, tile2, false);
        linkByDirection(tile3, tile4, false);
        linkByDirection(tile2, tile4, true);
        linkByDirection(starting, tile3, true);
        return starting;
    }
    
}
