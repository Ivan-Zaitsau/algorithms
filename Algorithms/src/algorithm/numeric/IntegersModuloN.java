package algorithm.numeric;

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
}
