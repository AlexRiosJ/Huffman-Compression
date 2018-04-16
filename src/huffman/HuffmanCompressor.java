package huffman;
import java.io.*;
import java.util.HashMap;

public class HuffmanCompressor {
	
	private static HashMap<Character, Frame> codeTable;

	public static void main(String[] args) {
		HashMap<Character, Integer> charFreq = charFreqFromFile("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		
		Tree tree = new Tree(charFreq);
		tree.print();
		
		FileOutputStream fos;
		FileInputStream fis;
		ByteArrayOutputStream bos;
		
		codeTable = new HashMap<>();
		
		try {
			fos = new FileOutputStream("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(tree);
			fos.write(bos.toByteArray());
			oos.close();
			System.out.println(fos.toString());
			System.out.println(bos.size());
			fis = new FileInputStream("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Tree test = (Tree) ois.readObject();
			ois.close();
			System.out.println("\n\n");
			test.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		generateTable(tree);
		System.out.println(codeTable.toString());
		
		
		
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
	
	public static void generateTable(Tree tree){
		generateRecursive(tree.getRoot(), (int) 0x00, 0);
	}
	
	private static void generateRecursive(Tree.Node n, int mask, int length) {
		if(n.left == null) {
			codeTable.put(n.character, new Frame(mask, length));
			return;
		}
		
		generateRecursive(n.left, (int) ((mask << 1) + 0), length + 1);
		generateRecursive(n.right, (int) ((mask << 1) + 1), length + 1);
	}
	
	private static class Frame {
		
		private int mask = 0;
		private int length = 0;
		
		public Frame(int mask, int length) {
			this.mask = mask;
			this.length = length;
		}
		
		public int getMask() {
			return this.mask;
		}
		
		public int getLength() {
			return this.length;
		}
		
		public String toString() {
			return (String.format("<%h, %d>\n", this.getMask(), this.getLength()));
		}
		
	}

}
