package algorithm.deprecated;

abstract class AbstractBitIndexedTree implements BitIndexedTree {

	int length;
	int[] data;

	int indexLength;
	int[] indexData;

	public AbstractBitIndexedTree(int size) {

		length = size;
		data = new int[length];

		indexLength = 1;
		while (indexLength < length)
			indexLength <<= 1;

		indexData = new int[indexLength];
	}

	abstract protected int accumulate(int a, int b);
	abstract protected int evaluateIndexedValue(int index);

	public void setValue(int index, int value) {
		// - update value
		data[index] = value;
		// - update index
		int ii = (index + indexLength) >>> 1;
		indexData[ii] = accumulate(evaluateIndexedValue(index & ~1), evaluateIndexedValue(index | 1));
		while (ii > 1) {
			ii >>>= 1; 
			indexData[ii] = accumulate(indexData[ii << 1], indexData[(ii << 1) + 1]);
		}
	}

	public int getValue(int index) {
		return evaluateIndexedValue(index);
	}

	public int getValue(int leftIndex, int rightIndex) {

		if (leftIndex == rightIndex)
			return getValue(leftIndex);

		int value = evaluateIndexedValue(leftIndex);
		leftIndex += 1;
		if ((leftIndex & 1) == 1) {
			value = accumulate(value, evaluateIndexedValue(leftIndex));
			leftIndex += 1;
		}
		if ((rightIndex & 1) == 0) {
			value = accumulate(value, evaluateIndexedValue(rightIndex));
			rightIndex -= 1;
		}
		int li = (leftIndex + indexLength) >>> 1;
		int ri = (rightIndex + indexLength) >>> 1;
		while (li <= ri) {
			if ((li & 1) == 1) {
				value = accumulate(value, indexData[li]);
				li += 1;
			}
			li >>>= 1;
			if ((ri & 1) == 0) {
				value = accumulate(value, indexData[ri]);
				ri -= 1;
			}
			ri >>>= 1;
		}
		return value;
	}
}
