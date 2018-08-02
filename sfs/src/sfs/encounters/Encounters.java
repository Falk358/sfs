package sfs.encounters;

import java.util.ArrayList;
import java.util.List;

import sfs.entities.Entity;
import sfs.entities.Player;

/**
 * This class provides storage for encounters.
 */
public final class Encounters {
	
	/**
	 * This list stores all encounters that will be processed in the next turn.
	 */
	private static final List<Encounter> PENDING_ENCOUNTERS = new ArrayList<Encounter>();
	
	/**
	 * Add the encounter of the given entities to the pending encounters list.
	 * If either entity1 or entity2 is a player the encounter is a player encounter
	 * and receives special treatment.
	 * 
	 * @param entity1
	 * 		The first entity involved in the encounter.
	 * @param entity2
	 * 		The second entity involved in the encounter.
	 */
	public static void addEncounter( Entity entity1, Entity entity2 )
	{
		if( entity1 instanceof Player )
			PENDING_ENCOUNTERS.add( new PlayerEncounter( (Player) entity1, entity2) );
		else if( entity2 instanceof Player )
			PENDING_ENCOUNTERS.add( new PlayerEncounter( (Player) entity2, entity1 ) );
		else
			PENDING_ENCOUNTERS.add( new Encounter( entity1, entity2 ) );
	}
	
	/**
	 * Takes the first encounter in the list, removes it from the list and returns it.
	 * 
	 * @return
	 * 		null if the list is empty otherwise the first encounter.
	 */
	public static Encounter popFirstEncounter()
	{
		if( PENDING_ENCOUNTERS.isEmpty() )
		{
			return null;
		}
		else
		{
			Encounter e = PENDING_ENCOUNTERS.get( 0 );
			PENDING_ENCOUNTERS.remove( 0 );
			return e;
		}
	}
	
	/**
	 * @return
	 * 		The number of pending encounters.
	 */
	public static int getNumberOfPendingEncounters()
	{
		return PENDING_ENCOUNTERS.size();
	}
	
	/**
	 * Removes all encounters.
	 */
	public static void clearPendingEncounters()
	{
		PENDING_ENCOUNTERS.clear();
	}

}
