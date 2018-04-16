package huffman;

import java.io.FileInputStream;
import java.util.Stack;

public class HuffmanDecompressor {
	
	int cursor;
	
	public HuffmanDecompressor() {
		this.cursor = 0;
	}
	
	/**
	 * Decompress a file 
	 * @param bytes The file to decompress
	 * @return The content of the file decompressed
	 */
	public String decompressFromByteArray(byte[] bytes) {
		Tree tree = deserialize(bytes);
		return null;
	}
	
	private Tree deserialize(byte[] bytes) {
		return null;
	}
}
