package sfs.encounters;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.NaturalCLI;

import sfs.map.Direction;
import sfs.map.Tile;
import sfs.entities.Entity;
import sfs.entities.Player;

/**
 * This class handles the encounter between the player and an entity.
 */
public class PlayerEncounter extends Encounter {

	/**
	 * A new parser for the commands the player can use in battle.
	 */
	private NaturalCLI natCLI;
	
	/**
	 * Scanner reading from system.in
	 */
	private Scanner scanner;
	
	/**
	 * Boolean to indicate weather the command was successful or not.
	 * With this flag we want to avoid that when the user misspells he
	 * doesn't loose one round in battle.
	 */
	private boolean successfulCommand;
	
	public PlayerEncounter( Player player, Entity entity2 ) 
	{
		/* Entity1 is equal to the player */
		super( player, entity2 );
		this.scanner = new Scanner( System.in );
		Set<Command> commands = prepareCommands();
		natCLI = new NaturalCLI( commands );
	}
	
	private Set<Command> prepareCommands()
	{
		Set<Command> commands = new HashSet<Command>();
		Command flee, attack, weapons, usables, inventory, useItem;
		
		try
		{
			flee = new Command( "flee", "flee from combat in a random direction", 
					r -> 
					{ 
						/* Remove all pending encounters since we leave this area */
						Encounters.clearPendingEncounters();
						
						/* get all possible directions from this tile */
						Tile currentLocation = entity1.getLocation();
						List<String> pd = currentLocation.getPossibleDirections();
						
						/* Chose randomly a direction */
						Random rand = new Random();
						Direction direction = Direction.valueOf( pd.get( rand.nextInt( pd.size() ) ) );
						
						successfulCommand = true;
						if( ( (Player) entity1 ).changeRoom( direction ) )
							System.out.println( "You successfully escaped to the " + direction.toString() );
						else
							System.out.println( "You couldn't escape!" );
					} 
			);
			
			attack = new Command( "attack", "attacks the enemy with the base attack (doesn't use a weapon).", 
					r -> 
					{ 
						( (Player) entity1 ).attackEntity( entity2 );
						successfulCommand = true;
					}
			);
			
			weapons = new Command( "weapons", "shows all weapons in your inventory", 
					r -> ( (Player) entity1 ).printWeaponsInInventory()
			);
			
			usables = new Command( "usables", "shows all useables in your inventory",
					r -> ( (Player) entity1 ).printUsablesInInventory()
			);
			
			inventory = new Command( "inventory", "commnad to look at the items in your inventory", 
					r -> ( (Player) entity1 ).printInventoryItems()
           	);
			
			useItem = new Command( "use <index:string> <target:string>", "command to use an item. If the item is a weapon you attack with it.\n"
					+ "If it is a usable item it gets used.\nThe syntax is use <index:int> <target:string>. Where index is the index of the item in your inventory.\n"
					+ "You can find the indices using the weapons, usables or inventory commands. The second argument is the target of the item.\n"
					+ "Valid targets are \"me\" or \"other\"\n",
					r -> 
					{
						try
						{
							int index = Integer.parseInt( r.getParameterValue(0).toString() );
							String target = r.getParameterValue( 1 ).toString();
							
							if( !target.equalsIgnoreCase( "me" ) && !target.equalsIgnoreCase( "other" ) )
							{
								System.err.println("You have to give either \"me\" or \"other\" as second argument after the index to the useItem command!");
								return;
							}
							
							Entity targetEntity = target.equalsIgnoreCase( "me" ) ? entity1 : entity2;
							boolean success = ( (Player) entity1 ).useItem( index, targetEntity );
							
							if( !success )
							{
								System.err.println("The operation was not successful.");
								return;
							}
							
							successfulCommand = true;
						}
						catch( NumberFormatException e )
						{
							System.err.println( "The index was not an integer!" );
						}
					}
			);
		}
		catch( InvalidSyntaxException e )
		{
			e.printStackTrace();
			return null;
		}
		
		Collections.addAll( commands, flee, attack, weapons, usables, inventory, useItem );
		return commands;
	}

	/**
	 * Entity1 equals to the player therefore we let the player enter the command and
	 * proceed.
	 */
	@Override
	protected void doEntity1Move() 
	{
		successfulCommand = false;
		while( !successfulCommand )
		{
			String line = scanner.nextLine();
			
			try
			{
				natCLI.execute( line );
			}
			catch( ExecutionException e )
			{
				System.err.println("Unknown command");
			}
		}
	}
	
	/**
	 * Just add some feedback for the user.
	 */
	@Override
	protected void doEntity2Move() 
	{
		int healthDiff = entity1.getHealth();
		entity2.attackEntity( entity1 );
		healthDiff -= entity1.getHealth();
		System.out.println( "Enemy attacked and dealt " + healthDiff + " damage. Your current life: " + entity1.getHealth() );
	}
}
