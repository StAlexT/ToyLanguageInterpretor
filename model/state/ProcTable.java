package model.state;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.statement.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcTable implements ProcTableInterface {
    private final Map<String, Pair<List<String>, Statement>> table;

    public ProcTable() {
        table = new HashMap<>();
    }

    @Override
    public void add(String name, Pair<List<String>, Statement> proc) throws ToyLanguageException {
        if (table.containsKey(name))
            throw new ToyLanguageException("Procedure " + name + " already defined");
        table.put(name, proc);
    }

    @Override
    public boolean isDefined(String name) {
        return table.containsKey(name);
    }

    @Override
    public Pair<List<String>, Statement> get(String name) throws ToyLanguageException {
        if (!table.containsKey(name))
            throw new ToyLanguageException("Procedure " + name + " not defined");
        return table.get(name);
    }

    @Override
    public void update(String name, Pair<List<String>, Statement> proc) throws ToyLanguageException {
        if (!table.containsKey(name))
            throw new ToyLanguageException("Procedure " + name + " not defined");
        table.put(name, proc);
    }

    @Override
    public String toString() {
        return table.toString();
    }
}
