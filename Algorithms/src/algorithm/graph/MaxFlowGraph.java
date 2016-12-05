package algorithm.graph;

public class MaxFlowGraph {
	
	private final int nodes;
	private final int srcId;
	private final int trgId;
	private final long[][] weights;

	private long maxWeight;
	private long maxFlow;
	
	public MaxFlowGraph(int nodes, int srcId, int trgId) {
		this.nodes = nodes;
		this.srcId = srcId;
		this.trgId = trgId;
		this.weights = new long[nodes][nodes];
	}
	
	public void addEdge(int srcId, int trgId, long value) {
		weights[srcId][trgId] += value;
		maxWeight = Math.max(maxWeight, weights[srcId][trgId]);
	}
	
	private long updateFlow(int scrId, int trgId, long value) {
		weights[srcId][trgId] -= value;
		weights[trgId][srcId] += value;
		return value;
	}
	
	private long improveFlow(int nodeId, boolean[] isVisited, long minWeight, long weightThreshold) {
		
		long[] currentWeights = weights[nodeId];
		if (currentWeights[trgId] >= weightThreshold) {
			return updateFlow(nodeId, trgId, Math.min(minWeight, currentWeights[trgId]));
		}
		
		isVisited[nodeId] = true;
		for (int i = 0; i < nodes; i++) {
			if (!isVisited[i] && currentWeights[i] >= weightThreshold) {
				long flowImprovement = improveFlow(i, isVisited, Math.min(minWeight, currentWeights[i]), weightThreshold);
				if (flowImprovement > 0) {
					return updateFlow(nodeId, i, flowImprovement);
				}
			}
		}
		return 0;
	}
	
	private long improveFlow(long weightThreshold) {
		return improveFlow(srcId, new boolean[nodes], Long.MAX_VALUE, weightThreshold);
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
