package model.state;

import java.util.Map;

public interface HeapRefInterface<H1, H2>{
     int add(H2 cont);
     H2 getValue(H1 addr);
     boolean contains(H1 addr);
     void setContent(Map<H1, H2> content);
     Map<H1, H2> getAll();
    void update(H1 address, H2 valueExp);
}
