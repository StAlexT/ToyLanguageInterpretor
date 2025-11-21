package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.IntType;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement { //reads one line of a file and stores the int value from there in variableName
    private final Expression expression;
    private final String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        if (!state.getSymbolTable().isDefined(variableName))
            throw new ToyLanguageException("Variable not declared: " + variableName);

        Value varVal = state.getSymbolTable().getValue(variableName);
        if (!(varVal.getType().equals(new IntType())))
            throw new ToyLanguageException(variableName + " is not of type int");

        Value fileVal = expression.eval(state.getSymbolTable());
        if (!(fileVal instanceof StringValue))
            throw new ToyLanguageException("Expression is not a string value");

        StringValue filename = (StringValue) fileVal;
        BufferedReader reader = state.getFileTable().lookup(filename);

        try {
            String line = reader.readLine();
            if (line == null) {
                state.getSymbolTable().update(variableName, new StringValue(""));
            } else {
                try {
                    int value = Integer.parseInt(line);
                    state.getSymbolTable().update(variableName, new IntegerValue(value));
                } catch (NumberFormatException e) {
                    // Not an int, treat as string
                    state.getSymbolTable().update(variableName, new StringValue(line));
                }
            }

//            String line = reader.readLine();
//            int value = (line == null) ? 0 : Integer.parseInt(line);
//            //looks nicer than an if, but does the same/ interesting way of being like: value is 0 if line is null, else value = (int) line
//            state.getSymbolTable().update(variableName, new IntegerValue(value));
        } catch (IOException e) {
            throw new ToyLanguageException("Error reading from file: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }
}

