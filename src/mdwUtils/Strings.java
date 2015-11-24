package mdwUtils;
/*
 * 
 * Licensed under the Creative Commons Zero (CC0) license (https://creativecommons.org/publicdomain/zero/1.0/)
 * 
 */

import java.util.Vector;

public class Strings {
	/*
    * Arrays.toString(array) uses '[]' delimiters.
    * It would be impolite to do this in a Nexus file, so make a replacement
    * which uses '()' delimiters
    */
   public static String arrayToString(double[] array) {
	   return arrayToString(array,"(",",",")");
   }
   
   public static String arrayToString(double[] array, String open, String separate, String close) {
		int n = array.length;
		StringBuffer buf = new StringBuffer(open);
		for (int i=0; i<n-1; i++) {
			buf.append(Double.toString(array[i])).append(separate);
		}
		if (n>0) buf.append(Double.toString(array[n-1]));
		buf.append(close);
		return buf.toString(); 	
   }

   public static String[][] parseCmdLine(String[] args)  {
   	Vector<String[]>parsedList = new Vector<String[]>();
   	int i=0;
   	while (i<args.length) {
   		// expecting a '-' option
   		if (args[i].charAt(0)!='-') throw new RuntimeException("Option did not start with '-'");
   		if (args[i].length()==2) {
   			// option name and option argument separated
   			if (i+1==args.length || args[i+1].charAt(0)=='-') {
   				// No option argument. (At time of writing this code,
   				// this will always be an error, but handle it for code reuse purposes.)
   				parsedList.add(new String[]{args[i],""});
   				i++;
   			} else {
   				parsedList.add(new String[]{args[i],args[i+1]});
   				i+=2;
   			}
   		} else {
   			// option name and option argument concatenated
   			parsedList.add(new String[]{args[i].substring(0, 2),args[i].substring(2)});
   			i++;
   		}
   	}
   	String[][] returnVal=new String[parsedList.size()][];
   	parsedList.toArray(returnVal);
   	return returnVal;
   }
   
   /**
    * For command line argument args, look for strings matching regexp -[^-].+
    * and split into two strings after 2nd char position. 
    * E.g. '-m3-5' would be converted into '-m' followed by '3-5'.
    * When working with args4j, this allows being agnostic as to whether there is
    * a space or not between flag and argument.
    * @param args
    * @return
    */
   public static String[] spacifyCommandLine(String[] args) {
	   Vector<String> newList = new Vector<String>();
	   for (String nextString : args) {
		   if (nextString.matches("-[^-].+")) {
			   newList.add(nextString.substring(0, 2));
			   newList.add(nextString.substring(2));
		   } else {
			   newList.add(nextString);
		   }
	   }
	   String[] returnList = new String[newList.size()];
	   newList.toArray(returnList);
	   return(returnList);
   }
   
   
   public static String booleanArrayToHexString(boolean[] bit) {
	   int nDigits = (bit.length+3)/4; // note integer arithmetic
	   StringBuffer sb = new StringBuffer(nDigits);
	   for (int i=nDigits; i>=0; i--) {
		   sb.append(HEX_DIGIT[getNybble(bit,i)]);
	   }
	   return sb.toString();
   }
   // Helpers for booleanArrayToHexString:
   private static byte getNybble(boolean[] bit, int index) {
	   int start = Math.min(index*4+3, bit.length-1);
	   int stop  = index*4;
	   int nybble = 0;
	   for (int i= start; i>=stop; i--) {
		   nybble = 2*nybble + ((bit[i]) ? 1 : 0);
	   }
	   return (byte)nybble;
   }
   private static final char[] HEX_DIGIT = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
   

}
