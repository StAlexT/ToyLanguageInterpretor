package model.state;

import java.util.*;

public class SymbolTable<T2, T3> implements SymbolTableInterface<T2, T3>{
    Map<T2, T3>symbolTable;

    public SymbolTable(){
        symbolTable = new Hashtable<>();
    }

    @Override
    public void add(T2 id, T3 elem) {
        symbolTable.put(id, elem);
    }

    @Override
    public void delete(T2 id) {
        symbolTable.remove(id);
    }

    @Override
    public boolean isDefined(T2 id) {
       if(symbolTable.containsKey(id))
           return true;
       return false;
    }

    @Override
    public T3 getValue(T2 id) {
        return symbolTable.get(id);
    }

    @Override
    public void update(T2 id, T3 new_elem) {
        symbolTable.replace(id, new_elem);
    }

    @Override
    public boolean valueExists(T3 elem) {
        if(symbolTable.containsValue(elem))
            return true;
        return false;
    }

    @Override
    public Map<T2, T3> getAll() {
        return new HashMap<>(symbolTable);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Symbol Table: \n");
        Set<T2> setOfKeysSymbolTable = symbolTable.keySet();
        Iterator<T2> iterator = setOfKeysSymbolTable.iterator();
        while (iterator.hasNext()){
            T2 key = iterator.next();
            stringBuilder.append(key).append("->").append(symbolTable.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
