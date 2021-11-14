## Rules
* Don’t assume something that will make your implementation easier
* Don’t directly contradict the spec

## Assumptions:
* Mercenaries, using the movement of the character, can go through portals.
* Mercenaries will spawn every 30 ticks at the player spawn - ‘periodically’
* Mercenaries have a battle radius of 5 squares
* Boulders can be pushed through portals
* Players exit portals on the same tile as the portal, on a higher visual layer than the portal.
* On Character death, the game will immediately restart (For Milestone 1)
* Mercenaries will not move if the player moves to their square (Players move first)
* There are two opportunities for characters to fight in a tick
    * The player moves before other entities, and as such will be able to fight them then
    * But if an entity moves into the same square as the character they will also fight
* When a dungeon is created with no goals, the dungeon can never be ‘won’, and will go until quit or the character dies.
* Bow/Sword/shield durability = after 3 uses it breaks (removed from inventory)
* Multiple Swords, Bows, Shields and Armor do not stack, having multiple of any of them is no different to having one
    * But, when the durability on any of these runs out, the next one will be used
* Shield effect = cut enemy attack in half
* Mercenaries will not follow a player (or run away from them during an invincibility potion) if the player is invisible. Mercenaries will stand still if the player is invisible.
* Spiders can’t teleport
* Mobs can’t pick up any items, they can only spawn with it
* Zombies and Mercenaries have a 20% chance of spawning with armour 
* When armoured mob drop their armour(defeated in battle) the armour is new 
* Zombie Toast Spawner, spawns zombies in a random valid adjacent tile
* If a boulder is already on a switch and the player places a bomb cardinally adjacent(no diagonals) it still explodes when the player moves one tick (explodes in the next tick)
* When the boulder is pushed onto the switch when there is a bomb cardinally adjacent then it explodes instantly (same tick as when the boulder is on the switch)
* Boulders can only be pushed onto switches and empty spaces
* zombieSpawner doesn’t spawns zombies if there is no valid positions to spawn
* Entity_id is assigned a random UUID on creation
* InvincibilityPotion will last for 5 ticks
* InvisibilityPotion will last for 5 ticks.
* The potion is removed from the player’s inventory when used.
* When any entity is revived, they will be on the same square they died on, such as via the One Ring
* There is a maximum of ten spiders per map, with up to 5 spawning initially in addition to any in the map
* Midnight Armour gives +5 attack damage and reduces damage taken by 5.
* If a mercenary cannot find a route to the player, it will not move
* Sceptre can be used an unlimited number of times
* Movement factor is by default 1, and can only be an int, so movement is calculated in whole numbers
* Swamp tile has a movement factor of 2
* Anduril does increased attack damage to all mobs
* Bomb explode and destroy everything (except Player) 1 tile away from bomb
* A mercenary or assassin being mind controlled using the sceptre will play a role in satisfying the `enemies` goal.
* Our base price for mercenaries and assassins is 1 treasure
* Sun Stones are not used up when building, but it does take priority over treasure 
* Sceptre are activated via a interact (click) and the effect duration wares off according to the ticks
* The chance of getting a rare entity drop on mob kill is 1/5 with a 1/10 chance of getting the one ring and 1/10 chance of getting anduril (cannot get both from one mob)
* If the difficulty is not hard, any hydra will be replaced by a zombie
* Zombie walk over portals

## Entity Statistics
* PlayerCharacter: 20 (Standard) 6 (Hard), Base AD = 1	→ 4 damage at 20 health
* Zombie: health = 10, Base AD = 2 		→ 2 damage if at full health
* Spider: health = 5, Base AD = 6		→ 3 damage if at full health
* Mercenary: health = 15, Base AD = 4	→ 6 damage if at full health
* Assassin: health = 15, Base AD = 10            → 15 damage if at full health
* Sword: AD Increase = 2
* Bow: AD Multiplier = 2
* Armour: Damage taken multiplier = 0.5	
* Shield: Damage taken multiplier = 0.5

## Layer Rank:
* Lowest(switch, Exit, floor tiles) = 0
* Second Lowest (Collectables) = 40
* Third lowest (portal)=  45
* Middle (player) = 50
* Third highest(static_Ent) = 80
* Second highest(spider) = 90
* Highest(boulder & placedBomb) = 100
