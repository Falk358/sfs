package sfs.entities;

import java.util.ArrayList;
import java.util.List;

import sfs.Init;
import sfs.map.Tile;
import sfs.items.Item;

/**
 * Base class for every entity.
 * TODO: This class is WIP and should be extended in the near future.
 */
public abstract class Entity {

	protected Entity( int health, int attackDamage, Tile startLocation )
	{
		this.health = health;
		this.attackDamage = attackDamage;
		this.location = startLocation;
		this.items = new ArrayList<Item>();
		
		/* Add the entity to the tile */
		this.location.addEntityToTile( this );
		/* Add the entity to the list of all entites */
		Init.ALL_ENTITES.add( this );
	}
	
	/**
	 * The health of the entity.
	 */
	private int health;
	
	/**
	 * The attackDamage of the entity. (without item)
	 */
	protected int attackDamage;
	
	/**
	 * The location of the entity.
	 */
	private Tile location;
	
	/**
	 * The list of all items the entity has.
	 */
	protected List<Item> items;
	
	/**
	 * Attack the given entity.
	 * 
	 * @param entity
	 * 		The entity that should be attacked.
	 */
	public abstract void attackEntity( Entity entity );
	
	/**
	 * Handles the attack from another entity with the given
	 * attack damage.
	 * 
	 * @param damage
	 * 		The attack damage of the other entity.
	 */
	public abstract void handleAttack( int damage );
	
	/**
	 * This method gets called in the case that an entity dies (aka the health is smaller equal 0).
	 */
	public abstract void onDeath();
	
	/**
	 * Picks up the given item from the current location.
	 * This method also updates the references in the item itself.
	 * 
	 * @param item
	 * 		The item that should be picked up.
	 */
	public void pickUpItem( Item item )
	{
		location.removeItemFromTile( item );
		addItem( item );
	}
	
	/**
	 * Adds one item to the inventory items and sets the owner properly.
	 * 
	 * @param item
	 * 		The item that should get added to the inventory.
	 */
	public void addItem( Item item )
	{
		items.add( item );
		item.setOwner( this );
	}

	public final int getHealth() 
	{
		return health;
	}
	
	/**
	 * Sets the new health. If the health is smaller or equal to 0
	 * the die method gets called.
	 * 
	 * @param health
	 * 		The new health of the entity.
	 */
	protected final void setHealth( int health )
	{
		if( health <= 0 )
			onDeath();
		
		this.health = health;
	}

	public int getAttackDamage() 
	{
		return attackDamage;
	}

	public final Tile getLocation() 
	{
		return location;
	}
	
	/**
	 * Sets the new location and updates the entity list of the tile.
	 * 
	 * @param location
	 * 		The new location.
	 */
	protected final void setLocation(Tile location)
	{
		this.location.removeEntityFromTile( this );
		this.location = location;
		this.location.addEntityToTile( this );
	}
	
	public List<Item> getItems()
	{
		return items;
	}
	
}
