package model.state;
import java.util.List;

public interface ExecutionStackInterface<T> {
    void push(T elem);
    T pop();
    boolean isEmpty();
    List<T> getAll();
}
