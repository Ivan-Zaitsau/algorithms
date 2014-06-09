package algorithm.util;

import java.util.Arrays;

final public class BasicUtils {
	
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
	
	public static long[][] reIndex(long[] a) {
		// - array mapping
		long[] mapping = Arrays.copyOf(a, a.length);
		Arrays.sort(mapping);
		int i = 1;
		while(i < mapping.length && mapping[i-1] < mapping[i]) i++;
		int j = i;
		while (++i < a.length)
			if (mapping[i-1] < mapping[i])
				mapping[j++] = mapping[i];
		mapping = Arrays.copyOf(mapping, j);
		// - re-indexed array
		long[] array = new long[a.length];
		for (i = 0; i < a.length; i++) array[i] = Arrays.binarySearch(mapping, a[i]);
		return new long[][] {array, mapping};
	}
	
	private BasicUtils() {};
}
