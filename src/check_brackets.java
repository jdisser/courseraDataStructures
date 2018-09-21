import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class BracketStack {
	
	private class Node {
		Bracket val;
		Node next;
		
		public Node(Bracket val) {
			this.val = val;
			this.next = null;
		}
	}
	
	private Node stack = null;
	private int elements = 0;
	
	public void push(Bracket e) {
		if (elements == 0) {
			stack = new Node(e);
			++elements;
		} else {
			Node cur = new Node(e);
			cur.next = stack;
			stack = cur;
			++elements;
		}
	}
	
	public Bracket pop() {
		if(elements != 0) {
			Node e = stack;
			stack = stack.next;
			--elements;
			return e.val;
		} else
			return null;	
	}
	
	public boolean isEmpty() {
		if(elements == 0)
			return true;
		else
			return false;
	}
	
	public void clearStack() {
		stack = null;
		elements = 0;
		return;
	}
	
}

class check_brackets {
	
	/*
	 * 
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
			for(Path file : pl) {
				System.out.println(file.getFileName());
			}

		} catch (IOException x) {
			System.err.println(x);
		} catch (DirectoryIteratorException x) {
			System.err.println(x);
		} finally {
			
		}
		return pl;
	}
	
	/*
	 * Returns the string contained in the specified file
	 * 
	 */
	public static String getFileContent(Path p) {
		
		String s = null;
		
		Charset cset = Charset.forName("US-ASCII");
		try(BufferedReader br = Files.newBufferedReader(p, cset)){
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	/*
	 * Prints a listing of the test files
	 * 
	 */
	public static void printFiles(String dirName, int n, int st) {
		
		List<Path> pl = getFileNames(dirName);
		String s = null;
		Path path = null;
		
		for(int f = st; f < st+n; ++f) {
			
			path = pl.get(f);
			s = getFileContent(path);
			
				System.out.println("Filename: " + path.getFileName());
				System.out.println(s);
			
		}
		
	}
	
	public String getInput() throws IOException {
		InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();
        return text;
	}
	
	public void runTests(String dirName, int test, int n ) {
		List<Path> paths = getFileNames(dirName);
		
		String testS = null;
		String result = null;
		Path path = null;
		
		for(int f = test; f < test+n; ++f) {
			
			path = paths.get(f*2);
			testS = getFileContent(path);
			path = paths.get(f*2 + 1);
			result = getFileContent(path);
			
			

	        BracketStack opening_brackets_stack = new BracketStack();
	        
	        for (int position = 0; position < testS.length(); ++position) {
	            char next = testS.charAt(position);

	            if (next == '(' || next == '[' || next == '{') {
	                // Process opening bracket, write your code here
	            }

	            if (next == ')' || next == ']' || next == '}') {
	                // Process closing bracket, write your code here
	            }
	        }
			
			
				System.out.println("Filename: " + path.getFileName());
				System.out.println(testS);
			
		}
	}
	
	
    public static void main(String[] args){
    	
    	String testsDir = "tests-check";
    	System.out.println("Test Files: ");
    	System.out.println("");
    	printFiles(testsDir, 2, 84);
    	
    	
    	
    	

    }
}
