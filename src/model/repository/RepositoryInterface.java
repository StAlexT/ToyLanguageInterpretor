package model.repository;

import model.exception.ToyLanguageException;
import model.state.ProgramState;

public interface RepositoryInterface {
    ProgramState getCurrentProgramState();
    void addProgramState(ProgramState state);
    void logProgramState(ProgramState state) throws ToyLanguageException;
}
