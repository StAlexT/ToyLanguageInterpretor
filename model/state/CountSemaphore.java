package model.state;

import javafx.util.Pair;
import model.exception.ToyLanguageException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountSemaphore<H1, H0, H2> implements CountSemaphoreInterface<H1, H0, H2>{
    Map<H1, Pair<H0, List<H2>>> countSemaphore;
    Pair<H0, List<H2>> pair;
    List<H2> list_elem;
    private final Lock lock = new ReentrantLock();
    private int freelocation;
    public CountSemaphore(){
        countSemaphore = new HashMap<>();
        freelocation = 1;
    }

    public void update(H1 id, H0 maxPermits, List<H2> list) {
        lock.lock();
        try {
            if (!countSemaphore.containsKey(id)) {
                throw new ToyLanguageException("Semaphore id not found in update");
            }
            Pair<H0, List<H2>> newPair = new Pair<>(maxPermits, list);
            countSemaphore.put(id, newPair);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(H1 id, H0 var, H2 exp) {
        lock.lock();
        try {
            if (exp.equals(-10000)){
                list_elem = new ArrayList<>();
                pair = new Pair<>(var, list_elem);
                countSemaphore.put(id, pair);
            } else if (countSemaphore.containsKey(id)) {
                list_elem = countSemaphore.get(id).getValue();
                list_elem.add(exp);
            } else {
                list_elem = new ArrayList<>();
                list_elem.add(exp);
                pair = new Pair<>(var, list_elem);
                countSemaphore.put(id, pair);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isDefined(H1 id) {
        lock.lock();
        try {
            return countSemaphore.containsKey(id);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Pair<H0, List<H2>> getValue(H1 id) {
        lock.lock();
        try {
            return countSemaphore.get(id);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public CountSemaphoreInterface deepCopy() {
        return this;
    }

    @Override
    public H1 getLocation(H2 elem) {
        for(H1 id : countSemaphore.keySet()) {
            Pair<H0, List<H2>> listPair = countSemaphore.get(id);
            if (listPair.getValue().contains(elem))
                return id;
        }
        return null;
    }

    @Override
    public int getFreeLocation() {
       return freelocation++;
    }

    @Override
    public Map getAll() {
        return new HashMap<>(countSemaphore);
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Semaphore Table: \n");
        Set<H1> setOfKeysTable = countSemaphore.keySet();
        Iterator<H1> iterator = setOfKeysTable.iterator();
        while (iterator.hasNext()){
            H1 key = iterator.next();
            stringBuilder.append(key).append("-> (").append(countSemaphore.get(key).getKey()).append(" , ").append(countSemaphore.get(key).getValue()).append(")\n");
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
