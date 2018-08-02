package sfs.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import sfs.Tile;
import sfs.entities.Entity;
import sfs.items.Item;
import sfs.items.interfaces.IWeapon;

/**
 * Base implementation of an enemy.
 */
public class BaseEnemy extends Entity {

	public BaseEnemy( int health, int attackDamage, Tile startLocation ) 
	{
		super(health, attackDamage, startLocation);
	}

	/**
	 * Simple attack implementation.
	 * Searches the items for a weapon if there is one the entity attacks with the
	 * weapon otherwise it will take the base attack damage.
	 */
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

	/**
	 * On death the enemy drops a random item if it has one.
	 */
	@Override
	public void onDeath() 
	{
		if( !items.isEmpty() )
		{
			Random rand = new Random();
			location.addItemToTile( items.get( rand.nextInt( items.size() ) ) );
		}
		
		location.removeEntityFromTile( this );
	}

}
