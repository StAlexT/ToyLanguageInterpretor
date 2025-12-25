package model.state;
import model.type.Type;

import java.util.Map;

public interface SymbolTableInterface<T2, T3>{
    void add(T2 id, T3 elem);
    void delete(T2 id);
    boolean isDefined(T2 id);
    T3 getValue(T2 id);
    void update(T2 id, T3 new_elem);
    boolean valueExists(T3 elem);
    SymbolTableInterface<T2, T3> deepCopy();

    Map<T2, T3> getAll();
}
