package test;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import huffman.HuffmanDecompressor;

public class DecompressTest {

	public static void main(String[] args) {

		System.out.println("DecompressTest.java");

		Frame f = new Frame();
		FileDialog fd = new FileDialog(f, "Choose a binary file to decompress", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setVisible(true);
		try {
			File file = fd.getFiles()[0];

			System.out.println("Decompressing. . .");
			HuffmanDecompressor.decompress(file.getAbsolutePath());
			System.out.println("Decompress succeeded!");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No file selected.\nExiting. . .");
		}

		f.dispose();

	}

}
