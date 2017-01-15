package algorithm.util;

import java.util.Arrays;
import java.util.BitSet;

final public class BasicUtils {

	public static BitSet generateCompositeNumbersSet(int limit) {
		BitSet isComposite = new BitSet(limit+1);
		isComposite.set(0); isComposite.set(1);
		for (int j = 4; 0 <= j & j <= limit; j+=2) {
			isComposite.set(j);
		}
		for (int i = 3, limitSqrt = 1 + (int)Math.sqrt(limit); 0 <= i & i <= limitSqrt; i = isComposite.nextClearBit(i+1)) {
			for (int j = i*i; 0 <= j & j <= limit; j+=i+i) {
				isComposite.set(j);
			}
		}
		return isComposite;
	}

	public static int[] generatePrimes(int limit) {
		if (limit < 2)
			return new int[] {};
		BitSet isComposite = generateCompositeNumbersSet(limit);
		int[] primes = new int[limit - isComposite.cardinality() + 1];
		primes[0] = 2;
		for (int i = 3, j = 1; 0 < i & i <= limit; i+=2)
			if (!isComposite.get(i))
				primes[j++] = i;
		return primes;
	}

	public static long[] getDivisors(long v) {
		if (v <= 1)
			return new long[]{};
		int i = 0;
		long[] divisors = new long[62];
		while ((v & 1) == 0) {
			divisors[i++] = 2;
			v >>>= 1;
		}
		while (v % 3 == 0) {
			v /= 3;
			divisors[i++] = 3;
		}
		for (int p = 5, d = 2; p <= Integer.MAX_VALUE; p += d, d = 6-d) {
			while (v % p == 0) {
				v /= p;
				divisors[i++] = p;
			}
			if ((long)p*p > v)
				break;
		}
		if (i > 0 & v > 1)
			divisors[i++] = v;
		return Arrays.copyOf(divisors, i);
	}

	public static int gcd(final int a, final int b) {
		return (a > b) ? gcd(b, a) : (a > 0) ? gcd(b%a, a) : b;
	}
	
	public static long gcd(final long a, final long b) {
		return (a > b) ? gcd(b, a) : (a > 0) ? gcd(b%a, a) : b;
	}
	
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
