package huffman;

import java.io.*;
import java.util.HashMap;

public class HuffmanCompressor {

	private static HashMap<Character, Frame> codeTable;

	public static void main(String[] args) {

		String filePath = "C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\treeTest.txt";

		HashMap<Character, Integer> charFreq = charFreqFromFile(filePath);

		Tree tree = new Tree(charFreq);
		tree.print();

		FileOutputStream fos;
		FileInputStream fis;
		ByteArrayOutputStream bos;

		codeTable = new HashMap<>();

		try {
			fos = new FileOutputStream(
					"C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(tree);
			fos.write(bos.toByteArray());
			oos.close();
			System.out.println(fos.toString());
			System.out.println(bos.size());
			fis = new FileInputStream(
					"C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Tree test = (Tree) ois.readObject();
			ois.close();
			System.out.println("\n\n");
			test.print();

			generateTable(tree);
			System.out.println(codeTable.toString());

			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();

			int aux;

			try {
				while ((aux = br.read()) != -1) {
					System.out.print((char) aux);
					sb.append((char) aux);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			String stringToEncode = sb.toString();

			try {
				encode(fos, stringToEncode);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public static void generateTable(Tree tree) {
		generateRecursive(tree.getRoot(), "");
	}

	private static void generateRecursive(Tree.Node n, String mask) {
		if (n.left == null) {
			codeTable.put(n.character, new Frame(mask));
			return;
		}
		generateRecursive(n.left, mask + "0");
		generateRecursive(n.right, mask + "1");
	}

	private static void encode(FileOutputStream fos, String stringToEncode) throws IOException {
		ByteArrayOutputStream baos;

		byte[] buffer = new byte[2];

		char element;
		String newByte;

		int currentElementsAdded = 0;
		int maskAux = 0;
		byte byteToAdd = 0;
		
		System.out.println();
		for (int i = 0; i < stringToEncode.length(); i++) {
			element = stringToEncode.charAt(i);
			newByte = codeTable.get(element).mask;
			System.out.print(newByte);
			for (int j = 0; j < newByte.length(); j++) {
				// Agregar elementos al buffer
				if (maskAux < 8) {
					byteToAdd += (newByte.charAt(j) - '0');
					byteToAdd <<= 1;
					maskAux++;
				} else {
					if (currentElementsAdded < 2) {
						buffer[currentElementsAdded] = byteToAdd;
						currentElementsAdded++;
						byteToAdd = 0;
						byteToAdd += (newByte.charAt(j) - '0');
						byteToAdd <<= 1;
						maskAux = 1;
					} else {
						baos = new ByteArrayOutputStream(2);
						baos.write(buffer, 0, 2);
						baos.writeTo(fos);
						buffer = new byte[2];
						currentElementsAdded = 0;
						buffer[currentElementsAdded] = byteToAdd;
						currentElementsAdded++;
						byteToAdd = 0;
						byteToAdd += (newByte.charAt(j) - '0');
						byteToAdd <<= 1;
						maskAux = 1;
					}
				}
			}
		}

		baos = new ByteArrayOutputStream(currentElementsAdded + 1);
		baos.write(buffer, 0, 2);
		baos.writeTo(fos);
		
	}

	private static class Frame {

		private String mask = "";

		public Frame(String mask) {
			this.mask = mask;
		}

		public String getMask() {
			return this.mask;
		}

		public String toString() {
			return (String.format("<%s, %d>\n", this.getMask(), this.getMask().length()));
		}

	}

}
