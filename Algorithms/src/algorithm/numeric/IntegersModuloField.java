package algorithm.numeric;

import java.util.BitSet;

public class IntegersModuloField {

	private final int mod;
	
	public IntegersModuloField(int mod) {
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
	
	private volatile int[] factorialsCache;
	private volatile int density;
	
	public synchronized void enableFactorialsCache(int upTo, int density) {
		if (density < 1)
			density = 1;
		if (upTo >= mod)
			upTo = mod-1;

		factorialsCache = new int[1 + upTo / density];
		this.density = density;
		int factorial = 1;
		int n = 1;
		for (int i = 0; i < factorialsCache.length; i++) {
			factorialsCache[i] = factorial;
			for (int j = 0; j < density; j++, n++)
				factorial = mul(factorial, n);
		}
	}
	
	public int factorial(int n) {
		if (n >= mod)
			return 0;
		
		int fact = 1;
		int i = 1;
		synchronized (this) {
			if (factorialsCache != null) {
				final int fci = (density * (factorialsCache.length - 1) >= n) ? factorialsCache.length - 1 : n / density;
				fact = factorialsCache[fci];
				i = fci * density;
			}			
		}
		for (i++; i <= n; i++)
			fact = mul(fact, i);
		
		return fact;
	}
	
	// - helper method
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

	// - number of combinations of n elements in k positions
	public int C(final int n, final int k) {
		if (n-k < k)
			return C(n, n-k);

		BitSet isComposite = new BitSet(k+1);
		int[] remainders = new int[k+1];
		for (int i = 1; i <= k; i++)
			remainders[i] = n-k+i;

		int result = 1;			
		for (int i = 2; i <= k; i++) {
			// - prime
			if (!isComposite.get(i)) {
				// - mark as not primes
				for (int j = i*i; j <= k; j+=i) {
					isComposite.set(j);
				}
				result = mul(result, pow(i, factorialDivCounts(n, i) - factorialDivCounts(n - k, i) - factorialDivCounts(k, i)));
				// - update remainders
				for (int j = i - (n-k) % i; j <= k; j += i)
					while (remainders[j] % i == 0)
						remainders[j] /= i;
			}
		}
		
		for (int i = 1; i <= k; i++) {
			if (remainders[i] > 0)
				result = mul(result, remainders[i]);
		}
		return result;
	}
}
