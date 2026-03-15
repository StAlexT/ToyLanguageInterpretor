package model.repository;

import model.exception.ToyLanguageException;
import model.state.ProgramState;

import java.util.List;

public interface RepositoryInterface {
//    ProgramState getCurrentProgramState();
    List<ProgramState> getListProgramState();
    void addProgramState(ProgramState state);
    void logProgramState(ProgramState state) throws ToyLanguageException;
    void setProgramList(List<ProgramState> listProgramState);
}
