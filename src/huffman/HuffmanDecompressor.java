package huffman;

import java.io.*;
import java.util.Arrays;

public class HuffmanDecompressor {
	
	static Tree tree;
	
	public static void decompress(String filePath) {
		
		try {
			
			InputStream is = new FileInputStream(filePath);
			ObjectInputStream ois;
		
			byte[] treeBytes;
			
			byte[] cbuf = new byte[4];
			int treeSize = 0;
			
			is.read(cbuf, 0, 4);
			treeSize += (cbuf[0] & 0x000000FF);
			treeSize <<= 8;
			treeSize += (cbuf[1] & 0x000000FF);
			treeSize <<= 8;
			treeSize += (cbuf[2] & 0x000000FF);
			treeSize <<= 8;
			treeSize += (cbuf[3] & 0x000000FF);
			
			System.out.println(treeSize);
			
			treeBytes = new byte[treeSize + 4];
			
			is.read(treeBytes, 4, treeSize);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(treeBytes, 4, treeBytes.length);
			
			ois = new ObjectInputStream(bais);
			
			tree = (Tree) ois.readObject();
			ois.close();
			tree.print();
			
			int fileLength = (int) new File(filePath).length();
			byte[] data = new byte[fileLength - (treeSize + 4)];
			byte[] fullData = new byte[fileLength];
			is.read(fullData, 0 , fileLength);
			is.close();
			for(byte b : fullData) {
				b = (byte) (b & 0x000000FF);
			}			
			for(int i = fileLength - 1; i >= fileLength - data.length; i--)
				data[i - (treeSize + 4)] = fullData[i];
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	private static void decode(byte[] data) {
		
	}
	
}
