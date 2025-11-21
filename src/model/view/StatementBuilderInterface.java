package model.view;

import model.exception.ToyLanguageException;
import model.statement.Statement;

public interface StatementBuilderInterface {
    String typeStatement(String statement);
    Statement createStatement(String statement) throws ToyLanguageException;
//    void start() throws ToyLanguageException;
}
