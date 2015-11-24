package mdwUtils;

import java.util.Arrays;

/*
 * Code from stackoverflow is by default licenced CC-BY-SA http://creativecommons.org/licenses/by-sa/2.5/
 */
public class ArrayUtils {
	// Code from http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
	@SafeVarargs
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}	
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
		    offset += array.length;
		}
		return result;
	}
}
