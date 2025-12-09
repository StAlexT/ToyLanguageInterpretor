package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements Statement{ //opens the file and adds filename and buffer to the fileTable
    private final Expression expression;

    public OpenReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        Value val = expression.eval(state.getSymbolTable(), state.getHeap());
        if (!(val.getType() instanceof StringType))
            throw new ToyLanguageException("Expression is not a string.");

        StringValue filename = (StringValue) val;
        if (state.getFileTable().isDefined(filename))
            throw new ToyLanguageException("File already opened: " + filename.getVal());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename.getVal()));
            state.getFileTable().add(filename, reader); //put the buffer and filename in the fileTable
        } catch (IOException e) {
            throw new ToyLanguageException("Could not open file: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new OpenReadFileStatement(expression.deepCopy());
    }

    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}

