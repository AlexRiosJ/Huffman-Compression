package test;

import huffman.HuffmanCompressor;
import huffman.HuffmanDecompressor;

public class Test {

	public static void main(String[] args) {
		
		System.out.println("Test.java");
		System.out.println("Compress");
		HuffmanCompressor.compress("C:\\Desarrollo\\Source\\Repos\\HuffmanCompression\\src\\treeTest.txt");
		System.out.println("Decompress");
		HuffmanDecompressor.decompress("C:\\Desarrollo\\Source\\Repos\\HuffmanCompression\\src\\data.bin");
		
	}

}
