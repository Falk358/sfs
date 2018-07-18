/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs;
import java.util.HashSet;
import java.util.Set;
import  org.naturalcli.*;
/**
 *
 * @author Max
 * entity representing the player
 */
public class Player {
    private int health; 
    private int base_attack;

    private Tile current_tile; //the tile where the player is located

    
    public Player(Tile starting_tile)
    {
        this.current_tile=starting_tile;
        this.health=100;//starting value
        this.base_attack=5;//starting value
    }
    
   
    // this function is called when the player should switch rooms. it returns true on success, false if something went wrong. 
    public boolean changeRoom(Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                if (this.current_tile.getTile_north()!=null)
                {
                    this.current_tile=this.current_tile.getTile_north();
                    return true;
                }
                else
                {
                    System.err.println("no tile north of here");
                    return false;
                }
                
            case SOUTH:
                if (this.current_tile.getTile_south()!=null)
                {
                    this.current_tile=this.current_tile.getTile_south();
                    return true;
                }
                else
                {
                    System.err.println("no tile south of here");
                    return false;
                }
               
            case WEST:
                if (this.current_tile.getTile_west()!=null)
                {
                    this.current_tile=this.current_tile.getTile_west();
                    return true;
                }
                else
                {
                    System.err.println("no tile west of here");
                    return false;
                }
              
            case EAST: 
                if (this.current_tile.getTile_east()!=null)
                {
                    this.current_tile=this.current_tile.getTile_east();
                    return true;
                }
                else
                {
                    System.err.println("no tile east of here");
                    return false;
                }
               
                
            default:
                System.err.println("enum type " + dir + "not valid");
                return false;
           }
    }
    
    
    
    
    
    
    
    //getters and setters
    public Tile getCurrent_tile() {
        return current_tile;
    }

    public void setCurrent_tile(Tile current_tile) {
        this.current_tile = current_tile;
    }
    
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBase_attack() {
        return base_attack;
    }

    public void setBase_attack(int base_attack) {
        this.base_attack = base_attack;
    }
    
    
}
