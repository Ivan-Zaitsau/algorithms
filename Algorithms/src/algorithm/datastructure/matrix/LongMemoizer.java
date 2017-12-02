package algorithm.datastructure.matrix;

import java.util.Arrays;

public class LongMemoizer {

	private final long undefined;
	private final long[] data;
	private final int[] dimensions;
	
	public LongMemoizer(long undefined, int... dimensions) {
		this.undefined = undefined;
		this.dimensions = dimensions.clone();
		int size = 1;
		for (int dim : this.dimensions)
			size *= dim;
		data = new long[size];
		Arrays.fill(data, undefined);
	}

	public long getValue(int... ids) {
		int globalId = 0;
		for (int i = 0; i < dimensions.length; i++) {
			if (ids[i] >= dimensions[i])
				return undefined;
			globalId = globalId * dimensions[i] + ids[i];
		}
		return data[globalId];
	}

	public void setValue(long value, int... ids) {
		int globalId = 0;
		for (int i = 0; i < dimensions.length; i++) {
			if (ids[i] >= dimensions[i])
				return;
			globalId = globalId * dimensions[i] + ids[i];
		}
		data[globalId] = value;
	}
}
