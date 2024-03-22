package join.setContainSearch;


import java.util.Vector;


public class TupleVector {

	public int tupleID;
	
	public int setSize;
	
	public Vector<Integer> setElements;
	
	public TupleVector(){}
	
	public TupleVector(int tid, Vector<Integer> elements){
		this.tupleID = tid;
		this.setElements = elements;
		this.setSize = elements.size();
	}
	
	public TupleVector(TupleVector _tuple){
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

	public Vector<Integer> getSetElements() {
		return setElements;
	}

	public void setSetElements(Vector<Integer> setElements) {
		this.setElements = setElements;
	}
	
	public Integer getElement(int pos){
		return this.setElements.get(pos);
	}
	
	/**
	 * return the element at pos from rear
	 * */
	public int getReverseElement(int pos){
		return this.setElements.get(this.setSize - 1 - pos);
	}
	
	public int getLastElement(){
		return this.setElements.get(this.setSize - 1);
	}

	public int[] getReverseElements(){
		int[] res = new int[this.setSize];
		for(int i = 0; i < this.setSize; ++i){
			res[i] = this.setElements.get(this.setSize - i - 1);
		}
		return res;
	}

	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<");
		sb.append(tupleID);
		sb.append(", ");
		sb.append(setSize);
		sb.append(", {");
		
		for(int i = 0; i < setSize; ++i){
			sb.append(setElements.get(i));
			if(i != setSize - 1){
				sb.append(", ");
			}
		}
		sb.append("}>");
		
		return sb.toString();
	}

}
