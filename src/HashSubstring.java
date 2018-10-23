import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
//import java.util.Iterator;
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
    	
    	//Rabin Karp algorithm
    	
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        long[] H = new long[n - m + 1];
        
        long prime = getPrime(n*m);
        long x = (long) (Math.random() * prime);
        
        long phash = polyHash(s,prime,x);
        
        H = precomputeHashes(t,m,prime,x);
        
        for(int i = 0; i < n - m; ++i) {
        	if(H[i] == phash) {
        		if(areEqual(t.substring(i, i + m - 1),s)) {
        			occurrences.add(i);
        		}
        	}
        }
        
        return occurrences;
    }
    
    private static boolean areEqual(String s1, String s2) {
    	if (s1.length() != s2.length())
    		return false;
    	
    	for(int i = 0; i < s1.length(); ++i) {
    		if(s1.charAt(i) != s2.charAt(i))
    			return false;
    	}
    	
    	return true;
    }
    
    
    private static long polyHash(String s, long p, long x) {
    	long h = 0;
    	int l = s.length();
    	for(int i = l - 1; i >= 0; --i) {
    		h = (h * x + s.charAt(i)) % p;
    	}
    	return h; 	
    }
    
    private static long[] precomputeHashes(String t, int m, long p, long x) {
    	//m is length of search pattern
    	//p is a large prime
    	//x is the polynomial factor of the universal hash function
    	
    	int n = t.length();
    	long[] H = new long[n - m + 1];
    	String s = t.substring(n - m);
    	long shash = polyHash(s,p,x);
    	
    	H[H.length - 1] = shash;
    	
    	long y = 1;
    	
    	//precompute x ^ m % p = y
    	
    	for(int j = 1; j <= m; ++j) {
    		y = (y * x) % p;
    	}
    	
    	for (int i = n - m - 1; i >= 0; --i) {
    		H[i] = (p + x * H[i + 1] + t.charAt(i) - t.charAt(i + m) * y ) % p;	//p is added to avoid a negative arg of %
    	}
    	
    	return H;
    	
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

    	Random rnd = new Random(tp);   	
    	long result = new BigInteger(l2, 16, rnd).longValueExact();		//generate prime number of l2 bits   	
    	result = Math.max(result, 31);	//min prime 31
    	
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

