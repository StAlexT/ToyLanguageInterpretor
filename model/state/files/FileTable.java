package model.state.files;

import model.exception.ToyLanguageException;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K, V> implements FileTableInterface<K, V>{ //keeps file name + buffer file
    private final Map<K, V> table = new HashMap<>();

    @Override
    public void add(K key, V value) throws ToyLanguageException {
        if (table.containsKey(key))
            throw new ToyLanguageException("File already open: " + key);
        table.put(key, value);
    }

    @Override
    public void remove(K key) throws ToyLanguageException {
        if (!table.containsKey(key))
            throw new ToyLanguageException("File not found: " + key);
        table.remove(key);
    }

    @Override
    public boolean isDefined(K key) {
        return table.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ToyLanguageException {
        if (!table.containsKey(key))
            throw new ToyLanguageException("No such file in FileTable: " + key);
        return table.get(key);
    }

    @Override
    public Map<K, V> getAll() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : table.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.isEmpty() ? "(empty)\n" : sb.toString();
    }

}
