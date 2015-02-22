package algorithm.datastructure.tree;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	
	private Node root = new Node();
	
	private static class Node {
		Map<Character, Node> children = new HashMap<>();
	}
	
	public int add(CharSequence s) {
		int prefix = 0;
		Node node = root;
		for (int i = 0; i < s.length(); i++) {
			Character ch = s.charAt(i);
			Node nextNode = node.children.get(ch);
			if (nextNode == null) {
				nextNode = new Node();
				node.children.put(ch, nextNode);
			}
			else prefix++;
		}
		return prefix;
	}
}
