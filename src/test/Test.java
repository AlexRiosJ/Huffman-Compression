package test;

import huffman.HuffmanCompressor;
import huffman.HuffmanDecompressor;

public class Test {

	public static void main(String[] args) {
		
		System.out.println("Test.java");
		System.out.println("Compress");
		HuffmanCompressor.compress("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		System.out.println("Decompress");
		HuffmanDecompressor.decompress("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\data.bin");
	}

}
