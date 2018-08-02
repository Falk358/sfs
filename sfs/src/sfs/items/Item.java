package sfs.items;

import sfs.entities.Entity;

/**
 * Abstract base class for every item.
 */
public abstract class Item {
	
	/**
	 * Name of the item.
	 */
	protected String name;
	
	/**
	 * A short description of the item.
	 */
	protected String description;
	
	/**
	 * The owner of the item.
	 */
	private Entity owner;

	/**
	 * Creates a new item with the given owner.
	 * We force every item to have an owner but null is also a valid owner.
	 * 
	 * @param owner
	 * 		The owner of the item or null if there is no owner.
	 */
	public Item( Entity owner ) 
	{
		setOwner( owner );
	}
	
	public String getName() 
	{
		return name;
	}

	protected void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	protected void setDescription(String description) 
	{
		this.description = description;
	}
	
	public Entity getOwner()
	{
		return owner;
	}
	
	public void setOwner( Entity owner )
	{
		this.owner = owner;
	}
}
