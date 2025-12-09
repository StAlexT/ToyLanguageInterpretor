package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements Statement{ //closes the file and removes it from FileTable
    private final Expression expression;

    public CloseReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        Value val = expression.eval(state.getSymbolTable(), state.getHeap());
        if (!(val instanceof StringValue))
            throw new ToyLanguageException("Expression is not a string value");

        StringValue filename = (StringValue) val;
        BufferedReader reader = state.getFileTable().lookup(filename); //get the buffer of the file that was opened

        try {
            reader.close();
            state.getFileTable().remove(filename);
        } catch (IOException e) {
            throw new ToyLanguageException("Error closing file: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CloseReadFileStatement(expression.deepCopy());
    }

    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
