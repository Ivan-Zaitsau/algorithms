package algorithm.graph;

public class MaxFlowGraph {
	
	private int n;
	private int srcId;
	private int trgId;
	private long[][] weights;

	private long maxWeight;
	private long maxFlow;
	
	public MaxFlowGraph(int nodes, int srcId, int trgId) {
		this.n = nodes;
		this.srcId = srcId;
		this.trgId = trgId;
	}
	
	public void addEdge(int srcId, int trgId, long value) {
		weights[srcId][trgId] = value;
		maxWeight = Math.max(maxWeight, weights[srcId][trgId]);
	}
	
	private long improveFlow(int nodeId, boolean[] isVisited, long minWeight, long weightThreshold) {
		
		long[] currentWeights = weights[nodeId];
		if (currentWeights[trgId] >= weightThreshold) {
			long flowImprovement = Math.min(minWeight, currentWeights[trgId]);
			currentWeights[trgId] -= flowImprovement;
			weights[trgId][nodeId] += flowImprovement;
			return flowImprovement;
		}
		
		isVisited[nodeId] = true;
		for (int i = 0; i < n; i++) {
			if (!isVisited[i] && currentWeights[i] >= weightThreshold) {
				long flowImprovement = improveFlow(i, isVisited, Math.min(minWeight, currentWeights[i]), weightThreshold);
				if (flowImprovement > 0) {
					currentWeights[i] -= flowImprovement;
					weights[i][nodeId] += flowImprovement;
					return flowImprovement;
				}
			}
		}
		
		return 0;
	}
	
	private long improveFlow(long weightThreshold) {
		return improveFlow(srcId, new boolean[n], Long.MAX_VALUE, weightThreshold);
	}
	
	public long getFlowValue() {
		long weightThreshold = (maxWeight+1) / 2;
		while (weightThreshold > 0) {
			long flowUpdate;
			do {
				flowUpdate = improveFlow(weightThreshold);
				maxFlow += flowUpdate;
			} while (flowUpdate > 0);
			weightThreshold = weightThreshold / 2;
		}
		return maxFlow;
	}
}
