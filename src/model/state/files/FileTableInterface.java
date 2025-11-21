package model.state.files;

import model.exception.ToyLanguageException;

import java.util.Map;

public interface FileTableInterface<K, V> {
    void add(K key, V value) throws ToyLanguageException;
    void remove(K key) throws ToyLanguageException;
    boolean isDefined(K key);
    V lookup(K key) throws ToyLanguageException;
    Map<K, V> getAll();
}
