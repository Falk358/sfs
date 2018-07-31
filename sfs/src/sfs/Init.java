package sfs;

import java.util.ArrayList;
import java.util.List;

import sfs.entities.Entity;

/**
 * This class holds information about all entities and other
 * objects that should do something on its own on a regular basis.
 * 
 * The collections from this class are later used in the Game class in the game loop.
 * 
 */
public final class Init {

	/**
	 * List of all entities.
	 * On creation of an entity it gets added to this list.
	 */
	public static final List<Entity> ALL_ENTITES = new ArrayList<Entity>();
}
