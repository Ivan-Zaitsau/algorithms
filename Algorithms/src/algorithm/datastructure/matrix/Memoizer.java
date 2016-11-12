package algorithm.datastructure.matrix;

import java.util.Arrays;
import java.util.BitSet;

public class Memoizer {
	
	private BitSet data;
	private int[] dimensions;
	
	public Memoizer(int... dimensions) {
		this.dimensions = Arrays.copyOf(dimensions, dimensions.length);
		int size = 1;
		for (int dim : this.dimensions)
			size *= dim;
		data = new BitSet(size+1);
	}
	
	public boolean wasThere(int... ids) {
		int globalId = 0;
		for (int i = 0; i < dimensions.length; i++) {
			if (ids[i] >= dimensions[i])
				return false;
			globalId = globalId * dimensions[i] + ids[i];
		}
		if (data.get(globalId)) {
			return true;
		}
		else {
			data.set(globalId);
			return false;
		}
	}
}
