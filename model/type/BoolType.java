package model.type;

import model.value.BooleanNumber;
import model.value.Value;

public class BoolType implements Type{

    @Override
    public String toString(){
        return "bool";
    }

    @Override
    public boolean equals(Object anotherObject) {
        return anotherObject instanceof BoolType;
    }

    @Override
    public Value defaultValue() {
        return new BooleanNumber(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
