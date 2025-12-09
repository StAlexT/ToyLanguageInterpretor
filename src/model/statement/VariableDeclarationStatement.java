package model.statement;
import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.type.*;
import model.value.BooleanNumber;
import model.value.IntegerValue;
import model.value.RefValue;
import model.value.StringValue;

public class VariableDeclarationStatement implements Statement{
    String name;
    Type type;

    public VariableDeclarationStatement(String st_name, Type st_type){
        name = st_name;
        type = st_type;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (state.getSymbolTable().isDefined(name))
            throw new ToyLanguageException("Variable already declared");
        if (type.equals(new IntType()))
            state.getSymbolTable().add(name, new IntegerValue(0));
        else if (type.equals(new BoolType()))
            state.getSymbolTable().add(name, new BooleanNumber(false));
        else if (type.equals(new StringType())) {
            state.getSymbolTable().add(name, new StringValue(""));
        } else if( type instanceof RefType refType){
            state.getSymbolTable().add(name, refType.defaultValue());
        }
        return state;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(name, type.deepCopy());
    }
}
