package sfs.items.weapons;

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
		this( attackDamage, "Weapon name", "No description available", owner );
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
	 * 
	 * For more complex implementations extend BaseWeapon and override
	 * the attack method.
	 */
	@Override
	public boolean attack( Entity targetEntity ) 
	{
		int entityHealthBefore = targetEntity.getHealth();
		targetEntity.handleAttack( this.attackDamage );
		
		/* Look up if damage was successfully dealt. */
		return targetEntity.getHealth() < entityHealthBefore;
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

	@Override
	public int getAttackDamage() 
	{
		return attackDamage;
	}
}
