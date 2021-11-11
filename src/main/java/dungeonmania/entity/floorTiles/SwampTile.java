package dungeonmania.entity.floorTiles;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    public SwampTile(Position position, int mov_factor) {
        super(position);
        super.setMovementFactor(mov_factor);
    }

    @Override
    public String getType() {
        return "swamp_tile";
    }
    
}
