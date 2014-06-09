import java.util.Arrays;


class Maximizer {
	
	private int indexSize;
	private long[] lStack;
	private long[] rStack;
	private long[] data;
	private long[] idata;
	
	public Maximizer(int size) {
		this.indexSize = 1;
		int levels = 1;
		while (this.indexSize < size) {
			this.indexSize *= 2;
			levels++;
		}
		this.data = new long[this.indexSize + size];
		this.idata = new long[this.indexSize + size];
		this.lStack = new long[levels];
		this.rStack = new long[levels];
	}
	
	public void update(int l, int r, int value) {
		l += indexSize;
		r += indexSize;
		while (l <= r) {
			if ((l & 1) == 1) {
				idata[l] += value;
				data[l >>> 1] = Math.max(data[l] + idata[l], data[l-1] + idata[l-1]);
				l++;
			}
			if ((r & 1) == 0) {
				idata[r] += value;
				data[r >>> 1] = Math.max(data[r] + idata[r], data[r+1] + idata[r+1]);
				r--;
			}
			l >>>= 1;
			r >>>= 1;
		}
	}
	
	public long get(int l, int r) {
		// - fill l-r stacks

		// - evaluate max value
		return 0;
	}
}

public class Test {
	
	public static int[][] reIndex(int[] a) {
		// - array mapping
		int[] mapping = Arrays.copyOf(a, a.length);
		Arrays.sort(mapping);
		int i = 1;
		while(i < mapping.length && mapping[i-1] < mapping[i]) i++;
		int j = i;
		while (++i < a.length)
			if (mapping[i-1] < mapping[i])
				mapping[j++] = mapping[i];
		mapping = Arrays.copyOf(mapping, j);
		// - re-indexed array
		int[] array = new int[a.length];
		for (i = 0; i < a.length; i++) array[i] = Arrays.binarySearch(mapping, a[i]);
		return new int[][] {array, mapping};
	}

	static void print(int... values) {
		for (int value : values) System.out.print(value + " ");
		System.out.println();
	}
	
	public static void main(String[] args) {
		int[][] result = reIndex(new int[]{-22, -3, 2, 4, 1, 2, 7, -40, -40});
		print(result[0]);
		print(result[1]);
	}
}
