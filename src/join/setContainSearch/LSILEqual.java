package join.setContainSearch;

import BFTree.Tuple;
import utils.Utility;

import java.io.IOException;
import java.util.*;

public class LSILEqual {
    long filterPre = 0;
    long filterNum = 0;
    long candNum = 0;
    //invertedIndex
    private HashMap<Integer, ArrayList<Integer>> invertedIndex = null;

    long resultSize = 0;

    Map<Integer,Integer> elem_sum = new HashMap<>();
    int[] elemCount = new int[500*10000];
    ArrayList<Tuple> tuplesS;


    long[] id_len_map = null;
//    private HashMap<Integer, Integer> len_startId = null;
    int[] LenIDMap = null;
    public LSILEqual(ArrayList<Tuple> tupleS) {
        this.tuplesS = tupleS;
        LenIDMap = new int[tuplesS.get(tuplesS.size()-1).setSize + 2];
        this.invertedIndex = Utility.buildInvertedIndex(tupleS);
        Arrays.fill(elemCount,0);
        for (Tuple t : tupleS){
            for (Integer e : t.setElements){
                elemCount[e]++;
                if (elem_sum.containsKey(e)){
                    elem_sum.put(e,elem_sum.get(e) + 1);
                }else {
                    elem_sum.put(e,1);
                }
            }
        }
        createLenIDMap();
    }
    public void createLenIDMap(){
        id_len_map = new long[tuplesS.size()+1];
//        len_startId = new HashMap<>();
        int len = 0;
        int indexPre = 0;
        int indexNext = 0;
        for (Tuple t : tuplesS){
            int setSize = t.setSize;
            id_len_map[t.tupleID]=t.setSize;
            if (setSize > len){
                LenIDMap[setSize] = t.tupleID;
//                len_startId.put(setSize,t.tupleID);
                indexNext = setSize+1;
                for (int j = setSize-1; j >= indexPre; j--){
                    LenIDMap[j] = t.tupleID;
                }
                len = setSize;
            }else{
                indexPre = indexNext;

            }
        }
        LenIDMap[LenIDMap.length-1] = LenIDMap[LenIDMap.length-2]+1;

    }

    public void searchLSIL_Equal(TupleInteger tuple){
        // Sorting the list lengths in ascending order
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        //Shortest list
        List<Integer> result = invertedIndex.get(tuple.setElements[0]);

        //Binary search for the starting ID of equal length queries
        int p = -1;
        if (id_len_map[result.get(0)] <= tuple.setSize){
            p = binarySearchLength(result, LenIDMap[tuple.setSize]);
        }else {
            return;
        }
        if (p == -1){
            return;
        }
        for (; p<result.size(); ++p){
            Integer m = result.get(p);
            if (m < LenIDMap[tuple.setSize+1]){
                int k;
                for (k = 1; k < tuple.setSize; ++k){
                    if (!binarySearch6(invertedIndex.get(tuple.setElements[k]),m)){
                        break;
                    }
                }
                if (k==tuple.setSize){
                    resultSize++;
                }
            }else {
                break;
            }
        }

    }

    private boolean binarySearch6(ArrayList<Integer> list, Integer cadID) {
        int low = 0;
        int high = list.size() - 1;
        while(low <= high){
//            ListCount++;
            int mid = (low + high) / 2;
            if(list.get(mid) < cadID){
                low = mid + 1;
            }
            else if(list.get(mid).equals(cadID)){
                return true;
            }
            else{
                high = mid - 1;
            }
        }
        return false;
    }

    //Find the smallest index greater than or equal to the value
    public static int binarySearchLength(List<Integer> sortedArray, Integer value){

//        int count = 0;
        int low = 0;
        int mid = 0;
        int high = sortedArray.size() - 1;
        while(low <= high){
//            count++;
            mid = (low + high) / 2;
            if(sortedArray.get(mid).compareTo(value) < 0){
                low = mid + 1;
            }
            else if(sortedArray.get(mid).compareTo(value) == 0){
                return mid;
            }
            else{
                high = mid - 1;
            }
        }
//        if (low > mid){
//            return low;
//        }
//        return mid;
//        System.out.println(count);
        return low;
    }




    public ArrayList<Integer> intersectBinary(List<Integer> list1, List<Integer> list2){
//        if(list1 == null || list2 == null){
//            return null;
//        }
//        ListCount = 0;
        ArrayList<Integer> res = new ArrayList<Integer>(list1.size());
        for (int a : list1) {
            if (binarySearch(list2, a)) {
                res.add(a);
            }
        }
        return res;
    }
    public boolean binarySearch(List<Integer> sortedArray, int value){
        int low = 0;
        int high = sortedArray.size() - 1;
        while(low <= high){
//            ListCount++;
            int mid = (low + high) / 2;
            if(sortedArray.get(mid) < value){
                low = mid + 1;
            }
            else if(sortedArray.get(mid) == value){
                return true;
            }
            else{
                high = mid - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        System.out.println("LSIL-Equal");

        ArrayList<String> file1 = new ArrayList<>();
        ArrayList<String> file2 = new ArrayList<>();


        String inputFilePath12 = "./dataset/containment/dblp/dblp-random-1000.txt";
//        String inputFilePath22 = "./dataset/new/dblp.txt";
        String inputFilePath22 = "./dataset/new/AscLen_dblp.txt";
        file1.add(inputFilePath12);
        file2.add(inputFilePath22);
        String inputFilePath13 = "./dataset/containment/flickr/flickr-random-1000.txt";
//        String inputFilePath23 = "./dataset/new/flickr.txt";
        String inputFilePath23 = "./dataset/new/AscLen_flickr.txt";
        file1.add(inputFilePath13);
        file2.add(inputFilePath23);


        String inputFilePath1;
        String inputFilePath2;
        for (int q = 0; q < file1.size(); q++) {
            inputFilePath1 = file1.get(q);
            inputFilePath2 = file2.get(q);

            System.out.println(inputFilePath1);
            System.out.println(inputFilePath2);

            ArrayList<TupleInteger> tuplesQ = join.setContainSearch.Utility.readData2(inputFilePath1,  false);
            ArrayList<Tuple> tuplesS = join.setContainSearch.Utility.readData3(inputFilePath2,false);

            LSILEqual lSILEqual = new LSILEqual(tuplesS);

            for (int k = 0; k < 10; ++k){
                long start;
                long end;

                start = System.nanoTime();
                //Perform the query
                for (TupleInteger t : tuplesQ){
                    lSILEqual.searchLSIL_Equal(t);

                }
                end = System.nanoTime();

//                System.out.print((end - start)/1000 + "\t");
//                System.out.println((end - start)/1000 + "us");
                System.out.println("Search Time: " + (end - start)/1000 + "us");
//            System.out.println("Search Time: " + (end - start)/1000 + "s");
//            System.out.println("resultSize: " + lSILEqual.resultSize);
//                lSILEqual.resultSize = 0;
            }
        }


    }

}
