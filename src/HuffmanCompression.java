import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HuffmanCompression {

	public static void main(String[] args) {
		HashMap<Character, Integer> charFreq = charFreqFromFile("C:\\Users\\Alejandro\\Documents\\GitHub\\Repositories\\HuffmanCompression\\src\\Harry Potter.txt");
		System.out.println(charFreq.toString());

	}
	
	public static HashMap<Character, Integer> charFreqFromFile(String file) {
		HashMap<Character, Integer> freqMap = new HashMap<>();
		try (FileReader fr = new FileReader(file);
	            BufferedReader br = new BufferedReader(fr);) {
			int aux;
	        while ((aux = br.read()) != -1) {
	        	System.out.print((char) aux);
	            if(freqMap.containsKey((char) aux)) {
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

}
