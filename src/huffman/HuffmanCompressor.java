package huffman;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class HuffmanCompressor {

	public static void main(String[] args) {
		HashMap<Character, Integer> charFreq = charFreqFromFile("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		
		Tree tree = new Tree(charFreq);
		tree.print();
		
		FileOutputStream fos;
		FileInputStream fis;
		
		try {
			fos = new FileOutputStream("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tree);
			oos.close();
			System.out.println(fos.toString());
			
			fis = new FileInputStream("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Tree test = (Tree) ois.readObject();
			ois.close();
			System.out.println("\n\n\n\n");
			test.print();
		} catch (Exception e) {
			e.printStackTrace();
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
//	
//	public static byte[] serializeTree(Tree tree) {
//		byte[] frame = new byte[tree.getNullNodes() + tree.getLeafNodes() * 3];
//		
//		
//		
//		return frame;
//	}

}
