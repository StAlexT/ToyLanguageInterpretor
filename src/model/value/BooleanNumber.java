package model.value;

import model.type.BoolType;
import model.type.Type;

public class BooleanNumber implements Value {
    boolean nr;

    public BooleanNumber(boolean nmb){
        nr = nmb;
    }

    public boolean getVal() {
        return nr;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return  new BooleanNumber(nr);
    }

    @Override
    public String toString(){
        return String.valueOf(nr);
    }
}
