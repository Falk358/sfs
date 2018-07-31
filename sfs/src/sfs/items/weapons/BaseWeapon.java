package sfs.items.weapons;

import java.util.ArrayList;
import java.util.List;

import sfs.entities.Entity;
import sfs.items.Item;
import sfs.items.interfaces.IWeapon;

/**
 * Base class for every weapon.
 */
public class BaseWeapon extends Item implements IWeapon {
	
	/**
	 * The attackDamage of the weapon.
	 */
	private int attackDamage;

	/**
	 * Creates a weapon with the given attack damage and a dummy name and description.
	 * 
	 * Note: attack damage has to be greater than or equal to 0 otherwise 0 will be set.
	 * 
	 * @param attackDamage
	 * 		The attack damage of the new weapon
	 * @param owner
	 * 		The owner of the item.
	 */
	public BaseWeapon( int attackDamage, Entity owner )
	{
		this( attackDamage, "No description available", "No name", owner );
	}
	
	/**
	 * Create a weapon with the given attack damage, description and name.
	 * 
	 * @param attackDamage
	 * 		The attack damage of the weapon.
	 * @param name
	 * 		The name of the weapon.
	 * @param description
	 * 		The description of the weapon.
	 * @param owner
	 * 		The owner of the item.
	 */
	public BaseWeapon( int attackDamage, String name, String description, Entity owner ) 
	{
		super( owner );
		this.setAttackDamage( attackDamage );
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Simple implementation of the attack functionality.
	 * Iterates over every entity in the list and attacks it. if the entity
	 * gets damaged true is added to the list.
	 * 
	 * For more complex implementations extend BaseWeapon and override
	 * the attack method.
	 */
	@Override
	public List<Boolean> attack( List<Entity> targetEntities ) 
	{
		List<Boolean> success = new ArrayList<Boolean>();
		
		for( Entity e : targetEntities )
		{
			int currentEntityHealth = e.getHealth();
			e.handleAttack( this.attackDamage );
		
			/* Look up if damage was successfully dealt. */
			success.add( e.getHealth() < currentEntityHealth );
		}
		
		return success;
	}
	
	/**
	 * Sets the attack damage. The damage has to be greater than or equal to 0 otherwise
	 * 0 will be set.
	 * 
	 * @param attackDamage
	 * 		The new attack damage of the weapon
	 */
	protected void setAttackDamage( int attackDamage )
	{
		this.attackDamage = ( attackDamage >= 0 ) ? attackDamage : 0;
	}
}
