/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs.entities;
import java.util.stream.Collectors;

import sfs.map.Direction;
import sfs.map.Tile;
import sfs.encounters.Encounters;
import sfs.items.Item;
import sfs.items.interfaces.IUsable;
import sfs.items.interfaces.IWeapon;
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
                if (getLocation().getTile_north()!=null)
                {
                    move( getLocation().getTile_north() );
                    return true;
                }
                else
                {
                    System.err.println("no tile north of here");
                    return false;
                }
                
            case SOUTH:
                if (getLocation().getTile_south()!=null)
                {
                    move( getLocation().getTile_south() );
                    return true;
                }
                else
                {
                    System.err.println("no tile south of here");
                    return false;
                }
               
            case WEST:
                if (getLocation().getTile_west()!=null)
                {
                    move( getLocation().getTile_west() );
                    return true;
                }
                else
                {
                    System.err.println("no tile west of here");
                    return false;
                }
              
            case EAST: 
                if (getLocation().getTile_east()!=null)
                {
                    move( getLocation().getTile_east() );
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
    	if( getLocation().getAllEntitesFromTile().size() > 1 )
    	{
    		System.err.println("There is another entity on this tile");
    		for( Entity e : getLocation().getAllEntitesFromTile() )
    			if( !e.equals( this ) )
    				Encounters.addEncounter( this, e );
    	}
    }

    public void move(Tile location) 
    {
    	if( !getLocation().equals( location ) )
    	{
    		super.setLocation( location );
    		checkForEncounters();
    	}
    }

    /**
     * Attacks the target entity with the base attack.
     */
	@Override
	public void attackEntity( Entity targetEntity ) 
	{
		int enemyHealthDiff = targetEntity.getHealth();
		targetEntity.handleAttack( attackDamage );
		enemyHealthDiff -= targetEntity.getHealth();
		System.out.println("The enemy took " + enemyHealthDiff + " damage. Enemy current life: " + targetEntity.getHealth() );
	}


	@Override
	public void handleAttack(int damage) 
	{
		setHealth( getHealth() - damage );
	}
	
	/**
	 * Uses the item at the given index.
	 * If the item is a weapon it attacks with the weapon.
	 * If the item is a usable it uses the item
	 * 
	 * @param index
	 * 		The index of the item in the items list of the player.
	 * @param targetEntity
	 * 		The target of the item.
	 * @return
	 * 		if the index is out of range false is returned otherwise true will be returned.
	 */
	public boolean useItem( int index, Entity targetEntity )
	{
		if( index >= items.size() || index < 0 )
		{
			System.err.println("Item index is invalid\n");
			return false;
		}
		
		Item searchedItem = items.get( index );
		if( searchedItem instanceof IWeapon )
			if( ( (IWeapon) searchedItem ).attack( targetEntity ) )
				System.out.println( "Hit enemy with " + ( (IWeapon) searchedItem ).getAttackDamage() + "\tEnemy live: " + targetEntity.getHealth() );
		else if( searchedItem instanceof IUsable )
			if( ( (IUsable) searchedItem ).useItem( targetEntity ) )
				System.out.println( "Used item " + searchedItem.getName() + " with success!" );
		
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
		if( getLocation().getAllItemsFromTile().isEmpty() )
			return false;
		
		Item pickedUpItem = getLocation().getAllItemsFromTile().get( 0 );
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
	
	/**
	 * Prints all items in the inventory of the player to system.out
	 */
	public void printInventoryItems()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("List of items in your inventory:\n");
		sb.append("Index\t\tName\t\tDescription\n");
		sb.append("--------------------------------------------------\n");
		sb.append( 
				items.stream()
				.map( i -> items.indexOf( i ) + "\t\t" + i.getName() + "\t\t" + i.getDescription() )
				.collect( Collectors.joining( "\n" ) ) 
		);
		sb.append("\n--------------------------------------------------");
		
		System.out.println( sb.toString() );
	}
	
	/**
	 * Prints all weapons in the inventory of the player to system.out
	 */
	public void printWeaponsInInventory()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("List of weapons in your inventory:\n");
		sb.append("Player base damage: " + attackDamage + "\n");
		sb.append("Index\t\tName\t\tDescription\t\tDamage\n");
		sb.append("--------------------------------------------------------------\n");
		
		sb.append(
				items.stream().filter( i -> i instanceof IWeapon )
				.map( i -> items.indexOf( i ) + "\t\t" + i.getName() + "\t\t" + i.getDescription() + 
						   "\t\t" + ( (IWeapon) i ).getAttackDamage() )
				.collect( Collectors.joining( "\n", "", "\n" ) )
		);
		
		sb.append("--------------------------------------------------------------");
		System.out.println( sb.toString() );
	}
	
	/**
	 * Prints all usables in the inventory of the player to system.out
	 */
	public void printUsablesInInventory()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("List of usables in your inventory:\n");
		sb.append("Index\t\tName\t\tDescription\t\tUses\n");
		sb.append("--------------------------------------------------------------\n");
		
		sb.append(
				items.stream().filter( i -> i instanceof IUsable )
				.map( i -> items.indexOf( i ) + "\t\t" + i.getName() + "\t\t" + i.getDescription() + 
						   "\t\t" + ( (IUsable) i ).getNumberOfUses() )
				.collect( Collectors.joining( "\n", "", "\n" ) )
		);
		
		sb.append("--------------------------------------------------------------");
		System.out.println( sb.toString() );
	}


	/**
	 * If the player dies the game is over.
	 * But this is also the abort condition for the game loop therefore we don't have
	 * to do anything here.
	 */
	@Override
	public void onDeath() {}
    
}
