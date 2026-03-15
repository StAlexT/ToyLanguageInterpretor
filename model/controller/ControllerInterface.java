package model.controller;

import model.exception.ToyLanguageException;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface ControllerInterface {
//    ProgramState oneStep(ProgramState state) throws ToyLanguageException;
    List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList);
    void allSteps() throws ToyLanguageException, IOException;
    RepositoryInterface getRepo();
}
