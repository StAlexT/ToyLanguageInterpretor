package model.controller;

import model.exception.ToyLanguageException;
import model.state.ProgramState;

import java.io.IOException;

public interface ControllerInterface {
    ProgramState oneStep(ProgramState state) throws ToyLanguageException;
    void allSteps() throws ToyLanguageException, IOException;
}
