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
public class Player {
    private int health; //starting value
    private int base_attack;

    private Tile current_tile;

    
    public Player(Tile starting_tile)
    {
        this.current_tile=starting_tile;
        this.health=100;
        this.base_attack=5;
    }
    
    public void changeRoom(Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                this.current_tile=this.current_tile.getTile_north();
                break;
            
            case SOUTH:
                this.current_tile=this.current_tile.getTile_south();
                break;
            case WEST:
                this.current_tile=this.current_tile.getTile_west();
                break;
            case EAST: 
                this.current_tile=this.current_tile.getTile_east();
                break;
                
            default:
                System.err.println("enum type " + dir + "not valid");
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
