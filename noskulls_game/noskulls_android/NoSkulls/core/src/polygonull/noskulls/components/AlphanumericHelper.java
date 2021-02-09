package polygonull.noskulls.components;

import java.util.ArrayList;

public class AlphanumericHelper {

	private String alphanumerics;

	public AlphanumericHelper(String alphanumerics) {
		this.alphanumerics = alphanumerics;
	}

	public int calcSymbolOffset(char symbol) { return alphanumerics.indexOf(symbol); }

	public ArrayList<Integer> stringToOffsets(String message) {
		ArrayList<Integer> offsets = new ArrayList<Integer>();
		for (int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			offsets.add(calcSymbolOffset(c));
		}
		return offsets;
	}

}
