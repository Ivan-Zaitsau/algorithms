package algorithm.graph;

import java.util.Arrays;

public class MinSpanningTree {
	
	public static final class Edge implements Comparable<Edge> {
		final int srcId;
		final int trgId;
		final long value;
		
		Edge(int srcId, int trgId, long value) {
			this.srcId = srcId;
			this.trgId = trgId;
			this.value = value;
		}
		
		public int compareTo(Edge o) {
			return (value < o.value) ? -1 : (value > o.value) ? 1 : 0;
		}
	}
	
	private static final class NodeEntry {
		NodesSet owner;
		NodeEntry next;		
	}
	
	private static final class NodesSet {
		int size;
		NodeEntry first;
		NodeEntry last;
		
		NodesSet(NodeEntry node) {
			size = 1;
			first = node;
			last = node;
		}
		
		static void merge(NodesSet set1, NodesSet set2) {
			if (set1.size < set2.size) {
				merge(set2, set1);
				return;
			}
			set1.size += set2.size;
			set1.last.next = set2.first;
			set1.last = set2.last;
			NodeEntry node = set2.first;
			while (node != null) {
				node.owner = set1;
				node = node.next;
			}
		}
	}
	
	private int nodesCount;
	private int edgesCount;
	private Edge[] edges;
	
	public MinSpanningTree(int nodes) {
		this.nodesCount = nodes;
		this.edgesCount = 0;
		this.edges = new Edge[nodesCount];
	}
	
	public void addEdge(int srcId, int trgId, long value) {
		if (edgesCount == edges.length) {
			edges = Arrays.copyOf(edges, 2 * edges.length);
		}
		edges[edgesCount++] = new Edge(srcId, trgId, value);
	}
	
	public Edge[] getMinSpanningTree() {
		NodeEntry[] nodes = new NodeEntry[nodesCount];
		for (int i = 0; i < nodesCount; i++) {
			nodes[i] = new NodeEntry();
			nodes[i].owner = new NodesSet(nodes[i]);
		}
		Arrays.sort(edges, 0, edgesCount);
		Edge[] spanningTree = new Edge[nodesCount-1];
		int k = 0;
		for (int i = 0; i < edgesCount & k+1 < nodesCount; i++) {
			NodesSet set1 = nodes[edges[i].srcId].owner;
			NodesSet set2 = nodes[edges[i].trgId].owner;
			if (set1 != set2) {
				NodesSet.merge(set1, set2);
				spanningTree[k++] = edges[i];
			}
		}
		return (k+1 < nodesCount) ? null : spanningTree;
	}
	
	public long getMinSpanningTreeSize() {
		Edge[] spanningTree = getMinSpanningTree();
		if (spanningTree == null) return -1;
		long treeSize = 0;
		for (Edge edge : edges) treeSize += edge.value;
		return treeSize;
	}
}
