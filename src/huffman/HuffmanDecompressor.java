package huffman;

import java.io.*;

public class HuffmanDecompressor {
	
	static Tree tree;
	static Tree.Node n;
	
	public static void decompress(String filePath) {
		
		try {

			FileInputStream  is = new FileInputStream(filePath);
			ObjectInputStream ois;

			byte[] treeBytes;

			int treeSize = 0;
			int dataToRead;

			for(int i = 0; i < 4; i++){
				dataToRead = is.read();
				treeSize <<= 8;
				treeSize += (dataToRead & 0x000000FF);
			}

			System.out.println(treeSize);


			treeBytes = new byte[treeSize];

			for(int i = 0; i < treeSize; i++){
				dataToRead = is.read();
				treeBytes[i] = (byte) (dataToRead & 0x000000FF);
			}
			
			ByteArrayInputStream bais = new ByteArrayInputStream(treeBytes);
			
			ois = new ObjectInputStream(bais);
			
			tree = (Tree) ois.readObject();
			ois.close();
			bais.close();
			// tree.print();

			byte bit;
			char characterToWrite = ' ';
			boolean ended = false;
			n = tree.getRoot();
			int charactersToRead = tree.getRoot().frequency;
			int charactersReaded = 0;
			String outFilePath = filePath.substring(0, filePath.lastIndexOf('\\')) + "\\out.txt";
			PrintWriter pr = new PrintWriter(outFilePath);

			while(!ended && (dataToRead = is.read()) != -1){
				// System.out.printf("%d, %H \n", dataToRead & 0x000000FF, dataToRead & 0x000000FF);
				for(int i = 7; i >= 0; i--){
					bit = (byte) ((dataToRead >> i) & 0x01);
					if(searchCharacter(bit)){
						charactersReaded++;
						characterToWrite = n.character;
						n = tree.getRoot();
						pr.write(characterToWrite);
					}
					if(charactersReaded == charactersToRead){
						ended = true;
						break;
					}
				}
			}

			pr.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private static boolean searchCharacter(byte bit){
		if(bit == 0){
			n = n.left;
		}else{
			n = n.right;
		}

		if(n.left == null){
			return true;
		}
		return false;
	}
	
}
