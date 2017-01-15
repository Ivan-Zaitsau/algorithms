package algorithm.numeric;

import java.util.Arrays;
import java.util.BitSet;

public class IntegersModuloN {

	private final int mod;
	
	public IntegersModuloN(int mod) {
		this.mod = mod;
	}
	
	public int add(int a, int b) {
		int r = a + b - mod;
		return r + (mod & (r >> 31));
	}

	public int sub(int a, int b) {
		int r = a - b;
		return r + (mod & (r >> 31));
	}

	public int mul(int a, int b) {
		return (int) (((long) a * b) % mod);
	}
	
	public int pow(int a, long power) {
		int result = 1;
		int e = a;
		while (power > 0) {
			if ((power & 1) > 0)
				result = mul(result, e);
			e = mul(e, e);
			power >>>= 1;
		}
		return result;
	}
	
	public int factorial(int n) {
		if (n >= mod)
			return 0;
		
		int fact = 1;
		for (int i = 2; i <= n; i++)
			fact = mul(fact, i);
		
		return fact;
	}
	
	public int nCk(final int n, final int k) {
		BitSet isComposite = generateCompositeNumbersSet(k);
		int[] divCounts = new int[k+1];
		for (int i = 2; i <= k; i++)
			if (!isComposite.get(i)) {
				divCounts[i] -= factorialDivCounts(k, i);
			}

		int result = 1;
		for (int i = n-k+1; i <= n; i++) {
			for (int divisor : getDivisors(i, k)) {
				if (divisor > k)
					result = mul(result, divisor);
				else
					divCounts[divisor]++;
			}
		}
		for (int divisor = 2; divisor <= k; divisor++)
			if (divCounts[divisor] > 0)
				result = mul(result, pow(divisor, divCounts[divisor]));
		return result;
	}
	
	// - helper methods
	private static int[] getDivisors(int v, int limit) {
		if (v <= 1)
			return new int[] {};
		if (limit < 2)
			return new int[] {v};
		
		int i = 0;
		int[] divisors = new int[30];
		while ((v & 1) == 0) {
			divisors[i++] = 2;
			v >>>= 1;
		}
		if (limit > 2) {
			while (v % 3 == 0) {
				v /= 3;
				divisors[i++] = 3;
			}
			for (int p = 5, d = 2; p <= limit & p * p <= v; p += d, d = 6-d) {
				while (v % p == 0) {
					v /= p;
					divisors[i++] = p;
				}
			}
		}
		if (v > 1)
			divisors[i++] = v;
		return Arrays.copyOf(divisors, i);
	}

	private static int factorialDivCounts(int n, int divisor) {
		if (divisor <= 1)
			throw new RuntimeException();
		int counts = 0;
		while (n >= divisor) {
			n /= divisor;
			counts += n;
		}
		return counts;
	}

	private static BitSet generateCompositeNumbersSet(int limit) {
		BitSet isComposite = new BitSet(limit+1);
		isComposite.set(0); isComposite.set(1);
		for (int j = 4; 0 <= j & j <= limit; j+=2) {
			isComposite.set(j);
		}
		for (int i = 3, limitSqrt = 1 + (int)Math.sqrt(limit); 0 < i & i <= limitSqrt; i = isComposite.nextClearBit(i+1)) {
			for (int j = i*i; 0 <= j & j <= limit; j+=i+i) {
				isComposite.set(j);
			}
		}
		return isComposite;
	}
}
