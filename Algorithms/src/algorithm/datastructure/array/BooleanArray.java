package algorithm.datastructure.array;

public class BooleanArray {
	
	private static final int ADDRESS_BITS = 6;
	private static final int BITS = 1 << ADDRESS_BITS;
	private static final int MASK = BITS - 1;
	
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
}
