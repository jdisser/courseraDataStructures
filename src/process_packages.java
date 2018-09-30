
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
        this.finish_time = -1;
        this.start_time = -1;
    }

    public int arrival_time;
    public int process_time;
    public int start_time;
    public int finish_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }
    
    public Response() {
    	
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.bsize = size;
        this.queued = new LinkedList<Request>();
        this.empty = true;
        this.full = false;
    }

    public boolean full;
    public boolean empty;
    private int bsize;
    public Deque<Request> queued;
    
    public void add(Request r) {
    	queued.add(r);
    	if(queued.size() == bsize)
    		this.full = true;
    }
    
    public Request remove() {
    	Request rtn = queued.remove();
    	if(queued.size() < bsize)
    		full = false;
    	if(queued.isEmpty())
    		empty = true;
    	
    	return rtn;
    }
    
    
}

class process_packages {
	
	static Queue<Request> requests;
	static Queue<Response>  responses;
	static int strtClk;
	static int busyClk;
	
	static Request switchingReq;
	static Request processingReq;
	static Request droppedReq;
	static Response processingResp;
	static Response printingResp;
	
	
    private static void loadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        requests = new LinkedList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }

    }

    private static void ProcessRequests( Buffer buffer) {
        
    	boolean busy = false;
    	
    	responses = new LinkedList<Response>();
        strtClk = 0;
        busyClk = 0;
//        buffer.full = false;
//        buffer.empty = true;
// 		  for non looping ?
        
        

        
        while(!requests.isEmpty() && !buffer.empty) {
        	
        	//initialize registers
            switchingReq = null;
            processingReq = null;
            droppedReq = null;
        	
            
        	//switch phase handles routing of incoming requests
        	switchingReq = requests.remove();
        	
        	busy = switchingReq.arrival_time < busyClk;
        	
        	if( busy && buffer.full)
        		droppedReq = switchingReq;
        	else if(!busy && buffer.empty)
        		processingReq = switchingReq;
        	else if(busy && !buffer.full) {
        		buffer.add(switchingReq);
        	}
        	
        	//Load request from buffer phase
        	
        	if(processingReq == null && !buffer.empty)
        		processingReq = buffer.remove();
        	
        	//Request Processing phase
        	
        	if(processingReq != null) {
        		if(processingReq.arrival_time > busyClk)
            		strtClk = processingReq.arrival_time;
            	else
            		strtClk = busyClk;
            	
            	processingReq.start_time = strtClk;
            	busyClk = strtClk + processingReq.process_time;
            	processingReq.finish_time = busyClk;
        	}
        	
        	//Response processing phase
        	
        	processingResp = new Response();
        	
        	if(droppedReq != null) {
        		processingResp.dropped = true;
        		processingResp.start_time = -1;
        	}
        	
        	if(processingReq != null) {
        		processingResp.dropped = false;
        		processingResp.start_time = processingReq.start_time;
        	}
        	
        	responses.add(processingResp);
        	
        }
  
    }

    private static void PrintResponses() {
        for (int i = 0; i < responses.size(); ++i) {
            printingResp  = responses.remove();
            if (printingResp.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(printingResp.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        loadQueries(scanner);
        ProcessRequests(buffer);
        PrintResponses();
    }
}
