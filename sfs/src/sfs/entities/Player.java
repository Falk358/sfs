/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs.entities;
import java.util.HashSet;
import java.util.Set;
import  org.naturalcli.*;

import sfs.Direction;
import sfs.Tile;
/**
 *
 * @author Max
 * entity representing the player
 */
public class Player extends Entity {
    
    public Player(Tile starting_tile)
    {
    	/*  health, attack damage, location */
    	super( 100, 5, starting_tile );
    }
    
   
    // this function is called when the player should switch rooms. it returns true on success, false if something went wrong. 
    public boolean changeRoom(Direction dir)
    {
        switch(dir)
        {
            case NORTH:
                if (this.location.getTile_north()!=null)
                {
                    setLocation( this.location.getTile_north() );
                    return true;
                }
                else
                {
                    System.err.println("no tile north of here");
                    return false;
                }
                
            case SOUTH:
                if (this.location.getTile_south()!=null)
                {
                    setLocation( this.location.getTile_south() );
                    return true;
                }
                else
                {
                    System.err.println("no tile south of here");
                    return false;
                }
               
            case WEST:
                if (this.location.getTile_west()!=null)
                {
                    setLocation( this.location.getTile_west() );
                    return true;
                }
                else
                {
                    System.err.println("no tile west of here");
                    return false;
                }
              
            case EAST: 
                if (this.location.getTile_east()!=null)
                {
                    setLocation( this.location.getTile_east() );
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
    
    private void checkForEncounters()
    {
    	if( this.location.getEntitiesOnTile().size() > 1 )
    		System.err.println("There is another entity on this tile\n");
    }
    
    
    //getters and setters
    public Tile getlocation() {
        return location;
    }

    @Override
    public void setLocation(Tile location) 
    {
    	if( this.location != location )
    	{
    		super.setLocation( location );
    		checkForEncounters();
    	}
    }

    public void setHealth(int health) 
    {
        this.health = health;
    }

	@Override
	public void attackEntity( Entity entity ) 
	{
		
	}


	@Override
	public void handleAttack(int damage) 
	{
		
	}
    
    
}
