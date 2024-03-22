package utils;




import BFTree.Tuple;


import java.io.*;
import java.util.*;

public class Utility {
	


	/**
	 * to check is first is a subset of second
	 * Assumption: both sets are in increasing order
	 * */
	public static boolean isSubsetOf(int[] first, int[] second){
		int i = 0;
		int j = 0;
		
		if(first.length > second.length){
			return false;
		}else if(first.length == second.length){
			while(i < first.length){
				if(first[i] != second[i]){
					return false;
				}
				else{
					++i;
				}
			}
			return true;
		}else{
			while(i < first.length && j < second.length){
				if(first[i] == second[j]){
					++i;
					++j;
				}else if(first[i] < second[j]){
					return false;
				}else{
					++j;
				}
			}
			return (i == first.length)? true:false;
		}
	}
	
	/**
	     * compare two sets, return whether set1 \supset set2
	     * if set1 == set2, return 0;
	     * if set1 \supset set2, return 1;
	     * else return -1;
	     *
	     * assume that set1 and set2 are sorted ascending
	     *        |set1| >= |set2|
	     * @param set1
	     * @param set2
	     */
	    public static int compareSet(int[] set1, int[] set2) {

	        //long start = System.nanoTime();

	        int i = 0;
	        int j = 0;
	        int len1 = set1.length;
	        int len2 = set2.length;

	        if(len1 > len2) {//containment
	            while((i < len1)&&(j < len2)) {
	                if(set1[i] == set2[j]) {
	                    i++;j++;
	                }else if(set1[i] > set2[j]) {
	                    //MakeTests.set_compare += System.nanoTime() - start;
	                    return -1;
	                }else {
	                    i++;
	                }
	            }
	            return (j == len2)?1:-1;
	        }else if(len1 == len2) {//equality
	            while(i < len1) {
	                if(set1[i] != set2[i]) {
	                    //MakeTests.set_compare += System.nanoTime() - start;
	                    return -1;
	                }
	                i++;
	            }
	            //MakeTests.set_compare += System.nanoTime() - start;
	            return 0;
	        }else {
	            //MakeTests.set_compare += System.nanoTime() - start;
	            return -1;
	        }
	}
	
	    /**
	     * the normal implementation, using modulo sig_len
	     * one element -> one bit in the long_signature
	     * @param set
	     * @param sig_len how many integers we use to represent a long_signature
	     * @return
	     */
	    public static int[] create_sig_normal(int[] set, int sig_len) {
	        int[] signature = new int[sig_len];
	        int remainder = 0;
	        int index = 0;
	        int bit = 0;
	        for(int i = 0; i < set.length; i++) {
	            //TODO
	            // = hf.hashInt(set[i]).asInt()%(Integer.SIZE*sig_len);
	            //remainder = remainder < 0? -remainder : remainder;
	            remainder = set[i]%(Integer.SIZE*sig_len);
	            index = remainder / Integer.SIZE;
	            bit = remainder % Integer.SIZE;
	            //System.out.print(index+","+bit);
	            signature[index] |= 1 << (bit);
	        }
	        return signature;
	}
	
	/**
	 * Get a random integer value in range [0, range)
	 * 
	 * */
	public static int getRandomValue(int range){
		Random generator = new Random();
		return generator.nextInt(range);
	}
	
	public static int[] convert(List<Integer> list){
		int size = list.size();
		int[] array = new int[size];
		for(int i = 0; i < size; ++i){
			array[i] = list.get(i).intValue();
		}
		return array;
	}
	
	public static ArrayList<Integer> convert(int[] array){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < array.length; ++i){
			list.add(array[i]);
		}
		return list;
	}
	
	public static int[] extractIntegers(String str, String regex){
		String[] array = str.split(regex);
		int[] ans = new int[array.length];
		for(int i = 0; i < array.length; ++i){
			ans[i] = Integer.parseInt(array[i]);
		}
		return ans;
	}
	
	public static String constructString(int[] values, String regex){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < values.length; ++i){
			sb.append(values[i]);
			if(i != values.length - 1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	






	public static HashMap<Integer, ArrayList<Integer>> buildInvertedIndex(Collection<Tuple> sets){
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
	

	private static final int MERGE = 1;
	private static final int BINARY = 2;
	
	/**
	 * Compute the intersection of two sorted lists in a merge-sort manner, in ascending order
	 * 
	 * */
	public static ArrayList<Integer> intersectMerge(List<Integer> list1, List<Integer> list2){
		if(list1 == null || list2 == null){
			return null;
		}
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		int size1 = list1.size();
		int size2 = list2.size();
		while(i < size1 && j < size2){
			if(list1.get(i) < list2.get(j)){
				++i;
			}
			else if(list1.get(i) > list2.get(j)){
				++j;
			}
			else{
				res.add(list1.get(i));
				++i;
				++j;
			}
		}
		return res;
	}
	
	/**
	 * Compute the intersection of two sorted lists in a binary search manner, in ascending order
	 * 
	 * */
	public static ArrayList<Integer> intersectBinary(List<Integer> list1, List<Integer> list2){
		if(list1 == null || list2 == null){
			return null;
		}
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		boolean list1Longer = list1.size() > list2.size();
		
		if(list1Longer){
			Iterator<Integer> itB = list2.iterator();
			while(itB.hasNext()){
				int b = itB.next();
				if(binarySearch(list1, b)){
					res.add(b);
				}
			}
		} else{
			Iterator<Integer> itA = list1.iterator();
			while(itA.hasNext()){
				int a = itA.next();
				if(binarySearch(list2, a)){
					res.add(a);
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Compute the intersection of two sorted lists in a hybrid way, 
	 * namely merge-sort or binary fashion.
	 * First use a cost model to decide between these two.
	 * 
	 * */
	public static ArrayList<Integer> intersectHybrid(List<Integer> list1, List<Integer> list2){
		if(list1 == null || list2 == null){
			return null;
		}
		
		int decision = judgeISCost(list1, list2);
		if(decision == MERGE) {
//			System.out.println("MERGE......................");
			return intersectMerge(list1, list2);
		} else if(decision == BINARY) {
//			System.out.println("BINARY......................");
			return intersectBinary(list1, list2);
		}
		// no other case possible...
		return null;
	}



	/**
	 * Decide between binary and merge method, based on the lists' sizes.
	 */
	public static int judgeISCost(List<Integer> list1, List<Integer> list2) {
		// decide between binary and merge
		int x = list1.size();
		int y = list2.size();
		// x needs to be the smaller list, so swap if needed
		if(x > y) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		// formulas from Panos' physical cost model
		double binaryISCost = 161*x*Math.log(y)/Math.log(2.0) + 59074;
		double mergeISCost = 292*x + 85*y + 175636;
		// return intersection strategy with lower cost
		if(binaryISCost < mergeISCost) {
			return BINARY;
		} else {
			return MERGE;
		}
	}

	/**
	 * Compute the intersection of two sorted lists in a binary search manner, in ascending order
	 *
	 * */
	public static ArrayList<Integer> intersectSkip(List<Integer> list1, List<Integer> list2) {
		if (list1 == null || list2 == null) {
			return null;
		}

		ArrayList<Integer> res = new ArrayList<Integer>();
		int i, t;
		i = t = 0;
		int size1 = list1.size();
		int size2 = list2.size();

		int p, q=0;

		while(i < size1 && t < size2){
			if(list1.get(i) < list2.get(t)){
				//++i;
//				p = i + (list2.get(j)-list1.get(i));
				if (i + 1 < size1){
					q = list1.get(i+1) - list1.get(i);
				}else {
					q = 0;
				}

				if (q < (list2.get(t)-list1.get(i))){
//					p = i +  (list2.get(j)-list1.get(i)) ;
					p = i +  (list2.get(t)-list1.get(i)) - q + 1;
					p = Math.min(p, size1 - 1);

//				int search = sequenSearch(list1, i + 1, p, list2.get(t));
					int search = binarySearch(list1, i + 2, p, list2.get(t));
					if (search >= 0){
						i = search + 1;
						res.add(list2.get(t));
					}else {
						i+=2;
					}
					t++;

				}else if(q > (list2.get(t)-list1.get(i))){
//					p = i + (list2.get(j)-list1.get(i));
					i++;
					t++;
				}else {
					res.add(list2.get(t));
					i +=2;
					t++;
				}
			}
			else if(list1.get(i) > list2.get(t)){

				if (t + 1 < size2){
					q = list2.get(t+1) - list2.get(t);
				}else {
					q = 0;
				}
				if (q < (list1.get(i)-list2.get(t))){
//					p = j + (list1.get(i)-list2.get(j));
					p = t + (list1.get(i)-list2.get(t)) - q + 1;
					p = Math.min(p, size2 - 1);

//				int search = sequenSearch(list2, t+ 1, p, list1.get(i));
					int search = binarySearch(list2, t + 2, p, list1.get(i));
					if (search >= 0){
						res.add(list1.get(i));
						t = search + 1;
					}else {
						t+=2;
					}
					i++;

				} else if(q > (list1.get(i)-list2.get(t))){
//					p = j + (list1.get(i)-list2.get(j));
					i++;
					t++;
				}else {
					res.add(list1.get(i));
					t +=2;
					i++;
				}
			}
			else{
				res.add(list1.get(i));
				++i;
				++t;
			}
		}
		return res;
	}
	public static ArrayList<Integer> intersectSkip3(List<Integer> list1, List<Integer> list2) {
		if (list1 == null || list2 == null) {
			return null;
		}
		ArrayList<Integer> res = new ArrayList<Integer>();
		int i, j;
		i = j = 0;
		int size1 = list1.size();
		int size2 = list2.size();

		int p, q = 0;

		Integer list_i, list_j;
		int temp, search;
		int[] info = new int[2];
		while(i < size1 && j < size2){
			q = 0;
			list_i = list1.get(i);
			list_j = list2.get(j);
//			long start = System.nanoTime();
			if(list_i < list_j){
				if (i + 1 < size1){
					q = list1.get(i+1) - list_i;
				}
				temp = list_j - list_i;
				if (q < temp){
					p = i +  temp - q + 1;
//					p = Math.min(p, size1 - 1);
//					binarySearchPatition2(list1, i + 2, p, list_j,info);
//					binarySearch2(list1, i + 2, p, list_j,info);

					if (p > size1 - 1){
						binarySearch2(list1, i + 2, size1 - 1, list_j,info);
//						binarySearchPatition2(list1, i + 2, size1 - 1, list_j,info);
					}else {
//						binarySearchPatition2(list1, i + 2, p, list_j,info);
						binarySearch2(list1, i + 2, p, list_j,info);
					}

					if (info[0] >= 0){
						i = ++info[0];
						res.add(list_j);
					}else {
						i = info[1];
					}
					++j;
//					Time += (System.nanoTime() - start);
				}else if(q > temp){
					++i;
					++j;
				}else {
					res.add(list_j);
					i +=2;
					++j;
				}
			}
			else if(list_i > list_j){
				if (j + 1 < size2){
					q = list2.get(j+1) - list_j;
				}
				temp = list_i - list_j;
//				long start = System.nanoTime();
				if (q < temp){
					p = j + temp - q + 1;
//					p = Math.min(p, size2 - 1);
//					p = p > size2 - 1? size2 - 1: p;
//					binarySearchPatition2(list2, j + 2, p, list_i,info);
//					binarySearch2(list2, j + 2, p, list_i,info);

					if (p > size2 - 1){
//						binarySearchPatition2(list2, j + 2, size2 - 1, list_i,info);
						binarySearch2(list2, j + 2, size2 - 1, list_i,info);
					}else {
//						binarySearchPatition2(list2, j + 2, p, list_i,info);
						binarySearch2(list2, j + 2, p, list_i,info);
					}

					if (info[0] >= 0){
						res.add(list_i);
						j = ++info[0];
					}else {
						j = info[1];
					}
					i++;

				} else if(q > temp){
					++i;
					++j;
				}else {
					res.add(list_i);
					j += 2;
					++i;
				}
			}
			else{
				res.add(list_i);
				++i;
				++j;
			}
//			Time += (System.nanoTime() - start);
		}

		return res;
	}
	//二分搜索，返回找到的位置，没找到返回 -1
	public static void binarySearch2(List<Integer> sortedArray, int low, int high,int value, int[] info){

		//info[0] >= 0  : 找到
		//info[0] == -1 : 没找到  info[1] = 大于value的位置
//		int[] info = new int[2];
		int mid = 0;
		while(low <= high){
//			searchCount1 += 1;
			mid = (low + high) / 2;
			if(sortedArray.get(mid) < value){
				low = mid + 1;
			}
			else if(sortedArray.get(mid) == value){
				info[0] = mid;
				return ;
			}
			else{
				high = mid - 1;
			}
		}
		info[0] = -1;
		if (low > mid){
			info[1] = low;
		}else {
			info[1] = mid;
		}
//		return ;
	}

	public static int binarySearch(List<Integer> sortedArray, int low, int high,int value){

//		int low = 0;
//		int high = sortedArray.size() - 1;
		while(low <= high){
			int mid = (low + high) / 2;
			if(sortedArray.get(mid) < value){
				low = mid + 1;
			}
			else if(sortedArray.get(mid) == value){
				return mid;
			}
			else{
				high = mid - 1;
			}
		}
		return -1;
	}


	public static ArrayList<Integer> union(List<Integer> list1, List<Integer> list2){
		ArrayList<Integer> res = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		while(i < list1.size() && j < list2.size()){
			if(list1.get(i) < list2.get(j)){
				res.add(list1.get(i));
				++i;
			}
			else if(list1.get(i) > list2.get(j)){
				res.add(list2.get(j));
				++j;
			}
			else{
				res.add(list1.get(i));
				++i;
				++j;
			}
		}
		res.addAll(list1.subList(i, list1.size()));
		res.addAll(list2.subList(j, list2.size()));
		return res;
	}
	
	public static void print(HashMap<Integer, ArrayList<Integer>> invertedList){
		for(Map.Entry<Integer, ArrayList<Integer>> entry : invertedList.entrySet()){
			System.out.print(entry.getKey());
			System.out.print("\t");
			for(int e : entry.getValue()){
				System.out.print(e);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * return the index of value in the sorted array or the first value larger than value if it does not appear in the array
	 * 
	 * */
	public static int binarySearchLocation(List<Integer> sortedArray, int value){
		
		int low = 0;
		int high = sortedArray.size() - 1;
		while(low <= high){
			int mid = (low + high) / 2;
			if(sortedArray.get(mid) < value){
				low = mid + 1;
			}
			else if(sortedArray.get(mid) == value){
				return mid;
			}
			else{
				high = mid - 1;
			}
		}
		return low;
	}
	
	/**
	 * search for element in sortedArray with binary search
	 * 
	 * */
	public static boolean binarySearch(List<Integer> sortedArray, int value){
		
		int low = 0;
		int high = sortedArray.size() - 1;
		while(low <= high){
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
	
	public static <T extends Tuple> ArrayList<T> readData(String inputFilePath, Class<T> cls, boolean reverse) 
			throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
		File fin = new File(inputFilePath);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		ArrayList<T> tuples = new ArrayList<T>();
		String line = null;
		while((line = br.readLine()) != null){
			String[] parts = line.split(getSplitString());
			int tupleId = 0;
			ArrayList<Integer> elements = new ArrayList<Integer>();
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
				int[] array = convert(elements);
				T tuple = cls.newInstance();
				tuple.tupleID = tupleId;
				tuple.setSize = array.length;
				tuple.setElements = array;
				tuples.add(tuple);
			}
		}

 		br.close();
		return tuples;
	}
	public static <T extends Tuple> ArrayList<T> readData2(String inputFilePath, Class<T> cls, boolean reverse)
			throws NumberFormatException, IOException, InstantiationException, IllegalAccessException{
		File fin = new File(inputFilePath);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		ArrayList<T> tuples = new ArrayList<T>();
		String line = null;
		while((line = br.readLine()) != null){
			line = line.trim();
			String[] parts = line.split(",");
			int tupleId = 0;
			ArrayList<Integer> elements = new ArrayList<Integer>();
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
				int[] array = convert(elements);
				T tuple = cls.newInstance();
				tuple.tupleID = tupleId;
				tuple.setSize = array.length;
				tuple.setElements = array;
				tuples.add(tuple);
			}
		}

 		br.close();
		fis.close();
		return tuples;
	}

	protected static String getSplitString() {
	        return " ";
	}
	
	/**
	 * evenly partition the length into numPartition segments, 
	 * return the endIndex (exclusive) of each segments
	 * for example: if length = 9, numPartition = 2, then ans = {4, 9};
	 * */
	public static int[] evenPartition(int length, int numPartition){
		assert(length > 0 && numPartition > 0);
		int[] ans = new int[numPartition];
		if(numPartition == 1){
			ans[0] = length;
			return ans;
		}
		int base = length / numPartition;
		int left = length % numPartition;
		for(int i = 0; i < numPartition; ++i){
			ans[i] = (i+1) * base;
		}
		ans[numPartition - 1] += left;
		return ans;
	}
	
	public static long toKB(long l) {
		return l / 1024;
	}
	
	public static long toMB(long l) {
		return l / (1024 * 1024);
	}
	
	public static void main(String[] args){
		int[] ans = evenPartition(12, 3);
		for(int i : ans){
			System.out.print(i + " ");
		}
	}
}


