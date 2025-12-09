package model.state;
import java.util.ArrayList;
import java.util.List;

public class Output<T1> implements OutputInterface<T1>{
    List<T1> outputList;

    public Output(){
        outputList = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Output list: \n");
        for(T1 elem : outputList){
            stringBuilder.append(elem).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void add(T1 elem) {
        outputList.add(elem);
    }

    @Override
    public void delete(T1 elem) {
        outputList.remove(elem);
    }

    @Override
    public List<T1> getAll() {
        return new ArrayList<>(outputList);
    }
}
