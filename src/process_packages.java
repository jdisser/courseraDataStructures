
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
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
    
    public Request() {
    	
    }
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
    
    public void clear() {
    	queued.clear();
    	empty = true;
    	full = false;
    }
    
    
}

class process_packages {
	
	static Queue<Request> requests;
	static Queue<Response>  responses;
	static Queue<Integer> starts;
	static Queue<Request> processed;
	static int strtClk;
	static int busyClk;
	
	static int bufSize;
	static int n = 0;
	
	static Buffer buffer;
	
	static Request switchingReq;
	static Request processingReq;
	static Request droppedReq;
	static Response processingResp;
	static Response printingResp;
	
	/*
	* Returns a List of paths to test files in the specified directory
	* 
	*/	
	public static List<Path> getFileNames(String dirName){
		Path p = Paths.get(dirName);
		List<Path> pl = new ArrayList<Path>();
		
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(p)) {
			for(Path file : ds) {
				pl.add(file);				
			}
			pl.sort(null);

//			for(Path file : pl) {
//				System.out.println(file.getFileName());
//			}

		} catch (IOException x) {
			System.err.println(x);
		} catch (DirectoryIteratorException x) {
			System.err.println(x);
		} finally {
			
		}
		return pl;
	}
	
	/*
	 * reads the specified test file and sets n & bufSize and loads requests
	 * 
	 */
	public static void getFileContent(Path p) {
		
		String lens = null;


		Charset cset = Charset.forName("US-ASCII");
		try(BufferedReader br = Files.newBufferedReader(p, cset)){
			lens = br.readLine();
//			System.out.println("In getFileContent: \n");
			System.out.println("Filename: " + p.getFileName().toString());
			System.out.println("Parameter line: " + lens);
			String[] vals = lens.split(" ");
			n = Integer.valueOf(vals[1]);
			bufSize = Integer.valueOf(vals[0]);
			
			for(int i = 0; i < n; ++i) {
				lens = br.readLine();
				vals = lens.split(" ");
				System.out.println("Read Line: " + lens);
				Request r = new Request();
				r.arrival_time = Integer.valueOf(vals[0]);
				r.process_time = Integer.valueOf(vals[1]);
				
				requests.add(r);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 * Read the test results from the file and set starts
	 * NOTE: must run getFileContent first to set n
	 * 
	 */
	public static void getResult(Path p) {
		
		String s = null;
		
		starts = new LinkedList<Integer>();
		
		
		Charset cset = Charset.forName("US-ASCII");
		try(BufferedReader br = Files.newBufferedReader(p, cset)){
			
			for(int i = 0; i < n ; ++i) {
				s = br.readLine();
				starts.add(Integer.valueOf(s));
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	/*
	 * Prints a listing of the test files
	 * Note: this is a destructive process that will empty the queues!!!
	 * 
	 */
	public static void printFiles(List<Path> pl, int n, int st) {
				
		Path path = null;
		int result = -1;
		requests = new LinkedList<Request>();
		responses = new LinkedList<Response>();
		processed = new LinkedList<Request>();
		
		
		
		for(int f = st; f < st + n*2; f = f + 2) {
			
			path = pl.get(f);
			getFileContent(path);
			path = pl.get(f + 1);
			getResult(path);
			if(!requests.isEmpty()) {
				buffer = new Buffer(bufSize);
				System.out.println("Calling ProcessRequests...");
				ProcessRequests();
			}

			
			
//				System.out.println("Filename: " + path.getFileName());
				System.out.println("# of Responses: " + responses.size());
				System.out.println("Requests: ");
				if(!processed.isEmpty()) {
					for (Request r : processed) {
						System.out.print("A: "+ r.arrival_time + " P: " + r.process_time + " St: " + starts.remove().intValue());
						if(!responses.isEmpty()) {
							System.out.println(" Resp: " + responses.remove().start_time);
						} else {
							System.out.println(" Resp: " + "none");
						}
					}
				} else {
					System.out.println("No requests");
				}

				requests.clear();
				responses.clear();
				System.out.println(" ");
				
		}
		
	}
	
	private static void loadFiles(String fileDir) throws IOException {
    	List<Path> paths = getFileNames(fileDir);
    	printFiles(paths, 5, 0);
    }
	
	
    private static void loadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        requests = new LinkedList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }

    }
    
    

    private static void ProcessRequests() {
        
    	boolean busy = false;
    	
//    	responses = new LinkedList<Response>();
        strtClk = 0;
        busyClk = 0;
        
        buffer.clear();
        
        

        
        while(!requests.isEmpty() || !buffer.empty) {
        	
        	System.out.println("Processing request...");
        	
        	//initialize registers
            switchingReq = null;
            processingReq = null;
            droppedReq = null;
            processingResp = null;
        	
            
        	//switch phase handles routing of incoming requests
        	switchingReq = requests.remove();
        	processed.add(switchingReq);				//make a copy of the queue for printing
        	
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
        	System.out.println("Added response");
        	
        }
  
    }

    private static void PrintResponses() {
        while (!responses.isEmpty()) {
            printingResp  = responses.remove();
            if (printingResp.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(printingResp.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        int buffer_max_size = scanner.nextInt();
//        Buffer buffer = new Buffer(buffer_max_size);

//        loadQueries(scanner);
//        ProcessRequests(buffer);
//        PrintResponses();
        
        printFiles(getFileNames("tests-pkg"), 18, 0);
//        scanner.close();
    }
}
