import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private int[] myData;
    private List<Swap> swaps;
    private List<Swap> mySwaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        myData = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
          myData[i] = data[i];
        }
    }

    private void writeResponse(List<Swap> l, String msg, int[] a) {
    	out.println(msg);
        out.println(l.size());
        for (Swap swap : l) {
          out.println(swap.index1 + " " + swap.index2);
        }
        out.println(Arrays.toString(a));
    }

    private void generateSwaps() {
      swaps = new ArrayList<Swap>();
      mySwaps = new ArrayList<Swap>();
      // The following naive implementation just sorts 
      // the given sequence using selection sort algorithm
      // and saves the resulting sequence of swaps.
      // This turns the given array into a heap, 
      // but in the worst case gives a quadratic number of swaps.
      //
      // TODO: replace by a more efficient implementation
      
      //Naive:
      for (int i = 0; i < data.length; ++i) {
        for (int j = i + 1; j < data.length; ++j) {
          if (data[i] > data[j]) {
            swaps.add(new Swap(i, j));
            int tmp = data[i];
            data[i] = data[j];
            data[j] = tmp;
          }
        }
      }
      
      //Build Binary Min Heap
      int n = myData.length;
      for(int i = n/2 - 1; i >= 0; --i) {
    	  minSiftDown(myData, i);
      }
      
    }

    private void minSiftDown(int[] a, int i) {
    	int mini = i;
    	int li = a.length - 1;	//0 based last index
    	int left = 2*i + 1;
    	int rght = 2*i + 2;
    	if(left <= li && a[left] < a[mini]) {
    		mini = left;
    	}
    	if(rght <= li && a[rght] < a[mini]) {
    		mini = rght;
    	}
    	if(mini != i) {
	   		swap(a, i, mini);
	   		minSiftDown(a, mini);
    	}
   		
    }
    
    //TODO: Check if int will meet problem constraints
    
    private void swap(int[] a, int i, int n) {
    	
    	int t = a[i];
    	a[i] = a[n];
    	a[n] = t;
    	
    	mySwaps.add(new Swap(i,n));
    }
    
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
//        System.out.println();
        writeResponse(swaps, "Naive:", data);
//        System.out.println();
        writeResponse(mySwaps, "BinaryHeap:", myData);
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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
