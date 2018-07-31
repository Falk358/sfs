package sfs.items.interfaces;

import java.util.List;

import sfs.entities.Entity;

/**
 * Marks an item as a weapon.
 */
public interface IWeapon {

	/**
	 * @param targetEntities
	 * 		The entities that get attacked.
	 * @return
	 * 		A list of boolean values that describe if the targetEntity was successfully
	 * 		attacked.
	 */
	public List<Boolean> attack( List<Entity> targetEntities );
	
}
