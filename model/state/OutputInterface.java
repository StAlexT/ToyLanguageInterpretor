package model.state;
import java.util.List;

public interface OutputInterface<T1> {
    void add(T1 elem);
    void delete(T1 elem);
    List<T1> getAll();
}