/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;

/**
 *
 * @author Max
 */
public class Tile {
    
    public Tile(String room_info)
    {
        this.room_info=room_info;
    }
    
    private String room_info; //information to be displayed when inspecting the room
    private Tile tile_north;
    private Tile tile_south;
    private Tile tile_west;
    private Tile tile_east;
    
    
    public void printRoomInfo()
    {
        System.out.println(room_info);
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
    
    
}
