package model.state;
import model.exception.ToyLanguageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExecutionStack<T> implements ExecutionStackInterface<T> {
    Stack<T> execStack;

    public ExecutionStack(){
        this.execStack = new Stack<>(); //how stack its initialised in java
    }
    @Override
    public void push(T elem) {
        execStack.push(elem);
    }

    @Override
    public T pop() {
        if(execStack.isEmpty())
            throw new ToyLanguageException("Stack is empty");
        return execStack.pop();
    }
    @Override
    public boolean isEmpty() {
        return execStack.isEmpty();
    }

    @Override
    public T peek() {
        if(execStack.isEmpty()) throw new RuntimeException("Empty stack");
        return execStack.get(execStack.size() - 1); // last element is top of stack
    }


    @Override
    public List<T> getAll() {
        return new ArrayList<>(execStack);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Execution stack:\n");
        for(T statement : execStack){
            stringBuilder.append(statement).append("\n");
        }
        return stringBuilder.toString();
    }

}
