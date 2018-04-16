package huffman;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Tree implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Node root = null;
	
	public Tree(HashMap<Character, Integer> frequencyTable) {
		PriorityQueue<Node> queue = new PriorityQueue<>();

		for (int i = 0; i < Character.MAX_VALUE; i++) {
			if (frequencyTable.containsKey((char) i)) 
				queue.add(new Node((char) i, frequencyTable.get((char) i)));
		}
		
		Node nodeL;
		Node nodeR;
		Node nodeJoiner;
		
		while(queue.size() >= 2) {
			nodeL = queue.poll();
			nodeR = queue.poll();
			
			nodeJoiner = new Node((char) 0, nodeL.frequency + nodeR.frequency);
			nodeJoiner.left = nodeL;
			nodeJoiner.right = nodeR;
			
			queue.add(nodeJoiner);
		}
		
		this.root = queue.poll();
	}
	
	private void print(Node node, int spaces) {
		if (node != null) {
			String str = "";
			for(int i = 0; i < spaces; i ++) str += "|";
			System.out.println(str + node);
			print(node.left, spaces + 2);
			print(node.right, spaces + 2);
		}
	}
	
	public void print() {
		print(this.root, 0);
		System.out.println("-----------");
	}
	
	public static class Node implements Comparable<Node>, Serializable {
		
		private static final long serialVersionUID = 1L;
		public char character = '\0';
		public int frequency = 0;
		public Node left = null;
		public Node right = null;

		public Node(char character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}

		@Override
		public String toString() {
			if (this.character == '\n')
				return ("[" + "\\n" + ", " + this.frequency + "]");
			if (this.character == '\r')
				return ("[" + "\\r" + ", " + this.frequency + "]");
			if(this.character == '\0')
				return ("[" + "ñ" + ", " + this.frequency + "]");
			return ("[" + this.character + ", " + this.frequency + "]");
		}

		@Override
		public int compareTo(Node node) {
			return this.frequency - node.frequency;
		}

	}
}
