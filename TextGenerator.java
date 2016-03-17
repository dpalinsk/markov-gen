/*@dpalinsk and @kwalter2
 * We affirm that we have adhered to the honor code on this assignment.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class TextGenerator {

    public static void main(String[] args) {
	int k = Integer.parseInt(args[0]);
	int m = Integer.parseInt(args[1]);
	String fname = args[2];
	FileReader input = null;
	int nextChar;
	String s = ""; // string containing all the characters of the file.
	try {
	    input = new FileReader(fname);
	} catch (FileNotFoundException e) {
	    System.err.println("Could not open file " + fname + ": "
		    + e.getMessage());
	    System.exit(2);
	}
	try {
	    while (-1 != (nextChar = input.read())) {
		char c = (char) nextChar;
		s = s.concat(String.valueOf(c));
	    }
	} catch (IOException e) {
	    System.err.println("Error reading from file " + fname + ": "
		    + e.getMessage());
	    System.exit(4);
	}
	Markov mv = new Markov(s);
	HashMap<String, Markov> q = mv.SuffixCounter(k, s);

	int p = 0;
	String finalString= s.substring(0, k);
	Markov current = q.get(finalString);
	if (q.size() < 100) {
	    mv.FrequencyCounter(k, s);
	}
	while(p<m) {
	    // System.out.print(q);
	    try {
	    finalString = finalString.concat(String.valueOf(current.random()));
	    } catch (NoSuchElementException e) {
		finalString = finalString.concat(finalString.substring(0, k));
	    }
	    current = q.get(finalString.substring(finalString.length() - k,
		    finalString.length()));
	    // System.out.println(current.suffix);
	    p += 1;

	}
	System.out.printf("k=%d, m=%d, file: %s \n", k, m, fname);
	System.out.println(finalString);
    }
}
