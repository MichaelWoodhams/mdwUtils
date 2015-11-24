package mdwUtils;
/*
 * 
 * Licensed under the Creative Commons Zero (CC0) license (https://creativecommons.org/publicdomain/zero/1.0/)
 * 
 */

public class Doubles {
	/*
	 * Not suitable for numbers which get expressed in scientific (i.e. exponential) format
	 * TODO: Fix this, or make it an error.
	 */
	public static int numDecimalPlaces(double x) {
		String str = Double.toString(x);
		int n = str.indexOf('.');
		if (n==-1) {
			// '.' did not appear at all
			return 0;
		} else {
			return str.length()-n-1;
		}
	}
}
