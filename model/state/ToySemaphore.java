package model.state;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ToySemaphore implements ToySemaphoreInterface {
    Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> toySemaphoreTable;
    private int freeLocation;
    private final Lock lock = new ReentrantLock();

    public ToySemaphore(){
        toySemaphoreTable = new HashMap<>();
        freeLocation=1;
    }

    @Override
    public void add(Integer id, Pair<Integer, Pair<List<Integer>, Integer>> value) {
        lock.lock();
        try {
            toySemaphoreTable.put(id, value);
        }finally {
            lock.unlock();
        }
    }


    @Override
    public boolean isDefined(Integer id) {
        lock.lock();
        try {
            return toySemaphoreTable.containsKey(id);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public Pair<Integer, Pair<List<Integer>, Integer>> getValue(Integer id) {
        lock.lock();
        try {
            return toySemaphoreTable.get(id);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public ToySemaphore deepCopy() {
        return this;
    }


    @Override
    public int getFreeLocation() {
        lock.lock();
        try {
            return freeLocation++;
        }finally {
            lock.unlock();
        }

    }

    @Override
    public Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> getAll() {
        return new HashMap<>(toySemaphoreTable);
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }

    @Override
    public void update(Integer id, Pair<Integer, Pair<List<Integer>, Integer>> value) {
        lock.lock();
        try {
            toySemaphoreTable.replace(id, value);
        }finally {
            lock.unlock();
        }
    }
}
