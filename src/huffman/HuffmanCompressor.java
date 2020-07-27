package huffman;

import java.io.*;
import java.util.HashMap;

public class HuffmanCompressor {

	private static HashMap<Character, Frame> codeTable;

	public static void compress(String filePath) {

		Tree tree = new Tree(charFreqFromFile(filePath));
		// tree.print();

		FileOutputStream fos;
		ByteArrayOutputStream bos;

		codeTable = new HashMap<>();

		try {
			String filename = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.lastIndexOf('.'));
			String outputFilePath = filePath.substring(0, filePath.lastIndexOf('\\') + 1) + filename + "c.bin";
			fos = new FileOutputStream(outputFilePath);
			bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(tree);

			String binaryString = Integer.toBinaryString(bos.size());
			// System.out.println(binaryString);
			byte[] treeSize = new byte[4];
			int index = 0;
			int stringIndex = 0;
			for (int i = 0; i < treeSize.length; i++) {
				for (int j = 0; j < 8; j++) {
					treeSize[i] <<= 1;
					if (index >= 32 - binaryString.length()) {
						treeSize[i] += (binaryString.charAt(stringIndex) - '0');
						stringIndex++;
					}
					index++;
				}
			}
			// System.out.println(bos.size());
			fos.write(treeSize);
			fos.write(bos.toByteArray());
			oos.close();

			generateTable(tree);
			// System.out.println(codeTable.toString());

			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();

			int aux;

			try {
				while ((aux = br.read()) != -1) {
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

	private static HashMap<Character, Integer> charFreqFromFile(String file) {
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

	private static void generateTable(Tree tree) {
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

		int len = stringToEncode.length();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(len);

		byte[] buffer = new byte[len + 1];
		char element;
		String newByte;
		int maskAux = 0;
		byte byteToAdd = 0;
		int bytesWritten = 0;

		for (int i = 0; i < stringToEncode.length(); i++) {
			element = stringToEncode.charAt(i);
			newByte = codeTable.get(element).mask;
			for (int j = 0; j < newByte.length(); j++) {
				// Agregar elementos al buffer
				if (maskAux < 7) {
					byteToAdd += (newByte.charAt(j) - '0');
					byteToAdd <<= 1;
					maskAux++;
				} else if (maskAux == 7) {
					byteToAdd += (newByte.charAt(j) - '0');
					maskAux++;
				} else {
					buffer[bytesWritten] = byteToAdd;
					bytesWritten++;
					byteToAdd = 0;
					byteToAdd += (newByte.charAt(j) - '0');
					byteToAdd <<= 1;
					maskAux = 1;
				}
			}
		}

		byteToAdd <<= 7 - maskAux;

		buffer[bytesWritten] = byteToAdd;
		baos.write(buffer, 0, bytesWritten + 1);
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
