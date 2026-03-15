package model.state;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface CyclicBarrierInterface{
    void add(Integer id, Pair<Integer, List<Integer>> value);
    boolean isDefined(Integer id);
    Pair<Integer, List<Integer>> getValue(Integer id);
    CyclicBarrier deepCopy();
    int getFreeLocation();
    Map<Integer, Pair<Integer, List<Integer>>> getAll();
    void lock();
    void unlock();
    void update(Integer id, Pair<Integer, List<Integer>> value);
}
