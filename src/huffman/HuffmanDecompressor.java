package huffman;

import java.io.*;

public class HuffmanDecompressor {
	
	static Tree tree;
	
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

			while((dataToRead = is.read()) != -1){
				System.out.printf("%d, %H \n", dataToRead & 0x000000FF, dataToRead & 0x000000FF);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
