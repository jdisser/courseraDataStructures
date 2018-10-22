import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
//    private List<String> elems;
    // for hash function
    
    private HashMap<Integer, LinkedList<String>> buckets;
    
    private int bucketCount;
    private long prime = 1000000007;
    private long multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = ((hash * multiplier + s.charAt(i)) % prime + prime) % prime;		//handle negative(?) numbers
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
         out.flush();
    }

    private void processQuery(Query query) {
    	
    	LinkedList<String> l;
    	String s = null;
    	int hs = -1;
    	
//    	System.out.println("Type: " + query.type);
    	
    	if(!query.type.equals("check")) {
    		s = query.s;
        	hs = hashFunc(s);
        	System.out.println(s + " Hash: " + hs);
        	l = buckets.get(hs);
    	} else {
    		l = buckets.get(query.ind);
    	}
    	
    	
   	
        switch (query.type) {
        
        	
            case "add":
            	if(l != null) {
            		if(!l.contains(query.s))
            			l.addFirst(s);
            	} else {
            		l = new LinkedList<String>();
            		l.addFirst(s);
            		buckets.put(hs, l);
            	}
            	/*
                if (!elems.contains(query.s))
                    elems.add(0, query.s);
                */
                break;
            case "del":
            	if(l != null)
            		l.remove(s);
            	
            	/*
                if (elems.contains(query.s))
                    elems.remove(query.s);
                */
                break;
            case "find":
            	if(l != null)
            		writeSearchResult(l.contains(s));
            	else
            		writeSearchResult(false);
                break;
            case "check":
            	
//            	System.out.println("L: " + l);
            	if(l == null)
            		out.println();
            	else {
            		for (ListIterator<String> it =  l.listIterator(); it.hasNext();) {
            			out.print(it.next() + " ");
            		}
            		out.println();
            	}
            	/*
                for (String cur : elems)
                    if (hashFunc(cur) == query.ind)
                        out.print(cur + " ");
                out.println();
                */
            	
                // Uncomment the following if you want to play with the program interactively.
                 out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
    	
//        elems = new LinkedList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        buckets = new HashMap<Integer, LinkedList<String>>();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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
