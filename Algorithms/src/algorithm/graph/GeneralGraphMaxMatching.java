package algorithm.graph;

import java.util.Arrays;

//- Ford–Fulkerson-Zaitsau algorithm
public class GeneralGraphMaxMatching {

	public static final int NOT_MATCHED = -1;

	private final int[][] possiblePairs;
	private int[] matches;
	
	public GeneralGraphMaxMatching(int[][] possiblePairs) {
		int n = possiblePairs.length;
		this.possiblePairs = new int[n][];
		for (int i = 0; i < n; i++) {
			this.possiblePairs[i] = Arrays.copyOf(possiblePairs[i], possiblePairs[i].length);
		}
	}
	
	private boolean improveMatching(boolean[] used, boolean[][] tried, int i) {
		used[i] = true;
		tried[0][i] = true;
		int[] near = possiblePairs[i];
		for (int j = 0; j < near.length; j++) {
			int ii = near[j];
			if (!used[ii] & matches[ii] == NOT_MATCHED) {
				matches[i] = ii;
				matches[ii] = i;
				used[i] = false;
				return true;
			}
		}
		for (int j = 0; j < near.length; j++) {
			int ii = near[j];
			if (!used[ii] & !tried[1][ii]) {
				used[ii] = true;
				tried[1][ii] = true;
				int ni = matches[ii];
				if (!used[ni] && !tried[0][ni] && improveMatching(used, tried, ni)) {
					matches[i] = ii;
					matches[ii] = i;
					used[i] = false;
					used[ii] = false;
					return true;
				}
				used[ii] = false;
			}
		}
		used[i] = false;
		return false;
	}
	
	private boolean improveMatching() {
		boolean improved = false;
		for (int i = 0; i < possiblePairs.length; i++)
			if (matches[i] == NOT_MATCHED) {
				if (improveMatching(new boolean[possiblePairs.length], new boolean[2][possiblePairs.length], i))
					improved = true;
			}
		return improved;
	}

	public int[] getMatches() {
		if (matches == null) {
			matches = new int[possiblePairs.length];
			Arrays.fill(matches, NOT_MATCHED);
			while (improveMatching());
			matches = Arrays.copyOf(matches, matches.length);
		}
		return matches;
	}
}

