package sfs.entities;

import java.util.ArrayList;
import java.util.List;

import sfs.Init;
import sfs.Tile;
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
		this.location.getEntitiesOnTile().add( this );
		/* Add the entity to the list of all entites */
		Init.ALL_ENTITES.add( this );
	}
	
	/**
	 * The health of the entity.
	 */
	protected int health;
	
	/**
	 * The attackDamage of the entity. (without item)
	 */
	protected int attackDamage;
	
	/**
	 * The location of the entity.
	 */
	protected Tile location;
	
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

	public int getHealth() 
	{
		return health;
	}

	public int getAttackDamage() 
	{
		return attackDamage;
	}

	public Tile getLocation() 
	{
		return location;
	}
	
	protected void setLocation(Tile location)
	{
		this.location.getEntitiesOnTile().remove( this );
		this.location = location;
		this.location.getEntitiesOnTile().add( this );
	}
	
	public List<Item> getItems()
	{
		return items;
	}
	
}
