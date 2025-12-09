package model.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HeapRef<H1, H2> implements HeapRefInterface<H1, H2> {
//ref means reference (like pointers do and shit)
    Map<H1, H2> table;
    int address;

    public HeapRef(){
        table = new HashMap<>();
        address = 1;
    }

    @Override
    public int add(H2 cont) {
        int current = address;
        table.put((H1) Integer.valueOf(address), cont);
        address++;
        return current;
    }



    @Override
    public H2 getValue(H1 addr) {
        return table.get(addr);
    }

    @Override
    public boolean contains(H1 addr) {
        if(table.containsKey(addr))
            return true;
        return false;
    }

    @Override
    public void setContent(Map<H1, H2> content) {
        table.clear();
        table.putAll(content);
    }

    @Override
    public Map getAll() {
        return table;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Heap table: \n");
        Set<H1> setKeys = table.keySet();
        Iterator<H1> iterator = setKeys.iterator();
        while (iterator.hasNext()){
            H1 key = iterator.next();
            stringBuilder.append(key).append("->").append(table.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void update(H1 addr, H2 new_el){
        table.replace(addr, new_el);
    }
}
