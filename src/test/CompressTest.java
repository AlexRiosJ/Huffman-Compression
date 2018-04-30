package test;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import huffman.HuffmanCompressor;

public class CompressTest {

	public static void main(String[] args) {
		
		System.out.println("CompressTest.java");
		
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f, "Choose a text file to compress", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setVisible(true);
        try {
        	File file = fd.getFiles()[0];
            
    		System.out.println("Compressing. . .");
    		HuffmanCompressor.compress(file.getAbsolutePath());
    		System.out.println("Compress succeeded!");
        } catch (IndexOutOfBoundsException e) {
        	System.out.println("No file selected.\nExiting. . .");
        }
        
        f.dispose();
		
	}

}
