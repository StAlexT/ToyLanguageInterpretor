package model.state;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.statement.Statement;

import java.util.List;

public interface ProcTableInterface {
    void add(String name, Pair<List<String>, Statement> proc) throws ToyLanguageException;
    boolean isDefined(String name);
    Pair<List<String>, Statement> get(String name) throws ToyLanguageException;
    void update(String name, Pair<List<String>, Statement> proc) throws ToyLanguageException;
}

