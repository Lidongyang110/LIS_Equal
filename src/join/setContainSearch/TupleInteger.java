package join.setContainSearch;

public class TupleInteger {

	public int tupleID;

	public int setSize;

	public Integer[] setElements;

	public TupleInteger(){}

	
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

	public Integer[] getSetElements() {
		return setElements;
	}

	public void setSetElements(Integer[] setElements) {
		this.setElements = setElements;
	}
	
	public int getElement(int pos){
		return this.setElements[pos];
	}

}
