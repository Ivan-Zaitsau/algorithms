package algorithm.datastructure.array;

public class BooleanArray {
	
	private static int ADDRESS_BITS = 6;
	private static int BITS = 1 << ADDRESS_BITS;
	private static int MASK = BITS - 1;
	
	private int length;
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
	
	public boolean get(int index) {
		return (data[index >>> ADDRESS_BITS] & (1L << (index & MASK))) > 0;
	}
	
	public int length() {
		return length;
	}
}
