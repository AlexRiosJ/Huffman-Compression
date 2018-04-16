import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCompression {

	public static void main(String[] args) {
		HashMap<Character, Integer> charFreq = charFreqFromFile(
				"C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		PriorityQueue<Node> queue = new PriorityQueue<>();
		for (int i = 0; i < 256; i++) {
			if (charFreq.containsKey((char) i))
				queue.add(new Node((char) i, charFreq.get((char) i)));
		}
		System.out.println("List of Nodes (char, frequency)");
		System.out.println("Sorted by char value: ");
		System.out.println(queue.toString());
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

	public static HashMap<Character, Integer> charFreqFromFile(String file) {
		HashMap<Character, Integer> freqMap = new HashMap<>();
		try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {
			int aux;
			while ((aux = br.read()) != -1) {
				if (freqMap.containsKey((char) aux)) {
					freqMap.replace((char) aux, freqMap.get((char) aux) + 1);
				} else {
					freqMap.put((char) aux, 1);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Missing file");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Empty file");
			System.exit(0);
		}
		return freqMap;
	}

}
