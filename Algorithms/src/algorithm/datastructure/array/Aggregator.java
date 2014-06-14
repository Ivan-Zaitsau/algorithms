package algorithm.datastructure.array;

public abstract class Aggregator {
	
	private int indexSize;
	private long[] data;
	
	public Aggregator(int size) {
		indexSize = 1;
		while (indexSize < size) indexSize <<= 1;
		data = new long[indexSize + size];
	}
	
	abstract protected long merge(long oldValue, long newValue);
	abstract protected long init();
	abstract protected long aggregate(long a, long b);

	public void updateValue(int index, long value) {
		index += indexSize;
		data[index] = merge(data[index], value);
		while (index > 1) {
			data[index >>> 1] = aggregate(data[index], data[index^1]);
			index >>>= 1;
		}
	}

	public long getAggregatedResult(int l, int r) {
		long result = init();
		l += indexSize;
		r += indexSize;
		while (l <= r) {
			if ((l & 1) == 1) {
				result = aggregate(result, data[l]);
				l++;
			}
			l >>>= 1;
			if ((r & 1) == 0) {
				result = aggregate(result, data[r]);
				r--;
			}
			r >>>= 1;
		}
		return result;
	}
}
