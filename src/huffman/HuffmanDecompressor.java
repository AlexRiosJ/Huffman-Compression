package huffman;

import java.io.*;

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
			is.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
