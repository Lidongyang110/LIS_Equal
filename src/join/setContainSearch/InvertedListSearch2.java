package join.setContainSearch;

import BFTree.Tuple;
import utils.Utility;

import java.io.IOException;
import java.util.*;

public class InvertedListSearch2 {
    long filterPre = 0;
    long filterNum = 0;
    long candNum = 0;
    //倒排索引
    private HashMap<Integer, ArrayList<Integer>> invertedIndex = null;

    long resultSize = 0;

    Map<Integer,Integer> elem_sum = new HashMap<>();
    int[] elemCount = new int[500*10000];
    ArrayList<Tuple> tuplesS;

    //ID到长度映射表
    long[] id_len_map = null;
    private HashMap<Integer, Integer> len_startId = null;
    int[] LIT = null;
    public InvertedListSearch2(ArrayList<Tuple> tupleS) {
        this.tuplesS = tupleS;
        LIT = new int[tuplesS.get(tuplesS.size()-1).setSize + 2];
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
        init();
    }
    public void init(){
        id_len_map = new long[tuplesS.size()+1];
        len_startId = new HashMap<>();
        int len = 0;
        int indexPre = 0;
        int indexNext = 0;
        for (Tuple t : tuplesS){
            int setSize = t.setSize;
            id_len_map[t.tupleID]=t.setSize;
            if (setSize > len){
                LIT[setSize] = t.tupleID;
                len_startId.put(setSize,t.tupleID);
                indexNext = setSize+1;
                for (int j = setSize-1; j >= indexPre; j--){
                    LIT[j] = t.tupleID;
                }
                len = setSize;
            }else{
                indexPre = indexNext;

            }
        }
        LIT[LIT.length-1] = LIT[LIT.length-2]+1;

    }
    //列表乱序
    public void searchInvList(TupleInteger tuple){

        ArrayList<Integer> result = invertedIndex.get(tuple.setElements[0]);

        for (int index = 1; index < tuple.setSize; ++index){
            result = intersectBinary(result, invertedIndex.get(tuple.setElements[index]));
        }
        resultSize += result.size();
    }
    //列表乱序
    public void searchInvListEqual(TupleInteger tuple){

        ArrayList<Integer> result = invertedIndex.get(tuple.setElements[0]);

        for (int index = 1; index < tuple.setSize; ++index){
            result = intersectBinary(result, invertedIndex.get(tuple.setElements[index]));
        }

        for (int id : result){
            if (tuplesS.get(id-1).setSize != tuple.setSize){
                continue;
            }
            resultSize++;
        }
//        resultSize += result.size();
    }
    public void searchInvListEqualLF(TupleInteger tuple){

        //第1个列表
        ArrayList<Integer> result = invertedIndex.get(tuple.setElements[0]);
//        filterPre += result.size();
        //二分查找与查询长度相等的起点ID
        int startIDIndex = binSearch(tuple.setSize,result);

        if (startIDIndex == -1){
            return;
        }
//        filterNum += startIDIndex;
        //对相等长度的ID以二分方式查询是否在剩下元素的列表中，如果有一个不在，则提前终止，如果都在，则为一个结果
        for (; startIDIndex<result.size()&&id_len_map[result.get(startIDIndex)]==tuple.setSize; ++startIDIndex){
//            if(28903==result.get(startIDIndex)){
//                System.out.println();
//            }
            int elemIndex = 1;
            while (elemIndex < tuple.setSize){
                //找存在与否
                if (!binSearch2(result.get(startIDIndex),invertedIndex.get(tuple.setElements[elemIndex]))){
                    break;
                }

                elemIndex++;
            }
            if (elemIndex == tuple.setSize){
                resultSize++;
            }
        }
    }

    //列表长度升序
    public void searchSVS(TupleInteger tuple){

        //列表长度升序
        //频率升序排序
//         Arrays.sort(tuple.setElements, (o1, o2) -> {
//            if(elem_sum.get(o1) < elem_sum.get(o2))
//                return -1;
//            if(elem_sum.get(o1) > elem_sum.get(o2))
//                return 1;
//            return 0;
//        });
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        ArrayList<Integer> result = invertedIndex.get(tuple.setElements[0]);

        for (int index = 1; index < tuple.setSize; ++index){
            result = intersectBinary(result, invertedIndex.get(tuple.setElements[index]));
        }
        resultSize += result.size();
    }
    public void searchSVSEqual(TupleInteger tuple){

        //列表长度升序
        //频率升序排序
//         Arrays.sort(tuple.setElements, (o1, o2) -> {
//            if(elem_sum.get(o1) < elem_sum.get(o2))
//                return -1;
//            if(elem_sum.get(o1) > elem_sum.get(o2))
//                return 1;
//            return 0;
//        });
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        List<Integer> result = invertedIndex.get(tuple.setElements[0]);

        for (int index = 1; index < tuple.setSize; ++index){
            result = intersectBinary(result, invertedIndex.get(tuple.setElements[index]));
        }
        for (int id : result){
            if (tuplesS.get(id-1).setSize != tuple.setSize){
                continue;
            }
            resultSize++;
        }

//        resultSize += result.size();
    }
    public void searchSVSEqual2(TupleInteger tuple){

        //列表长度升序
        //频率升序排序
//         Arrays.sort(tuple.setElements, (o1, o2) -> {
//            if(elem_sum.get(o1) < elem_sum.get(o2))
//                return -1;
//            if(elem_sum.get(o1) > elem_sum.get(o2))
//                return 1;
//            return 0;
//        });
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        List<Integer> result = invertedIndex.get(tuple.setElements[0]);

        //长度过滤
        int startIDIndex = -1;
        if (id_len_map[result.get(0)] <= tuple.setSize){
//            startIDIndex = binarySearchLength(result, len_startId.get(tuple.setSize));
            startIDIndex = binarySearchLength(result, LIT[tuple.setSize]);
//            int startIndex = binarySearchLength(result, len_startId.get(tuple.setSize));
//            if (startIDIndex > 0){
//                result = result.subList(startIDIndex,result.size());
//            }
        }else {
            return;
        }
        if (startIDIndex == -1){
            return;
        }
//        if (startIDIndex != 0){
//            result = result.subList(startIDIndex,result.size());
//        }
        if (tuple.setSize>=2){
//            result = intersectBinary2(len_startId.get(tuple.setSize+1),startIDIndex,result,invertedIndex.get(tuple.setElements[1]));
            result = intersectBinary2(LIT[tuple.setSize+1],startIDIndex,result,invertedIndex.get(tuple.setElements[1]));
        }

        for (int index = 2; index < tuple.setSize; ++index){
            result = intersectBinary(result, invertedIndex.get(tuple.setElements[index]));
//            result = intersectBinary2(LIT[tuple.setSize+1],0,result,invertedIndex.get(tuple.setElements[index]));
//            result = intersectBinary3(LIT[tuple.setSize],LIT[tuple.setSize+1],result,invertedIndex.get(tuple.setElements[index]));

        }
        for (int id : result){
            if (tuplesS.get(id-1).setSize != tuple.setSize){
                continue;
            }
            resultSize++;
        }

//        resultSize += result.size();
    }
    public void searchLEIL_Equal(TupleInteger tuple){
//        for (int k : elemCount){
//            System.out.print(k + ",");
//        }
        //列表长度升序
        //查询的元素频率升序排序
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        //最短列表
        List<Integer> result = invertedIndex.get(tuple.setElements[0]);
//        filterPre += result.size();
        //二分查找与查询长度相等的起点ID
        //长度过滤
        int startIDIndex = -1;
        if (id_len_map[result.get(0)] <= tuple.setSize){
            startIDIndex = binarySearchLength(result, len_startId.get(tuple.setSize));
//            int startIndex = binarySearchLength(result, len_startId.get(tuple.setSize));
//            if (startIDIndex > 0){
//                result = result.subList(startIDIndex,result.size());
//            }
        }else {
            return;
        }
        if (startIDIndex == -1){
            return;
        }
//        int temp = startIDIndex;
//        filterNum += startIDIndex;
        //对相等长度的ID以二分方式查询是否在剩下元素的列表中，如果有一个不在，则提前终止，如果都在，则为一个结果
        for (; startIDIndex<result.size(); ++startIDIndex){
//            if(28903==result.get(startIDIndex)){
//                System.out.println();
//            }
            Integer cadID = result.get(startIDIndex);
//            if (id_len_map[cadID]==1&&tuple.setSize==1){
//                resultSize++;
//                continue;
//            }
            if (cadID < len_startId.get(tuple.setSize+1)){
                //此时候选与查询长度相等，接下验证此cadID是否在存在于查询剩下的倒排列表
                int k;
                for (k = 1; k < tuple.setSize; ++k){
                    if (!binarySearch6(invertedIndex.get(tuple.setElements[k]),cadID)){
                        break;
                    }
                }
                if (k==tuple.setSize){
                    resultSize++;
                }
            }else {
//                candNum += startIDIndex-temp;
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

    //找大于等于value的最小index
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

    public void searchSVSEqualLF(TupleInteger tuple){

        //列表长度升序
        //频率升序排序
//         Arrays.sort(tuple.setElements, (o1, o2) -> {
//            if(elem_sum.get(o1) < elem_sum.get(o2))
//                return -1;
//            if(elem_sum.get(o1) > elem_sum.get(o2))
//                return 1;
//            return 0;
//        });
        //查询的元素频率升序排序
        Arrays.sort(tuple.setElements, (o1, o2) -> {
            if(elemCount[o1] < elemCount[o2])
                return -1;
            if(elemCount[o1] > elemCount[o2])
                return 1;
            return 0;
        });

        //最短列表
        ArrayList<Integer> result = invertedIndex.get(tuple.setElements[0]);
        filterPre += result.size();
        //二分查找与查询长度相等的起点ID
        int startIDIndex = binSearch(tuple.setSize,result);

        if (startIDIndex == -1){
            return;
        }
        filterNum += startIDIndex;
        //对相等长度的ID以二分方式查询是否在剩下元素的列表中，如果有一个不在，则提前终止，如果都在，则为一个结果
        for (; startIDIndex<result.size()&&id_len_map[result.get(startIDIndex)]==tuple.setSize; ++startIDIndex){
//            if(28903==result.get(startIDIndex)){
//                System.out.println();
//            }
            int elemIndex = 1;
            while (elemIndex < tuple.setSize){
                //找存在与否
                if (!binSearch2(result.get(startIDIndex),invertedIndex.get(tuple.setElements[elemIndex]))){
                    break;
                }

                elemIndex++;
            }
            if (elemIndex == tuple.setSize){
                resultSize++;
            }
        }

    }

    private boolean binSearch2(Integer value, ArrayList<Integer> sortedArray) {
        int low = 0;
        int high = sortedArray.size() - 1;
        while(low <= high){
//            ListCount++;
            int mid = (low + high) / 2;
            if(sortedArray.get(mid) < value){
                low = mid + 1;
            }
            else if(sortedArray.get(mid).equals(value)){
                return true;
            }
            else{
                high = mid - 1;
            }
        }
        return false;

    }

    private int binSearch(int setSize, ArrayList<Integer> result) {
        int startIndex = 0;
        int endIndex = result.size()-1;
        int startIDIndex;
        do {
            startIDIndex = innerBinSearch(setSize, startIndex, endIndex, result);
            if (startIDIndex <= 0||startIDIndex==result.size()-1){
                return startIDIndex;
            }
            //更新范围
            endIndex = startIDIndex-1;

        }while (id_len_map[result.get(startIDIndex)]==id_len_map[result.get(startIDIndex-1)]);

        return startIDIndex;
    }

    private int innerBinSearch(int value, int startIndex, int endIndex, ArrayList<Integer> result) {
        int mid;
        while (startIndex <= endIndex){
            mid = (startIndex+endIndex)/2;
            if(id_len_map[result.get(mid)] < value){
                startIndex = mid + 1;
            }
            else if(id_len_map[result.get(mid)] == value){
                return mid;
            }
            else{
                endIndex = mid - 1;
            }
        }
        return -1;
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
    public ArrayList<Integer> intersectBinary2(int endID, int startIndex, List<Integer> list1, List<Integer> list2){
//        if(list1 == null || list2 == null){
//            return null;
//        }
//        ListCount = 0;
        ArrayList<Integer> res = new ArrayList<Integer>(list1.size());
        for (; startIndex < list1.size(); startIndex++){
            int a = list1.get(startIndex);
            if (a < endID){
                if (binarySearch(list2, a)) {
                    res.add(a);
                }
            } else {
                break;
            }
        }

        return res;
    }
    public ArrayList<Integer> intersectBinary3(int startID, int endID, List<Integer> list1, List<Integer> list2){
//        if(list1 == null || list2 == null){
//            return null;
//        }
//        ListCount = 0;
        ArrayList<Integer> res = new ArrayList<Integer>(list1.size());
        for (int a : list1){
            if (a < endID && a >= startID){
                if (binarySearch(list2, a)) {
                    res.add(a);
                }
//                if (binarySearch(list2, a)) {
//                    res.add(a);
//                }
            } else {
                break;
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
    public boolean binarySearch2(int start, int end, List<Integer> sortedArray, int value){
        int low = 0;
        int high = sortedArray.size() - 1;
        while(low <= high){
//            ListCount++;
            int mid = (low + high) / 2;
//            if (!(sortedArray.get(mid)>=start && sortedArray.get(mid)<end)){
//                return false;
//            }
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
        System.out.println("InvertedListSearch2");

        ArrayList<String> file1 = new ArrayList<>();
        ArrayList<String> file2 = new ArrayList<>();

//        for (int num = 1; num < 10; num++) {

        String inputFilePath11 = "./dataset/containment/accidents/accidents-random-1000.txt";
//        String inputFilePath21 = "./dataset/new/accidents.txt";
        String inputFilePath21 = "./dataset/new/AscLen_accidents.txt";
//        file1.add(inputFilePath11);
//        file2.add(inputFilePath21);
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
        String inputFilePath14 = "./dataset/containment/T40/T40I10D100K-random-1000.txt";
//        String inputFilePath24 = "./dataset/new/T40I10D100K.txt";
        String inputFilePath24 = "./dataset/new/AscLen_T40I10D100K.txt";
//        file1.add(inputFilePath14);
//        file2.add(inputFilePath24);

        String inputFilePath1;
        String inputFilePath2;
        for (int q = 0; q < file1.size(); q++) {
            inputFilePath1 = file1.get(q);
            inputFilePath2 = file2.get(q);

            System.out.println(inputFilePath1);
            System.out.println(inputFilePath2);

            ArrayList<TupleInteger> tuplesQ = join.setContainSearch.Utility.readData2(inputFilePath1,  false);
            ArrayList<Tuple> tuplesS = join.setContainSearch.Utility.readData3(inputFilePath2,false);

            InvertedListSearch2 invertedListSearch = new InvertedListSearch2(tuplesS);

            long[] runTime = new long[20];
            for (int k = 0; k < 20; ++k){
                long start;
                long end;

                start = System.nanoTime();
                //查询
                long tempresult = 0;
                for (TupleInteger t : tuplesQ){
//                    invertedListSearch.searchInvList(t);
//                    invertedListSearch.searchInvListEqual(t);
//                    invertedListSearch.searchInvListEqualLF(t);
//                    invertedListSearch.searchSVS(t);
//                    invertedListSearch.searchSVSEqual(t);
//                    if (t.tupleID == 44){
//                        System.out.println();
//                    }
                    invertedListSearch.searchSVSEqual2(t); //SVS-LF
//                    invertedListSearch.searchLEIL_Equal(t);
//                    System.out.println(t.tupleID + "\t" + (invertedListSearch.resultSize-tempresult));
//                    tempresult = invertedListSearch.resultSize;
//                    System.out.println(t.tupleID + ":" + invertedListSearch.resultSize);

                }
                end = System.nanoTime();

                runTime[k] = (end - start)/1000;
//                System.out.print((end - start)/1000 + "\t");
//                System.out.println((end - start)/1000 + "us");
//                System.out.println("Search Time: " + (end - start)/1000 + "us");
//            System.out.println("Search Time: " + (end - start)/1000 + "s");
            System.out.println("resultSize: " + invertedListSearch.resultSize);
                invertedListSearch.resultSize = 0;
            }
            for (long time : runTime){
                System.out.print(time +"\t");
            }
            System.out.println();
//            System.out.println("sotred");
            Arrays.sort(runTime);
            for (long time : runTime){
//                System.out.print(time +"\t");
            }
            System.out.println();
//            System.out.println(invertedListSearch.filterPre + "\t" + invertedListSearch.candNum);
//            invertedListSearch.filterPre = invertedListSearch.filterNum=0;
        }


    }

}
