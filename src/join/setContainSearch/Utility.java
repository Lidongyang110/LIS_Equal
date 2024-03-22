package join.setContainSearch;

import BFTree.Tuple;

import java.io.*;
import java.util.*;

public class Utility {
    public static ArrayList<TupleVector> readData(String inputFilePath, boolean reverse)
            throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
        File fin = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        ArrayList<TupleVector> tuples = new ArrayList<>();
        String line = null;
        while((line = br.readLine()) != null){
            line = line.trim();
            String[] parts = line.split(",");
            int tupleId = 0;
//            ArrayList<Integer> elements = new ArrayList<Integer>();
            TupleVector tupleVector = new TupleVector();
            Vector<Integer> elements = new Vector<>(parts.length-1);
            //System.out.println(line);
            if(parts.length > 0){
                tupleId = Integer.parseInt(parts[0]);
                if(reverse){
                    for(int i = parts.length-1; i > 0; --i){
                        elements.add(Integer.parseInt(parts[i]));
                    }
                }
                else{
                    for(int i = 1; i < parts.length; ++i){
                        elements.add(Integer.parseInt(parts[i]));
                    }
                }
//                int[] array = convert(elements);
//                T tuple = cls.newInstance();
                tupleVector.tupleID = tupleId;
                tupleVector.setSize = elements.size();
                tupleVector.setElements = elements;
                tuples.add(tupleVector);
            }
        }

        br.close();
        fis.close();
        return tuples;
    }
    public static HashMap<Integer, ArrayList<Integer>> buildInvertedIndex3(Collection<Tuple> sets){
        assert(sets != null);
        HashMap<Integer, ArrayList<Integer>> invIndex = new HashMap<>();
        for(Tuple tuple : sets){
//			System.out.println("id: " + tuple.tupleID);
            for(int e : tuple.getSetElements()){
                if(!invIndex.containsKey(e)){
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(tuple.getTupleID());
                    invIndex.put(e, list);
                }
                else{
//					System.out.println("invIndexSize: " + invIndex.size());
                    invIndex.get(e).add(tuple.getTupleID());
                }
            }
        }
//		for(ArrayList<Integer> list : invIndex.values()){
//			Collections.sort(list);
//		}
        return invIndex;
    }

    public static HashMap<Integer, ArrayList<Integer>> buildInvertedIndex2(Collection<TupleInteger> sets){
        assert(sets != null);
        HashMap<Integer, ArrayList<Integer>> invIndex = new HashMap<>();
        for(TupleInteger tuple : sets){
//			System.out.println("id: " + tuple.tupleID);
            for(Integer e : tuple.getSetElements()){
                if(!invIndex.containsKey(e)){
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(tuple.getTupleID());
                    invIndex.put(e, list);
                }
                else{
                    invIndex.get(e).add(tuple.getTupleID());
                }
            }
        }
//		for(ArrayList<Integer> list : invIndex.values()){
//			Collections.sort(list);
//		}
        return invIndex;
    }
    public static HashMap<Integer, ArrayList<Integer>> buildInvertedIndex(Collection<TupleVector> sets){
        assert(sets != null);
        HashMap<Integer, ArrayList<Integer>> invIndex = new HashMap<>();
        for(TupleVector tuple : sets){
//			System.out.println("id: " + tuple.tupleID);
            for(int e : tuple.getSetElements()){
                if(!invIndex.containsKey(e)){
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(tuple.getTupleID());
                    invIndex.put(e, list);
                }
                else{
                    invIndex.get(e).add(tuple.getTupleID());
                }
            }
        }
//		for(ArrayList<Integer> list : invIndex.values()){
//			Collections.sort(list);
//		}
        return invIndex;
    }
    public static ArrayList<TupleInteger> readData2(String inputFilePath, boolean reverse)
            throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
        File fin = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        ArrayList<TupleInteger> tuples = new ArrayList<TupleInteger>();
        String line = null;
        while((line = br.readLine()) != null){
            line = line.trim();
            String[] parts = line.split(",");
            int tupleId = 0;
            Integer[] elements = new Integer[parts.length-1];
            int kk = 0;
            //System.out.println(line);
            if(parts.length > 0){
                tupleId = Integer.parseInt(parts[0]);
                if(reverse){
                    for(int i = parts.length-1; i > 0; --i){
                        elements[kk++] = Integer.parseInt(parts[i]);
                    }
                }
                else{
                    for(int i = 1; i < parts.length; ++i){
                        elements[kk++] = Integer.parseInt(parts[i]);
                    }
                }
                TupleInteger tuple = new TupleInteger();
                tuple.tupleID = tupleId;
                tuple.setSize = elements.length;
                tuple.setElements = elements;
                tuples.add(tuple);
            }
        }

        br.close();
        fis.close();
        return tuples;
    }
    public static ArrayList<Tuple> readData3(String inputFilePath, boolean reverse)
            throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
        File fin = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        ArrayList<Tuple> tuples = new ArrayList<Tuple>();
        String line = null;
        while((line = br.readLine()) != null){
            line = line.trim();
            String[] parts = line.split(",");
            int tupleId = 0;
            int[] elements = new int[parts.length-1];
            int kk = 0;
            //System.out.println(line);
            if(parts.length > 0){
                tupleId = Integer.parseInt(parts[0]);
                if(reverse){
                    for(int i = parts.length-1; i > 0; --i){
                        elements[kk++]  = Integer.parseInt(parts[i]);
                    }
                }
                else{
                    for(int i = 1; i < parts.length; ++i){
                        elements[kk++]  = Integer.parseInt(parts[i]);
                    }
                }
                Tuple tuple = new Tuple();
                tuple.tupleID = tupleId;
                tuple.setSize = elements.length;
                tuple.setElements = elements;
                tuples.add(tuple);
            }
        }

        br.close();
        fis.close();
        return tuples;
    }
    public static ArrayList<Tuple> readData4(String inputFilePath, boolean reverse)
            throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
        File fin = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        ArrayList<Tuple> tuples = new ArrayList<Tuple>();
        String line = null;
        while((line = br.readLine()) != null){
            line = line.trim();
            String[] parts = line.split(" ");
            int tupleId = 0;
            int[] elements = new int[parts.length-1];
            int kk = 0;
            //System.out.println(line);
            if(parts.length > 0){
                tupleId = Integer.parseInt(parts[0]);
                if(reverse){
                    for(int i = parts.length-1; i > 0; --i){
                        elements[kk++]  = Integer.parseInt(parts[i]);
                    }
                }
                else{
                    for(int i = 1; i < parts.length; ++i){
                        elements[kk++]  = Integer.parseInt(parts[i]);
                    }
                }
                Tuple tuple = new Tuple();
                tuple.tupleID = tupleId;
                tuple.setSize = elements.length;
                tuple.setElements = elements;
                tuples.add(tuple);
            }
        }

        br.close();
        fis.close();
        return tuples;
    }

    public static int[] convert(List<Integer> list){
        int size = list.size();
        int[] array = new int[size];
        for(int i = 0; i < size; ++i){
            array[i] = list.get(i).intValue();
        }
        return array;
    }
    public static HashMap<Integer, ArrayList<Integer>> buildInvertedIndexLimit(ArrayList<Tuple> sets){
        assert(sets != null);
        HashMap<Integer, ArrayList<Integer>> invIndex = new HashMap<Integer, ArrayList<Integer>>();
        for(Tuple tuple : sets){
            for(int e : tuple.getSetElements()){
                if(!invIndex.containsKey(e)){
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(tuple.getTupleID());
                    invIndex.put(e, list);
                }
                else{
                    invIndex.get(e).add(tuple.getTupleID());
                }
            }
        }
//        for(ArrayList<Integer> list : invIndex.values()){
//            Collections.sort(list);
//        }
        return invIndex;
    }

}
