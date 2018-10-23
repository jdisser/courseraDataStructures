import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i + m <= n; ++i) {
	    boolean equal = true;
	    for (int j = 0; j < m; ++j) {
			if (s.charAt(j) != t.charAt(i + j)) {
			     equal = false;
	 		    break;
			}
		    }
	            if (equal)
	                occurrences.add(i);
		}
        return occurrences;
    }
    
    private static long getPrime(long tp) {
    	
    	//tp is the text length * pattern length
    	
    	int l2 = 0;				//~log2
    	long temp = tp;
    	while(temp >= 2) {
    		++l2;
    		temp /= 2;
    	}
    	
    	if(l2 < 16)				//32 bit max prime
    		l2 *= 2;
    	else
    		l2 = 32;
    	
    	l2 = Math.max(l2, 31);	//min prime 31
    	
    	Random rnd = new Random(tp);
    	
    	long result = new BigInteger(l2, 16, rnd).longValueExact();
    	
    	return result;
 	
    }
    

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

