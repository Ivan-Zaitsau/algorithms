package algorithm.numeric;

public interface PrimalityTest {
	
	PrimalityTest MILLER_RABIN = new PrimalityTest() {
		
		private long mulMod(long a, long b, long m) {
			if (a >= m) a %= m;
			if (b >= m) b %= m;
			long d = 0;
			for (int i = 0; i < 64; i++) {
				d <<= 1;
				if (d >= m) d -= m;
				if ((a & 0x8000000000000000L) != 0) {
					d += b;
					if (d >= m) d -= m;
				}
				a <<= 1;
			}
			return d;
		}
		
		private long powMod(long a, long p, long m) {
			long ap = a;
			long r = 1;
			while (p > 0) {
				if ((p & 1) > 0) r = mulMod(r, ap, m);
				p >>>= 1;
				ap = mulMod(ap, ap, m);
			}
			return r;
		}

		private boolean isDivisibleBy235(long n) {
			if ((n & 1) == 0)
				return true;
			n = (n >>> 32) + (n & 0xFFFFFFFFL);
			n = (n >>> 16) + (n & 0xFFFF);
			n = (n >>>  8) + (n & 0xFF);
			n = (n >>>  4) + (n & 0xF);
			return ((1L << n) & 0x92CD259A4B349669L) != 0;
		}

		@Override
		public boolean isPrime(long n) {
			if (n == 2 | n == 3 | n == 5)
				return true;
			if (n <= 1 || isDivisibleBy235(n))
				return false;
			// - this sequence of witnesses ensures correct answer for any 64 bit integer
			long[] witnesses = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};
			int s = Long.numberOfTrailingZeros(n-1);
			long d = (n-1) >>> s;
			nextWitness:
			for (int i = 0; i < witnesses.length && witnesses[i] < n; i++) {
				long x = powMod(witnesses[i], d, n);
				if (x == 1 | x == n-1) continue nextWitness;
				for (int j = 0; j < s-1; j++) {
					x = mulMod(x, x, n);
					if (x == 1) return false;
					if (x == n-1) continue nextWitness;
				}
				return false;
			}
			return true;
		}
	};
	
	boolean isPrime(long v);
}
