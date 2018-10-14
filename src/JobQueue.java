import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;
    
    
    private Queue<Output> outQ;
    private MinHeap threads;
    
    
    static class Output{
    	public int thread;
    	public long start;
    	
		public Output(int thread, long start) {
//			super();
			this.thread = thread;
			this.start = start;
		}
    	
    	
    }
    
 

    static class Thread {
    	private int index;
    	private long start = 0;
    	
		public Thread(int index, long start) {
//			super();
			this.index = index;
			this.start = start;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public long getStart() {
			return start;
		}
		public void setStart(long start) {
			this.start = this.start + start;
		}
	
    }
    
    static class MinHeap{
    	
    	private List<Thread> minHeap = new ArrayList<Thread>();
    	
    	public MinHeap(int n) {
    		for(int i=0; i<n; ++i) {
    			Thread t = new Thread(i,0);
    			insert(t);
    		}
    	}
    	
    	
    	public int parent(int i) {
    		if(i == 0)
    			return 0;
    		return (i - 1) / 2;
    	}
    	
    	public int leftChild(int i) {
    		return 2 * i + 1;
    	}
    	
    	public int rightChild(int i) {
    		return 2 * i + 2;
    	}
    	
    	//TODO: make sure i & j are within the array!!!
    	public void swap(int i, int j) {
    		
    		Thread ti = minHeap.get(i);
    		Thread tj = minHeap.get(j);
    		
    		long tstart = ti.start;
    		int tindex = ti.index;
    		
    		ti.start = tj.start;
    		ti.index = tj.index;
    		
    		tj.start = tstart;
    		tj.index = tindex;

    	}
    	
    	public boolean minThread(int i, int j) {
    		
    		//thread(i) < thread(j)
    		
    		Thread ti = minHeap.get(i);
    		Thread tj = minHeap.get(j);
    		
    		if(ti.start == tj.start) {
    			if(ti.index < tj.index)
    				return true;
    			else
    				return false;
    		} else if(ti.start < tj.start) {
    			return true;
    		} else
    			return false;   		
    	}
    	
    	public void siftUp(int i) {
    		
    		while(i > 0 && !minThread(parent(i),i)) {
    			swap(parent(i),i);
    			i = parent(i);
    		}
   		
    	}
    	
    	public void siftDown(int i) {
    		
    		int mini = i;
    		int l = leftChild(i);
    		int r = rightChild(i);
    		
    		if(l < minHeap.size() && minThread(l,mini))
    			mini = l;
    		if(r < minHeap.size() && minThread(r,mini))
    			mini = r;
    		
    		if(mini != i) {
    			swap(mini,i);
    			siftDown(mini);
    		}
 
    	}
    	
    	public Thread extractMin() {
    		
    		Thread result = minHeap.get(0);
    		swap(0,minHeap.size() - 1);
    		minHeap.remove(minHeap.size() - 1);
    		siftDown(0);
    		
    		return result;
    		
    	}
    	
    	public void remove(int i) {
    		minHeap.get(i).start = -1;
    		siftUp(i);
    		extractMin();
    	}
    	
    	public void insert(Thread t) {
    		minHeap.add(minHeap.size(),t);
    		siftUp(minHeap.size() - 1);
    	}
    	
    	public Thread getMin() {
    		return minHeap.get(0);
    	}
    	
    	
    }
    
    
    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        
    	Output o;
    	
    	for (int i = 0; i < jobs.length; ++i) {
//            out.print("Naive: " + assignedWorker[i] + " " + startTime[i]);
            o = outQ.remove();
//            out.println("    pQue: " + o.thread + " " + o.start);
            out.println(o.thread + " " + o.start);
        }
    }

    private void assignJobs() {
        
    	//priority Queue
    	outQ = new LinkedList<Output>();
    	threads = new MinHeap(numWorkers);
    	Thread t;
    	/*
    	//naive algorithm
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        */
        
        
        
        //shared loop
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            /*
            //naive
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
            */
            //priorityQue
            t = threads.getMin();
            outQ.add(new Output(t.index,t.start));
            t.setStart(duration);
            threads.siftDown(0);
 
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
