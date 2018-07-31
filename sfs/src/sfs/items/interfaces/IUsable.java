package sfs.items.interfaces;

import sfs.entities.Entity;

/**
 * Marks an item as a usable item.
 */
public interface IUsable {

	/**
	 * @param targetEntity
	 * 		The entity that is the target of the usable item.
	 * @return
	 * 		True if the item was successfully used.
	 */
	public boolean useItem( Entity targetEntity );
}
