import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/*
 * @dpalinsk and @kwalter
 * Markov  - ness.
 */
public class Markov {
    String substring;
    int count;
    TreeMap<Character, Integer> suffix = new TreeMap<Character, Integer>();
    boolean end = false;


    public Markov(String substring) {
	this.substring = substring;
	count = 1;
    }

    public void add() {
	count += 1;
    }

    public String toString() {
	String s = substring.toString();
	s = s.concat(":" + " " + suffix.entrySet());
	return s;
    }

    public static void FrequencyCounter(int z, String p) {
	MyHashMap<String, Markov> hm = new MyHashMap<String, Markov>();
	int ind = 0;
	int k = z;
	String input = p;
	while (ind + k <= input.length()) {
	    Markov m = new Markov(input.substring(ind, ind + k));
	    String s = m.substring;
	    if (!hm.containsKey(s)) {
		hm.put(s, m);
		if (ind + k < input.length()) {
		    m.add(input.charAt(ind + k));
		} else {
		    m.end = true;
		}
	    } else {
		Markov n = hm.get(s);
		if (ind + k < input.length()) {
		    n.add(input.charAt(ind + k));
		} else {
		    n.end = true;
		}
		n.count += 1;
	    }
	    ind += 1;
	}
	Iterator<String> itk = hm.keys();
	Iterator<Markov> itv = hm.values();
	System.out.printf("%d distinct keys \n", hm.size());
	while (itv.hasNext()) {
	    Markov temp = itv.next();
	    System.out.printf("%d %s \n", temp.count, temp);
	}

    }

    public static HashMap<String, Markov> SuffixCounter(int k, String input) {
	HashMap<String, Markov> hm = new HashMap<String, Markov>();
	int ind = 0;

	while (ind + k <= input.length()) {
	    Markov m = new Markov(input.substring(ind, ind + k));

	    String s = m.substring;
	    if (!hm.containsKey(s)) {
		hm.put(s, m);
		if (ind + k < input.length()) {
		    m.add(input.charAt(ind + k));
		} else {
		    m.end = true;
		}
	    } else {
		Markov n = hm.get(s);
		if (ind + k < input.length()) {
		    n.add(input.charAt(ind + k));
		} else {
		    n.end = true;
		}
		n.count += 1;
	    }
	    ind += 1;
	}

	return hm;
    }

    public void add(char c) {
	if (!suffix.containsKey(c)) {
	    suffix.put(c, 1);
	} else {
	    int temp = suffix.get(c);
	    temp += 1;
	    suffix.put(c, temp);
	}
    }
    
    public char random() {
	int num = -1;
	if (this.end == false) {
	    num = this.count; // The number of suffixes is always the count
			      // unless the string is the last object, in which
			      // case it will be one less the count.
	} else {
	    num = this.count;
	}

	Random rando = new Random();
	int rand = rando.nextInt(num) + 1;
	Set<Entry<Character, Integer>> suffixes = suffix.entrySet();
	// System.out.print(suffixes);
	Iterator<Entry<Character, Integer>> it = suffixes.iterator();

	Entry<Character, Integer> entry = it.next();
	char current = entry.getKey();
	int range1 = 1;
	int range2 = entry.getValue();
	while (range2 <= num) {
	    if (rand >= range1 && rand <= range2) {
		return current;
	    } else {
		range1 = range2;
		entry = it.next();
		;
		range2 += entry.getValue();
		current = entry.getKey();
	    }
	}
	char c = substring.charAt(0);

	return c;
    }

    public static void main(String[] args) {
	int k = Integer.parseInt(args[0]);
	Scanner scan = new Scanner(System.in);
	String s = scan.next();
	FrequencyCounter(k, s);
	// Dear graders, we combined frequency counter
	// and suffix counter into one thing.

    }

}
