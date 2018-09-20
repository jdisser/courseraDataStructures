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
import java.util.Stack;

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

class check_brackets {
	
	
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
	
	
	public static void printFiles(String dirName, int n, int st) {
		
		List<Path> pl = getFileNames(dirName);
		Charset cset = Charset.forName("US-ASCII");
		String s = null;
		Path path = null;
		
		for(int f = st; f < st+n; ++f) {
			
			path = pl.get(f);
			s = getFileContent(path);
			
				System.out.println("Filename: " + path.getFileName());
				System.out.println(s);
			
		}
		
	}
	
	
    public static void main(String[] args) throws IOException {
    	
    	String testsDir = "tests-check";
    	System.out.println("Test Files: ");
    	System.out.println("");
    	printFiles(testsDir, 2, 84);
    	
    	
    	
    	/*
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here
            }
        }
		*/

    }
}
