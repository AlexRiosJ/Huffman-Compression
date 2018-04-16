package huffman;

public class Tree {
	
	private int size;
	
	public Tree() {
		
	}
	
	public int getSize() {
		return size;
	}
	
	public static class Node implements Comparable<Node> {

		public char character;
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
			return ("[" + this.character + ", " + this.frequency + "]");
		}

		@Override
		public int compareTo(Node node) {
			return this.frequency - node.frequency;
		}

	}
}
