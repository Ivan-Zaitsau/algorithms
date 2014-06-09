package algorithm.graph;

import java.util.Arrays;

public class PairMatcher {
	
	public static final int NOT_MATCHED = -1;

	private int n;
	private int m;
	private int[] matches;
	private int[][] possiblePairs;
	
	public PairMatcher(int n, int m, int[][] possiblePairs) {
		this.n = n;
		this.m = m;
		this.possiblePairs = new int[n][];
		for (int i = 0; i < n; i++) {
			this.possiblePairs[i] = Arrays.copyOf(possiblePairs[i], possiblePairs[i].length);
		}
	}
	
	private boolean improveMatching(boolean[] tried, int i) {
		tried[i] = true;
		int[] pairs = possiblePairs[i];
		for (int j = 0; j < pairs.length; j++) {
			int ii = pairs[j];
			if (matches[n + ii] == NOT_MATCHED) {
				matches[i] = ii;
				matches[n + ii] = i;
				return true;
			}
		}
		for (int j = 0; j < pairs.length; j++) {
			int ii = pairs[j];
			int ni = matches[n + ii];
			if (!tried[ni] && improveMatching(tried, ni)) {
				matches[i] = ii;
				matches[n + ii] = i;
				return true;
			}
		}
		return false;
	}
	
	private boolean improveMatching() {
		boolean[] tried = new boolean[n];
		for (int i = 0; i < n; i++)
			if (matches[i] == NOT_MATCHED & !tried[i] && improveMatching(tried, i)) 
				return true;
		return false;
	}
	
	public synchronized int[] getMatches() {
		if (matches == null) {
			matches = new int[n+m];
			Arrays.fill(matches, NOT_MATCHED);
			while(improveMatching());
			matches = Arrays.copyOf(matches, n);
		}
		return matches;
	}
}
