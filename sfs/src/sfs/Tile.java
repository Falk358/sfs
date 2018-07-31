/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sfs.entities.Entity;
import sfs.items.Item;

/**
 *
 * @author Max
 */
public class Tile {
    
    /**
     * List of all entities on that tile.
     */
    private List<Entity> entitiesOnTile;
    
    /**
     * List of all items on that tile.
     */
    private List<Item> itemsOnTile;
    
    private String room_info; //information to be displayed when inspecting the room
    
    //pointers to the neighbouring tiles
    private Tile tile_north;
    private Tile tile_south;
    private Tile tile_west;
    private Tile tile_east;
    

    public Tile(String room_info)
    {
        this.room_info=room_info;
        this.entitiesOnTile = new ArrayList<Entity>();
        this.itemsOnTile = new ArrayList<Item>();
    }
    
    //checks for room_info and adjacent Tiles; then prints RoomInfo and possible directions to move to
    public void printRoomInfo()
    {
        //checks for string written into room_info and adds it into a buffer string
        String room_info_buff=room_info;
        room_info_buff=room_info_buff+"\nThere are paths in the following directions:";
        //if condition checking whether adjacent tiles are empty via boolean condition
        if(tile_north!=null)
        {
            //if tile is adjacent, adds a string to the buffer string displaying a direction in which a tile has been confirmed
            room_info_buff=room_info_buff+" NORTH";
        }
        if(tile_east!=null)
        {
            room_info_buff=room_info_buff+" EAST";
        }
        if(tile_south!=null)
        {
            room_info_buff=room_info_buff+" SOUTH";
        }
        if(tile_west!=null)
        {
            room_info_buff=room_info_buff+" WEST";
        }
        
        if( itemsOnTile.isEmpty() )
        	room_info_buff += "\nThere doesn't seem to be anything interesting.";
        else
        	room_info_buff += "\nThe following items are on this tile:\n" + itemsOnTile.stream().map(Item::getName).collect( Collectors.joining( "\n" ) );
        
        System.out.println(room_info_buff);
    }
    
    
    
    //getters and setters

    public String getRoom_info() {
        return room_info;
    }

    public void setRoom_info(String room_info) {
        this.room_info = room_info;
    }

    public Tile getTile_north() {
        return tile_north;
    }

    public void setTile_north(Tile tile_north) {
        this.tile_north = tile_north;
    }

    public Tile getTile_south() {
        return tile_south;
    }

    public void setTile_south(Tile tile_south) {
        this.tile_south = tile_south;
    }

    public Tile getTile_west() {
        return tile_west;
    }

    public void setTile_west(Tile tile_west) {
        this.tile_west = tile_west;
    }

    public Tile getTile_east() {
        return tile_east;
    }

    public void setTile_east(Tile tile_east) {
        this.tile_east = tile_east;
    }
    
    public void removeEntityFromTile( Entity entity )
    {
    	if( entitiesOnTile.contains( entity ) )
    		entitiesOnTile.remove( entity );
    }
    
    public void addEntityToTile( Entity entity )
    {
    	entitiesOnTile.add( entity );
    }
    
    public List<Entity> getAllEntitesFromTile()
    {
    	return entitiesOnTile;
    }
    
    public List<Item> getAllItemsFromTile()
    {
    	return itemsOnTile;
    }
    
    public void addItemToTile( Item item )
    {
    	itemsOnTile.add( item );
    }
    
    public void removeItemFromTile( Item item )
    {
    	if( itemsOnTile.contains( item ) )
    		itemsOnTile.remove( item );
    }
}
