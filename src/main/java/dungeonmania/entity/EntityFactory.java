package dungeonmania.entity;
import dungeonmania.entity.staticEnt.Wall;
import java.util.Locale;


public class EntityFactory {
    // public static Entity getEntity() {
	// 	String platform = System.getProperty("os.name");
	// 	return getEntity(platform);
	// }

	public static Entity getEntity(String entityType) {
		Entity ent = null;

		switch (entityType.toLowerCase(Locale.ROOT)) {
            // case "player":
            //     return makePlayer();
            case "wall":
                ent = new Wall();
            // case "exit":
            //     return makeExit();
            // case "boulder":
            //     return makeBoulder();
            // case "floorswitch":
            //     return makeFloorSwitch();
            // case "door":
            //     return makeDoor();
            // case "portal":
            //     return makePortal();
            // case "toaster":
            //     return makeToaster();
            // case "mercenary":
            //     return makeMercenary();
            // case "zombie":
            //     return makeZombie();
            // case "spider":
            //     return makeSpider();
            // case "treasure":
            //     return makeTreasure();
            // case "key":
            //     return makeKey();
            // case "healthpotion":
            //     return makeHealthPotion();
            // case "invincibilitypotion":
            //     return makeInvincibilityPotion();
            // case "invisibilitypotion":
            //     return makeInvisibilityPotion();
            // case "wood":
            //     return makeWood();
            // case "arrow":
            //     return makeArrow();
            // case "bomb":
            //     return makeBomb();
            // case "sword":
            //     return makeSword();
            // case "armour":
            //     return makeArmour();
            // case "onering":
            //     return makeOneRing();
        }

		return ent;
	}


}
