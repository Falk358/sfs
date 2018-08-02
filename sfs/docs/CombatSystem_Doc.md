# Combat System 
This documentation should help to get used to the framework.
Now to the basics the combat system is a turn based system and activates when the player
encounters another entity on the same tile.
After the combat system is activated the player has to end the battle before
he can do anything else such as moving to another tile.

## Activation of the combat system
Every time the player moves to a new tile the player updates the
location and if it is a different location he checks weather
there is another entity on the same tile.

In the class `Player`:
```java
	private void checkForEncounters()
    {
    	if( getLocation().getAllEntitesFromTile().size() > 1 )
    	{
    		System.err.println("There is another entity on this tile");
    		for( Entity e : getLocation().getAllEntitesFromTile() )
    			if( !e.equals( this ) )
    				Encounters.addEncounter( this, e );
    	}
    }

    public void move(Tile location) 
    {
    	if( !getLocation().equals( location ) )
    	{
    		super.setLocation( location );
    		checkForEncounters();
    	}
    }
```

Note: To make entities also attack other entities the method `checkForEncounters`
just has to be pushed into the `Entity` class and called there inside of `setLocation`.

If there is another entity on this tile the player adds a new Entry to the
`PENDING_ENCOUNTERS` in the `Encounters` class.

In class `Encounters`:
```java
	public static void addEncounter( Entity entity1, Entity entity2 )
	{
		if( entity1 instanceof Player )
			PENDING_ENCOUNTERS.add( new PlayerEncounter( (Player) entity1, entity2) );
		else if( entity2 instanceof Player )
			PENDING_ENCOUNTERS.add( new PlayerEncounter( (Player) entity2, entity1 ) );
		else
			PENDING_ENCOUNTERS.add( new Encounter( entity1, entity2 ) );
	}
```

The `PENDING_ENCOUNTERS` list stores all encounters of the player that need to
be dealt with.

In the class `Game` that runs the game loop we check if there are any pending
encounters and if so we handle them immediately. Note that the player
has to make all open encounters at once before he can proceed.

In class `Game`:
```java
	 while( player.getHealth() > 0 )
        {	
            // retrieve next argument entered by user
            String arg= scanner.nextLine();
            try
            {
                /*parse the argument and execute the correct code if defined in a Command*/
                natcli.execute(arg);
            }
            catch (ExecutionException e)
            {
                System.err.println("command not defined");
            }
            
            /* if the player has pending encounters we process them yet. */
        	if( Encounters.getNumberOfPendingEncounters() > 0 )
        		for( int i = 0; i < Encounters.getNumberOfPendingEncounters(); i++ )
        			Encounters.popFirstEncounter().start();
            
        }
```

As you can see the processing of the encounters happens after the player
has entered his command (which has to be a go command).


To sum up at this point the player moved successfully one tile, added
all encounters to the `PENDING_ENCOUNTERS` list and now starts to process
them.

## Execution of one combat
As mentioned in the last section at the moment only encounters between the player
and another entity is possible. Nevertheless the combat system could also handle
two entities.

The foundation of the combat system is the `Encounter` class:
```java
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
			boolean onSameTile = entity1.getLocation().equals( entity2.getLocation() );
		
			for( int turn = 0; entity1.getHealth() > 0 && entity2.getHealth() > 0 && onSameTile; turn++ )
			{
				if( turn % 2 == 0 )
					doEntity1Move();
				else
					doEntity2Move();
					
				onSameTile = entity1.getLocation().equals( entity2.getLocation() )
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
```

As you can see the `Encounter` class needs two entities to work with and calls
alternating `doEntity1Move` and `doEntity2Move` after the encounter gets started.
This class is generally meant as a base class and is only useful for the encounter of 
two entities since they don't require any other user action.
Because it is meant as a base class it doesn't call `entity1.attackEntity( entity2 );` 
directly since we override this method in the subclass.

Such a subclass would be `PlayerEncounter`. Since one entity is a player we also
have to ask the user repeatedly for input. Additionally we want a special set
of commands to use in battle since not every command that the player normally
has makes sense in the context of a battle.

Therefore the `PlayerEncounter` class overrides the `doEntity1Move`:
SideNode: The `PlayerEncounter` class ensures that entity1 is the player.
```java
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
```

and also the `doEntity2Move` gets overridden since we want some more feedback for the player:
```java
	@Override
	protected void doEntity2Move() 
	{
		int healthDiff = entity1.getHealth();
		entity2.attackEntity( entity1 );
		healthDiff -= entity1.getHealth();
		System.out.println( "Enemy attacked and dealt " + healthDiff + " damage. Your current life: " + entity1.getHealth() );
	}
```

So to sum up the combat system is just another loop with its own commands.

## Damage calculation
As you have seen in the previous section there was no damage calculation whatsoever.
This is because every `Entity` has to implement `attackEntity( Entity entity )` and
`handleAttack( int damage )`.
This has the advantage that it is very easy to implement custom entity behavior. Just extend
`Entity` and implement the unimplemented methods or if you want to do less work extend the
`BaseEnemy` class which already provides some functionality.

For example `BaseEnemy` implements the methods like this:
```java
	@Override
	public void attackEntity(Entity entity) 
	{
		/* Filter all weapons */
		List<Item> weapons = items.stream().filter( i -> i instanceof IWeapon ).collect( Collectors.toList() );
		
		if( weapons.size() == 0 )
			entity.handleAttack( this.attackDamage );
		else
		{
			/* get one randomly chosen weapon*/
			Random random = new Random();
			IWeapon randomWeapon = (IWeapon) weapons.get( random.nextInt( weapons.size() ) );
		
			/* Attack with the weapon */
			randomWeapon.attack( entity );
		}
	}

	@Override
	public void handleAttack(int damage) 
	{
		setHealth( getHealth() - damage );
	}
```

In `attackEntity` it uses a random weapon if it has one otherwise it attacks with the base attack damage.
Another important point is that weapons (all items that implement the `IWeapon` interface) have their
own attack method since they might implement a special functionality like AOE or more damage the more
often the same entity gets hit and so on.

The `handleAttack` method is not very interesting but note that it uses the `setHealth` method
from `Entity` rather then manipulating the health as variable. This is important because if the
health is below or equal 0 another abstract method from `Entity` gets called namely `onDeath`.

`setHealth` method in `Entity`:
```java
	protected final void setHealth( int health )
	{
		if( health <= 0 )
			onDeath();
		
		this.health = health;
	}
```

For example the `BaseEnemy` class implements the `onDeath` method such that it drops
a random item if it has one:
```java
	@Override
	public void onDeath() 
	{
		if( !items.isEmpty() )
		{
			Random rand = new Random();
			Item drop = items.get( rand.nextInt( items.size() ) );
			drop.setOwner( null );
			location.addItemToTile( drop );
		}
		
		location.removeEntityFromTile( this );
	}
```

Thats it for the docs for the combat system. Check out the docs for the entities and
the items since they are tightly related to the combat system.

Version 0.1 ~ 02.08.18