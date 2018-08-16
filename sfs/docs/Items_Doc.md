# Items
This doc page covers the principles behind the item system
to enable an easy use of the items "framework".

## Overview
Every item should extend the `Item` class since it gives some
basic attributes like `name`, `description` and an `owner` which
is an `Entity`.
For further distinguishing between items the interfaces `IWeapon`
and `IUsable` can be used. These interfaces define functionality
that an item of that type should implement.

For example the class `BaseWeapon` implements `IWeapon` and therefore
offers the methods `getAttackDamage` and `attack`:
```java
	@Override
	public boolean attack( Entity targetEntity ) 
	{
		int entityHealthBefore = targetEntity.getHealth();
		targetEntity.handleAttack( this.attackDamage );
		
		/* Look up if damage was successfully dealt. */
		return targetEntity.getHealth() < entityHealthBefore;
	}
	
	@Override
	public int getAttackDamage() 
	{
		return attackDamage;
	}
```

The `BaseWeapon` class is a simple weapon that has no special effects and only
deals the attack damage.

To add an item to the game you can either add the item to an `Entity` or set the owner
of the item to `null` and add it to a `Tile`.
Example: 
Add item to a tile:
```java
    Tile tile2 =new Tile("test room 2");
	BaseWeapon bw = new BaseWeapon( 50, null );
    tile2.addItemToTile( bw );
```

Add item to entity:
```java
	Tile tile3 = new Tile( "test room 3" );
	BaseEnemy be = new BaseEnemy(100, 20, tile3);
	be.getItems().add( new BaseWeapon( 50, be ) );
```

So always be aware that there is a double sided link between an item and the owner
and if the item is on a tile it needs to be added to this tile and the owner should
be `null`.