/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs.entities;
import sfs.Direction;
import sfs.Tile;
import sfs.items.Item;
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
    	if( this.location.getAllEntitesFromTile().size() > 1 )
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
		throw new UnsupportedOperationException("Not implemented yet.");
	}


	@Override
	public void handleAttack(int damage) 
	{
		setHealth( getHealth() - damage );
	}
	
	/**
	 * Uses the item at the given index.
	 * 
	 * @param index
	 * 		The index of the item in the items list of the player.
	 * @return
	 * 		if the index is out of range false is returned otherwise true will be returned.
	 */
	public boolean useItem( int index )
	{
		if( items.size() >= index || index < 0 )
		{
			System.err.println("Item number is invalid\n");
			return false;
		}
		
		Item searchedItem = items.get( index );
		return true;
	}
	
	/**
	 * Takes the first item from the tile.
	 * 
	 * @return
	 * 		True if an item could be picked up otherwise false.
	 */
	public boolean pickUpItem()
	{
		if( location.getAllItemsFromTile().isEmpty() )
			return false;
		
		Item pickedUpItem = location.getAllItemsFromTile().get( 0 );
		super.pickUpItem( pickedUpItem );
		
		return true;
	}
    
	/**
	 * @return
	 * 		The last item of the player or null if the player has no items.
	 */
	public Item getLastItem()
	{
		return items.isEmpty() ? null : items.get( items.size() - 1 );
	}
    
}
