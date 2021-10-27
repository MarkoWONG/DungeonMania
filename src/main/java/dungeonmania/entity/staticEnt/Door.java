package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Door extends StaticEntity{
    private Boolean isOpen;
    public Door(Position position){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE));   
        this.isOpen = false;  
    }

    public void unlockDoor(){
        this.isOpen = true;
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), 0));
    }

    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}