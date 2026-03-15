package model.value;

import model.type.IntType;
import model.type.Type;

public class IntegerValue implements Value {
    int value;

    public int getVal() {
        return value;
    }

    public IntegerValue(int v){
        value = v;
    }

    @Override
    public String toString(){
         return String.valueOf(value);
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntegerValue(value);
    }

}
