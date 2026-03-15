package model.state;

import model.type.Type;

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
    public SymbolTableInterface<T2, T3> deepCopy() {
        SymbolTable<T2, T3> copy = new SymbolTable<>();
        copy.symbolTable = new Hashtable<>(this.symbolTable); // shallow copy of internal table
        return copy;
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

//
//import java.util.*;
//
//public class SymbolTable<T2, T3> implements SymbolTableInterface<T2, T3> {
//    private Map<T2, T3> symbolTable;
//    private SymbolTableInterface<T2, T3> parent; // new parent reference
//
//    // constructors
//    public SymbolTable() {
//        this.symbolTable = new Hashtable<>();
//        this.parent = null;
//    }
//
//    public SymbolTable(SymbolTableInterface<T2, T3> parent) {
//        this.symbolTable = new Hashtable<>();
//        this.parent = parent;
//    }
//
//    @Override
//    public void add(T2 id, T3 elem) {
//        symbolTable.put(id, elem);
//    }
//
//    @Override
//    public void delete(T2 id) {
//        symbolTable.remove(id);
//    }
//
//    @Override
//    public boolean isDefined(T2 id) {
//        return symbolTable.containsKey(id) || (parent != null && parent.isDefined(id));
//    }
//
//    @Override
//    public T3 getValue(T2 id) {
//        if (symbolTable.containsKey(id)) return symbolTable.get(id);
//        if (parent != null) return parent.getValue(id);
//        throw new RuntimeException("Variable " + id + " not defined");
//    }
//
//    @Override
//    public void update(T2 id, T3 new_elem) {
//        if (symbolTable.containsKey(id)) {
//            symbolTable.put(id, new_elem);
//        } else if (parent != null && parent.isDefined(id)) {
//            parent.update(id, new_elem);
//        } else {
//            throw new RuntimeException("Variable " + id + " not defined");
//        }
//    }
//
//    @Override
//    public boolean valueExists(T3 elem) {
//        return symbolTable.containsValue(elem) || (parent != null && parent.valueExists(elem));
//    }
//
//    @Override
//    public SymbolTableInterface<T2, T3> deepCopy() {
//        SymbolTable<T2, T3> copy = new SymbolTable<>(parent); // keep same parent
//        copy.symbolTable = new Hashtable<>(this.symbolTable);
//        return copy;
//    }
//
//    @Override
//    public Map<T2, T3> getAll() {
//        Map<T2, T3> all = new HashMap<>();
//        if (parent != null) all.putAll(parent.getAll());
//        all.putAll(symbolTable);
//        return all;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("Symbol Table: \n");
//        for (Map.Entry<T2, T3> entry : getAll().entrySet()) {
//            sb.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
//        }
//        return sb.toString();
//    }
//}
