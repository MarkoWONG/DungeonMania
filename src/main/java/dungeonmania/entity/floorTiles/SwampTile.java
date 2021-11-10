package dungeonmania.entity.floorTiles;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    public SwampTile(Position position) {
        super(position);
        super.setMovementFactor(2.0);
    }

    @Override
    public String getType() {
        return "swamp_tile";
    }
    
}
