package model.state;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface CountSemaphoreInterface<H1, H0, H2>{
    void add(H1 id, H0 var, H2 exp);
    boolean isDefined(H1 id);
    Pair<H0, List<H2>> getValue(H1 id);
    CountSemaphoreInterface<H1, H0, H2> deepCopy();
    H1 getLocation(H2 elem);
    int getFreeLocation();
    Map<H1, H2> getAll();
     void lock();
     void unlock();
     void update(H1 id, H0 maxPermits, List<H2> list);
}
