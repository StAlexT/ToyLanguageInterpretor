package model.state;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ToySemaphoreInterface {
    void add(Integer id, Pair<Integer, Pair<List<Integer>, Integer>> value);
    boolean isDefined(Integer id);
    Pair<Integer, Pair<List<Integer>, Integer>> getValue(Integer id);
    ToySemaphore deepCopy();
    int getFreeLocation();
    Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> getAll();
    void lock();
    void unlock();
    void update(Integer id, Pair<Integer, Pair<List<Integer>, Integer>> value);
}
