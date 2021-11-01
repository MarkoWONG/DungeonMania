# Rules
* Don’t assume something that will make your implementation easier
* Don’t directly contradict the spec

# Assumptions:
* Mercenaries, using the movement of the character, can go through portals.
    * Uncertain at this point whether Mercenaries will even enter a portal anyway.
* Mercenaries will spawn every 30 ticks at the player spawn - ‘periodically’
* Boulders can be pushed through portals
* Players exit portals on the same tile as the portal, on a higher visual layer than the portal.
    * [Spec does not imply any specific portal behaviour, so this is fine.]
* On Character death, the game will immediately restart (For Milestone 1)
* Mercenaries will not move if the player moves to their square (Players move first)
* There are two opportunities for characters to fight in a tick
    * The player moves before other entities, and as such will be able to fight them then
    * But if an entity moves into the same square as the character they will also fight
* When a dungeon is created with no goals, the dungeon can never be ‘won’, and will go until quit or the character dies.
* Bow/Sword durability = after 3 uses it breaks (removed from inventory)
* Shield durability = a random whole number between and including 2-5 indicating the number of uses before breaking (removed from inventory)
* Multiple Swords, Bows, Shields and Armor do not stack, having multiple of any of them is no different to having one 
    * But, when the durability on any of these runs out, the next one will be used
* Shield effect = cut enemy attack in half
* Mercenaries will not follow a player (or run away from them during an invincibility potion) if the player is invisible. Mercenaries will stand still if the player is invisible.
    * Not implemented in Milestone 2
* Mobs can’t pick up any items, they can only spawn with it
* Zombies and Mercenaries have a 20% chance of spawning with armour 
* When armoured mob drop their armour(defeated in battle) the armour is new 
* Zombie Toast Spawner, spawns zombies in a random valid adjacent tile
* If a boulder is already on a switch and the player places a bomb cardinally adjacent(no diagonals) it still explodes when the player moves one tick (explodes in the next tick)
* When the boulder is pushed onto the switch when there is a bomb cardinally adjacent then it explodes instantly (same tick as when the boulder is on the switch)
* Boulders can only be pushed onto switches and empty spaces
* zombieSpawner spawns zombies on top of spawner(toaster)
* Entity_id is assigned a random UUID on creation
* InvincibilityPotion will last for 5 ticks
* InvisibilityPotion will last for 5 ticks.
* All potions are removed from the player’s inventory when used.
* When any entity is revived, they will be on the same square they died on, such as via the One Ring

# Entity Statistics
* PlayerCharacter: 20 (Standard) 6 (Hard), Base AD = 1	→ 4 damage at 20 health
* Zombie: health = 10, Base AD = 2 		→ 2 damage if at full health
* Spider: health = 5, Base AD = 6		→ 3 damage if at full health
* Mercenary: health = 15, Base AD = 4	→ 6 damage if at full health
* Sword: AD Increase = 2
* Bow: AD Multiplier = 2
* Armour: Damage taken multiplier = 0.5	
* Shield: Damage taken multiplier = 0.5

# Layer Rank:
* Lowest(switch, Exit) = 0
* Second Lowest (Collectables) = 40
* Middle (player) = 50
* Third highest(static_Ent) = 80
* Second highest(spider) = 90
* Highest(boulder & placedBomb) = 100
