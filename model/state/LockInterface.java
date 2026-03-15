package model.state;

import java.util.Map;

public interface LockInterface<L1, L2>{
    void add(L1 id, L2 elem);
    boolean isDefined(L1 id);
    L2 getValue(L1 id);
    void update(L1 id, L2 new_elem);
    boolean valueExists(L2 elem);
    Map<L1, L2> getAll();
    int getNextFreeLocation();
    void lock();
    void unlock();
}
