package algorithm.graph;

import java.util.Arrays;

final public class GraphUtil {
	
	static public int[][] toNearby(int[][] weights, int isUnconnected) {
		int[][] nearby = new int[weights.length][];
		int[] near = new int[weights.length];
		for (int i = 0; i < weights.length; i++) {
			int nearbyAmount = 0;
			for (int j = 0; j < weights[i].length; j++)
				if (weights[i][j] != isUnconnected) {
					near[nearbyAmount++] = j;
				}
			nearby[i] = Arrays.copyOf(near, nearbyAmount);
		}
		return nearby;
	}
	
	private GraphUtil() {};
	
}