package model.type;

import model.value.RefValue;
import model.value.Value;

import javax.print.attribute.standard.MediaSize;

public class RefType implements Type {
    Type inner;

    public RefType(Type e){
        inner = e;
    }

    public Type getType(){
        return inner;
    }

    public Type getInner(){return inner;};
    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() { /// idfk maybe rewrite this
        return new RefType(inner);
    }

    public boolean equals(Object another) {
        if(another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    @Override
    public String toString(){
        return "Ref(" + inner.toString()+")";
    }
}
