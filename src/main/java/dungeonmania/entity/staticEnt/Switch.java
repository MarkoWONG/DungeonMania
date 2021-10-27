package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Switch extends StaticEntity{
    private boolean switchOn;
    public Switch(Position position){
        super(new Position(position.getX(), position.getY(), 0));    
        switchOn = false; 
    }

    @Override
    public void interact(Boulder boulder){
        // turn on switch
        switchOn = true;
    }

    public boolean getSwitchOn() {
        return this.switchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        this.switchOn = switchOn;
    }

}