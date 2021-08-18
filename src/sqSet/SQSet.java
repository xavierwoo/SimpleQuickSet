package sqSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SQSet<T extends SQSetElement> {
    final private int[] hash_set;
    final private ArrayList<T> list = new ArrayList<>();

    public SQSet(int max_index){
        hash_set = new int[max_index+1];
        Arrays.fill(hash_set, -1);
    }

    public void add(T obj){
        var key = obj.getSQSetKey();
        if(key >= hash_set.length){
            throw new Error("Set capacity is not enough");
        }
        if(hash_set[key] != -1){
            list.set(hash_set[key], obj);
        }else{
            hash_set[key] = list.size();
            list.add(obj);
        }
    }

    public void remove(int key){
        if(hash_set[key] != -1){
            var last_elem = list.get(list.size()-1);
            list.set(hash_set[key], last_elem);
            hash_set[last_elem.getSQSetKey()] = hash_set[key];
            hash_set[key] = -1;

            list.remove(list.size()-1);
        }
    }

    public void remove(T obj){
        remove(obj.getSQSetKey());
    }

    public List<T> valueList(){
        return Collections.unmodifiableList(list);
    }

    public int size(){
        return list.size();
    }

    public boolean contains(T o){
        return hash_set[o.getSQSetKey()] != -1;
    }

    public boolean equals(SQSet o){
        if(size() != o.size()){
            return false;
        }
        for(var elem : list){
            if(!o.contains(elem)){
                return false;
            }
        }
        return true;
    }
}
