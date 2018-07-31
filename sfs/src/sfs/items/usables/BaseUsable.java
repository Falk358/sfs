package sfs.items.usables;

import sfs.entities.Entity;
import sfs.items.Item;
import sfs.items.interfaces.IUsable;

public abstract class BaseUsable extends Item implements IUsable {

	/**
	 * The number of uses the usable item can survive.
	 * if this values reaches 0 the item gets destroyed.
	 */
	private int numberOfUses;
	
	/**
	 * Creates a base usable with the given entity as owner, 1 use and a dummy name and description.
	 * 
	 * @param owner
	 * 		The owner of the item.
	 */
	public BaseUsable( Entity owner ) 
	{
		this( 1, owner );
	}
	
	/**
	 * Creates a base usable with the given number of uses and the given owner.
	 * Takes as name and description dummy strings.
	 * 
	 * Note: The numberOfUses has to be a positive number greater than 0 otherwise
	 * 1 will be assigned to the number of uses.
	 * 
	 * @param numberOfUses
	 * 		The number of uses the item can be used without breaking.
	 * @param owner
	 * 		The owner of the item.
	 */
	public BaseUsable( int numberOfUses, Entity owner ) 
	{
		this( numberOfUses, "dummy usable", "no description", owner );
	}
	
	/**
	 * Creates a base usable with the given number of uses, the given owner, 
	 * the given name and description.
	 * 
	 * Note: The numberOfUses has to be a positive number greater than 0 otherwise
	 * 1 will be assigned to the number of uses.
	 * 
	 * @param numberOfUses
	 * 		The number of uses the item can be used without breaking.
	 * @param name
	 * 		The name of the item.
	 * @param description
	 * 		The description of the item.
	 * @param owner
	 * 		The owner of the item.
	 */
	public BaseUsable( int numberOfUses, String name, String description, Entity owner ) 
	{
		super( owner );
		setNumberOfUses( numberOfUses );
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Uses the item and decreases the number of uses and if the
	 * number of uses is equal or below 0 the item gets removed from the owner.
	 */
	@Override
	public boolean useItem( Entity targetEntity ) 
	{
		/* Check if the owner is not null */
		if( this.getOwner() == null )
			return false;
		
		boolean success = doItemAction( targetEntity );
		
		if( --numberOfUses <= 0 )
			this.getOwner().getItems().remove( this );
		
		return success;
	}
	
	/**
	 * Needs a custom implementation.
	 * 
	 * @param targetEntity
	 * 		The target of the usable item.
	 * @return
	 * 		True if item action was successful.
	 */
	protected abstract boolean doItemAction( Entity targetEntity );

	public int getNumberOfUses() 
	{
		return numberOfUses;
	}

	/**
	 * Sets the number of uses of the item before it breaks.
	 * 
	 * Note: The numberOfUses has to be a positive number greater than 0 otherwise
	 * 1 will be assigned to the number of uses.
	 * 
	 * @param numberOfUses
	 */
	protected void setNumberOfUses(int numberOfUses) 
	{
		this.numberOfUses = ( numberOfUses > 1 ) ? numberOfUses : 1;
	}
}
