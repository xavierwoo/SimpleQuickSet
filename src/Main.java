import sqSet.SQSetElement;
import sqSet.SQSet;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        var max_index = 900000;
        var data = new ArrayList<Test>();
        var request = new ArrayList<Test>();
        Random r = new Random(0);

        for(int i=0; i<max_index; ++i){
            if(r.nextInt(10) > 3)continue;//make the numbers sparse
            data.add(new Test(i));
        }
        System.out.println("data size:" + data.size());
        for(int i=0; i<max_index; ++i){
            if(r.nextInt(10) > 3)continue;//make the numbers sparse
            request.add(new Test(i));
        }
        System.out.println("request size:" + request.size());


        for(int i=0; i<5; ++i) {
            System.out.println("Test No." + i);
            Collections.shuffle(data, r);
            test_simple_set(max_index, data, request);
            test_tree_set(data, request);
            test_hash_set(data, request);
        }
    }

    public static void test_hash_set(ArrayList<Test> data, ArrayList<Test> request){
        //test hash set
        var start_t = System.currentTimeMillis();
        HashSet<Test> h_set = new HashSet<>();
        for(var t : data){
            h_set.add(t);
        }
        long sum_hs = 0;
        for(var e : h_set){
            sum_hs += e.getSQSetKey();
        }
        int count = 0;
        for(var e : request){
            if(h_set.contains(e)){
                count++;
            }
        }

        for(var t:data){
            h_set.remove(t);
        }
        System.out.println(sum_hs + " " + count + ", hash set time: " + (System.currentTimeMillis() - start_t));
    }

    public static void test_tree_set(ArrayList<Test> data, ArrayList<Test> request){
        //Test Tree set
        var start_t = System.currentTimeMillis();
        TreeSet<Test> t_set = new TreeSet<>(Comparator.comparingInt(Test::getSQSetKey));
        for(var t : data){
            t_set.add(t);
        }
        long sum_ts = 0;
        for(var e : t_set){
            sum_ts += e.getSQSetKey();
        }
        int count = 0;
        for(var e : request){
            if(t_set.contains(e)){
                count++;
            }
        }
        for(var t:data){
            t_set.remove(t);
        }
        System.out.println(sum_ts + " " + count + ", tree set time: " + (System.currentTimeMillis() - start_t));
    }

    public static void test_simple_set(int max_index, ArrayList<Test> data, ArrayList<Test> request){
        //Test SimpleSet
        long start_t = System.currentTimeMillis();
        SQSet<Test> set = new SQSet<>(max_index);
        for(var t : data){
            set.add(t);
        }
        long sum_ss = 0;
        for(var e : set.valueList()){
            sum_ss += e.getSQSetKey();
        }
        int count = 0;
        for(var e : request){
            if(set.contains(e)){
                count++;
            }
        }
        for(var t : data){
            set.remove(t);
        }
        System.out.println(sum_ss +" " + count + ", simple set time: " + (System.currentTimeMillis() - start_t));
        if(set.size() != 0){
            throw new Error("simple set error");
        }
    }

    public static class Test implements SQSetElement {
        int key;

        public Test(int k){
            key = k;
        }
        @Override
        public int getSQSetKey() {
            return key;
        }

        @Override
        public String toString(){
            return String.valueOf(key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return key == test.key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
