package sfs.items.interfaces;

import java.util.List;

import sfs.entities.Entity;

/**
 * Marks an item as a weapon.
 */
public interface IWeapon {

	/**
	 * @param targetEntity
	 * 		The entity that gets attacked.
	 * @return
	 * 		True if the targetEntity took damage.
	 */
	public boolean attack( Entity targetEntity );
	
	/**
	 * @return
	 * 		The damage of the weapon.
	 */
	public int getAttackDamage();
}
