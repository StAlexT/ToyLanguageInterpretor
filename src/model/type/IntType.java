package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntType implements Type{

    public boolean equals(Object another){
        if(another instanceof IntType)
            return true;
        else 
            return false;
    }

    @Override
    public String toString(){
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
