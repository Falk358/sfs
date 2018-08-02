package sfs.encounters;

import sfs.entities.Entity;

/**
 * Class to handle an encounter between two entities.
 * If one of the two entities is a player use the class PlayerEncounter.
 */
public class Encounter {

	protected Entity entity1;
	
	protected Entity entity2;
	
	public Encounter( Entity entity1, Entity entity2 )
	{
		this.entity1 = entity1;
		this.entity2 = entity2;
	}
	
	/**
	 * Starts the encounter.
	 */
	public void start()
	{
		System.out.println("Starting encounter between " + entity1 + " and " + entity2);
		
		for( int turn = 0; entity1.getHealth() > 0 && entity2.getHealth() > 0; turn++ )
		{
			if( turn % 2 == 0 )
				doEntity1Move();
			else
				doEntity2Move();
		}
		
		System.out.println( ( entity1.getHealth() > 0 ? entity1 : entity2 ) + " won the battle!");
	}
	
	protected void doEntity1Move()
	{
		entity1.attackEntity( entity2 );
	}
	
	protected void doEntity2Move()
	{
		entity2.attackEntity( entity1 );
	}
}
