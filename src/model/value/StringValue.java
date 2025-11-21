package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value{
    String value;

    public StringValue(String val) {
        this.value = val;
    }

    public String getVal() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringValue && ((StringValue) other).value.equals(this.value);//??
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }
}
