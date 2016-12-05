package algorithm.datastructure.array;

import java.util.Arrays;

public class BooleanArray {
	
	private static final int ADDRESS_BITS = 6;
	private static final int MASK = (1 << ADDRESS_BITS) - 1;
	
	public final int length;
	
	private long[] data;

	public BooleanArray(int size) {
		this.length = size;
		this.data = new long[1 + ((size-1) >>> ADDRESS_BITS)];
	}

	public void setTrue(int index) {
		data[index >>> ADDRESS_BITS] |= 1L << (index & MASK);
	}
	
	public void setFalse(int index) {
		data[index >>> ADDRESS_BITS] &= ~(1L << (index & MASK));
	}
	
	public void set(int index, boolean value) {
		if (value)
			setTrue(index);
		else
			setFalse(index);
	}
	
	public boolean get(int index) {
		return (data[index >>> ADDRESS_BITS] & (1L << (index & MASK))) != 0;
	}
	
	public int bitCount(int fromIndex, int toIndex) {
		if (fromIndex >= toIndex)
			return 0;
		int fromIndexHigh = fromIndex >>> ADDRESS_BITS;
		int fromIndexLow = fromIndex & MASK;
		int toIndexHigh = toIndex >>> ADDRESS_BITS;
		int toIndexLow = toIndex & MASK;
		if (fromIndexHigh == toIndexHigh)
			return Long.bitCount(data[fromIndexHigh] & ~((1L << fromIndexLow) - 1) & ((1L << toIndexLow) - 1));
		int result = 0;
		if (fromIndexLow > 0) {
			result += Long.bitCount(data[fromIndexHigh] & ~((1L << fromIndexLow) - 1));;
			fromIndexHigh++;
		}
		if (toIndexLow > 0) {
			result += Long.bitCount(data[toIndexHigh] & ((1L << toIndexLow) - 1));
		}
		for (int i = fromIndexHigh; i < toIndexHigh; i++) {
			result += Long.bitCount(data[i]);
		}
		return result;
	}
	
	public void clear() {
		Arrays.fill(data, 0);
	}
}
