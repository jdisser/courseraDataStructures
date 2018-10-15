import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    Table[] tables;
    int maxRows[];
    
    
    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    
    
    
    class Table {
        int parent;
        int rank;
        int numberOfRows;


        Table(int numberOfRows, int index) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = index;
        }

    }

    int maximumNumberOfRows = -1;

    void make(int rows, int i) {
    	Table t = new Table(rows, i);
    	tables[i] = t;
//    	System.out.println("Created Table: " + i);
    }
    
    int find(int i) {
    	
    	if(tables[i].parent != i) {
    		tables[i].parent = find(tables[i].parent);		//path compression
    	}
    	return tables[i].parent;
    }
    
    void merge(int i, int j) {
        
    	//System.out.println("i: " + i + " j: " + j);
    	
    	int pi = find(i);
    	int pj = find(j);
    	
    	if(pi == pj)
    		return;
    	
    	int ranki = tables[pi].rank;
    	int rankj = tables[pj].rank;
    	
    	if(ranki > rankj) {											// use rank heuristic
    		tables[pj].parent = pi;
    		tables[pi].numberOfRows += tables[pj].numberOfRows;
    		tables[pj].numberOfRows = 0;
    		maximumNumberOfRows = Math.max(maximumNumberOfRows, tables[pi].numberOfRows);
    	} else {
    		tables[pi].parent = pj;
    		tables[pj].numberOfRows += tables[pi].numberOfRows;
    		tables[pi].numberOfRows = 0;
    		maximumNumberOfRows = Math.max(maximumNumberOfRows, tables[pj].numberOfRows);
    	}
    	
    	if(ranki == rankj) {
    		++tables[pj].rank;
    	}
    	
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        tables = new Table[n];
        maxRows = new int[n];
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            make(numberOfRows, i);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        
//        System.out.println("m: " + m);
        
        for (int i = 0; i < m; i++) {
//        	System.out.println("i: " + i);
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(destination, source);
            //writer.printf("%d\n", maximumNumberOfRows);
            maxRows[i] = maximumNumberOfRows;
//            System.out.println("maxRows[" + i + "]: " + maxRows[i]);
        }
        
        for (int i = 0; i < m; i++)
        	System.out.println(maxRows[i]);
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
