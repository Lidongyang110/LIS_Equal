package BFTree;


import utils.Utility;

@SuppressWarnings("serial")
public class Tuple{

	public int tupleID;
	
	public int setSize;
	
	public int[] setElements;
	
	public Tuple(){}
	
	public Tuple(int tid, int[] elements){
		this.tupleID = tid;
		this.setElements = elements;
		this.setSize = elements.length;
	}
	
	public Tuple(Tuple _tuple){
		this.tupleID = _tuple.getTupleID();
		this.setSize = _tuple.getSetSize();
		this.setElements = _tuple.setElements;
	}
	
	public int getTupleID() {
		return tupleID;
	}

	public void setTupleID(int tupleID) {
		this.tupleID = tupleID;
	}

	public int getSetSize() {
		return setSize;
	}

	public void setSetSize(int setSize) {
		this.setSize = setSize;
	}

	public int[] getSetElements() {
		return setElements;
	}

	public void setSetElements(int[] setElements) {
		this.setElements = setElements;
	}
	
	public int getElement(int pos){
		return this.setElements[pos];
	}
	
	/**
	 * return the element at pos from rear
	 * */
	public int getReverseElement(int pos){
		return this.setElements[this.setSize - 1 - pos];
	}
	
	public int getLastElement(){
		return this.setElements[this.setSize - 1];
	}

	public int[] getReverseElements(){
		int[] res = new int[this.setSize];
		for(int i = 0; i < this.setSize; ++i){
			res[i] = this.setElements[this.setSize - i - 1];
		}
		return res;
	}
	
	public void reverseElements(){
		int temp;
		for(int i = 0; i < this.setSize / 2; ++i){
			temp = this.setElements[i];
			this.setElements[i] = this.setElements[this.setSize - i - 1];
			this.setElements[this.setSize - i - 1] = temp;
		}
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<");
		sb.append(tupleID);
		sb.append(", ");
		sb.append(setSize);
		sb.append(", {");
		
		for(int i = 0; i < setSize; ++i){
			sb.append(setElements[i]);
			if(i != setSize - 1){
				sb.append(", ");
			}
		}
		sb.append("}>");
		
		return sb.toString();
	}
	
	
	/**
	 * compare two tuples, return whether THIS \subseteq t
	 * 
	 * if THIS \subseteq t, return true;
	 * else return false;
	 *
	 * assume that THIS and t are sorted ascending
	 * @param t
	*/
	public boolean isSubsetOf(Tuple t){
		
		int i = 0;
		int j = 0;
		
		if(this.setSize > t.setSize){
			return false;
		}else if(this.setSize == t.setSize){
			while(i < this.setSize){
				if(this.setElements[i] != t.setElements[i]){
					return false;
				}
				else{
					++i;
				}
			}
			return true;
		}else{
			while(i < this.setSize && j < t.setSize){
				if(this.setElements[i] == t.setElements[j]){
					++i;
					++j;
				}else if(this.setElements[i] < t.setElements[j]){
					return false;
				}else{
					++j;
				}
			}
			return (i == this.setSize)? true:false;
		}
	}

	/**
	 * Randomly select an element from the set
	 * */
	public int getRandomElement(){
		int idx = Utility.getRandomValue(this.setSize);
		return this.setElements[idx];
	}

	/*
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.tupleID = in.readInt();
		this.setSize = in.readInt();
		this.setElements = new int[setSize];
		for(int j = 0; j < setSize; ++j){
			this.setElements[j] = in.readInt();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(this.getTupleID());
		out.writeInt(this.getSetSize());
		for(int j = 0; j < this.getSetSize(); ++j){
			out.writeInt(this.getElement(j));
		}
	}

	@Override
	public int hashCode() {
		return this.tupleID;
	}

	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if(obj instanceof Tuple){
			Tuple _other = (Tuple) obj;
			res = (Utility.compareTuples(this, _other) == 0);
		}
		return res;
	}
	*/
	public static void main(String[] args){
		int[] a = {1, 2, 5, 4, 6};
		Tuple tuple = new Tuple(0, a);
		System.out.println(tuple);
		tuple.reverseElements();
		System.out.println(tuple);
	}
	/*
	@Override
	public int compareTo(Tuple _other) {
		// TODO Auto-generated method stub
		return Utility.compareTuples(this, _other);
	}

	 */
}
