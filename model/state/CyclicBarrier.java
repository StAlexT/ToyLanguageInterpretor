package model.state;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrier implements CyclicBarrierInterface{
    Map<Integer, Pair<Integer, List<Integer>>> cyclicBarrier;
    private int freeLocation;
    private final Lock lock = new ReentrantLock();

    public CyclicBarrier() {
        cyclicBarrier = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public void add(Integer id, Pair<Integer, List<Integer>> value) {
        lock.lock();
        try {
            cyclicBarrier.put(id, value);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isDefined(Integer id) {
        lock.lock();
        try {
            return cyclicBarrier.containsKey(id);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public Pair<Integer, List<Integer>> getValue(Integer id) {
        lock.lock();
        try {
            return cyclicBarrier.get(id);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public CyclicBarrier deepCopy() {
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
    public Map<Integer, Pair<Integer, List<Integer>>> getAll() {
        return new HashMap<>(cyclicBarrier);
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
    public void update(Integer id, Pair<Integer, List<Integer>> value) {
        lock.lock();
        try {
            cyclicBarrier.replace(id, value);
        }finally {
            lock.unlock();
        }
    }
}
