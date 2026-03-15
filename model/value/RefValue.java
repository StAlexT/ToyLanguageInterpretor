package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue v) {
            if(address == v.address && locationType.equals(v.locationType))
                return true;
            else
                return false;
        }
        return false;
    }
}

