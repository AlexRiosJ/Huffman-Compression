package huffman;
import java.io.*;
import java.util.HashMap;

public class HuffmanCompressor {

	public static void main(String[] args) {
		HashMap<Character, Integer> charFreq = charFreqFromFile("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		
		Tree tree = new Tree(charFreq);
		tree.print();
		
		FileOutputStream fos;
		FileInputStream fis;
		ByteArrayOutputStream bos;
		
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
