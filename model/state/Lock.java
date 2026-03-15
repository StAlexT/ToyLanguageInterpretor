package model.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Lock<L1, L2> implements LockInterface<L1, L2> {
    Map<L1, L2> lockTable;
    int nextFreeLoc;
    private final java.util.concurrent.locks.Lock lock = new ReentrantLock();

    public Lock(){
        lockTable = new HashMap<>();
        nextFreeLoc = 0;
    }

    @Override
    public void add(L1 id, L2 elem) {
        lock.lock();
        try {
            lockTable.put(id, elem);
        }finally {
            lock.unlock();
        }

    }


    @Override
    public boolean isDefined(L1 id) {
        lock.lock();
        try {
            return lockTable.containsKey(id);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public L2 getValue(L1 id) {
        lock.lock();
        try {
            return lockTable.get(id);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void update(L1 id, L2 new_elem) {
        lock.lock();
        try {
            lockTable.replace(id, new_elem);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean valueExists(L2 elem) {
        lock.lock();
        try {
            return lockTable.containsValue(elem);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public Map<L1, L2> getAll() {
        return new HashMap<>(lockTable);
    }

    @Override
    public int getNextFreeLocation(){
        lock.lock();
        try {
            return nextFreeLoc++;
        }finally {
            lock.unlock();
        }
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Lock Table: \n");
        Set<L1> setOfKeysSymbolTable = lockTable.keySet();
        Iterator<L1> iterator = setOfKeysSymbolTable.iterator();
        while (iterator.hasNext()){
            L1 key = iterator.next();
            stringBuilder.append(key).append("->").append(lockTable.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }
}
