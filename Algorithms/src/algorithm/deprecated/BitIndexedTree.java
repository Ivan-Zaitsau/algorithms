package algorithm.deprecated;

interface BitIndexedTree {

	void setValue(int index, int value);
	
	int getValue(int index);
	int getValue(int leftIndex, int rightIndex);
}
