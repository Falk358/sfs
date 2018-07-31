/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Max
 */
public class MapBuilderTest {
    
    

    /**
     * Test of linkByDirection method, of class MapBuilder.
     */
    @Test
    public void testLinkByDirection() {
        System.out.println(" testing linkByDirection");
        Tile tile1 = new Tile("test tile 1");
        Tile tile2 = new Tile("test tile 2");
        boolean is_north_south = true;
        MapBuilder.linkByDirection(tile1, tile2, is_north_south);
        //check whether values have been changed correctly
        assertSame(tile2, tile1.getTile_south());
        assertSame(tile1, tile2.getTile_north());
        //same test for east west
        is_north_south=false;
        Tile tile3  = new Tile("test tile 3");
        Tile tile4 = new Tile("test tile 4");
        MapBuilder.linkByDirection(tile3, tile4, is_north_south);
        assertSame(tile4, tile3.getTile_east());
        assertSame(tile3, tile4.getTile_west());
        System.out.println("testLinkByDirection successfull");
    }
    
    @Test
    public void testSquareDummyMap()
    {
        System.out.println("testing SquareDummyMap");
        Tile startingTile = MapBuilder.squareDummyMap();
        assertNotNull(startingTile.getTile_south());
        assertTrue(startingTile.getTile_south().getRoom_info().equals("test room 3"));
        assertNotNull(startingTile.getTile_south().getTile_east());
        assertTrue(startingTile.getTile_south().getTile_east().getRoom_info().equals("test room 4"));
        System.out.println("testSquareDummyMap successful");
    }
    
    
}
