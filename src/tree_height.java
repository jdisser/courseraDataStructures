import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class tree_height {
	/*
	 * Read input from cl or grader with methods that return an int
	 * 
	 * 
	 */
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			
			return tok.nextToken();
		}
		
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
    
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
			/*
			for(Path file : pl) {
				System.out.println(file.getFileName());
			}
			*/
		} catch (IOException x) {
			System.err.println(x);
		} catch (DirectoryIteratorException x) {
			System.err.println(x);
		} finally {
			
		}
		return pl;
	}
	
	/*
	 * Prints a listing of the test files
	 * 
	 */
	public static void printFiles(String dirName, int n, int st, TreeHeight tree) {
		
		List<Path> pl = getFileNames(dirName);
		int[] s = null;
		Path path = null;
		int result = -1;
		
		for(int f = st; f < st + n*2; f = f + 2) {
			
			path = pl.get(f);
			s = getFileContent(path);
			path = pl.get(f + 1);
			result = getResult(path);
			
				System.out.println("Filename: " + path.getFileName());
//				System.out.println(Arrays.toString(s));
				System.out.println(" ");
				tree.growTree(s);
				System.out.println("ComputeHeight: " + tree.computeHeight());
				System.out.println("Result: " + result);
				System.out.println(" ");
			
		}
		
	}
	
	/*
	 * Returns the array of ints contained in the specified file
	 * 
	 */
	public static int[] getFileContent(Path p) {
		
		String len = null;
		String arrs = null;
		//String[] vals = null;
		
		int l = 0;
		
		
		
		Charset cset = Charset.forName("US-ASCII");
		try(BufferedReader br = Files.newBufferedReader(p, cset)){
			len = br.readLine();
			arrs = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		l = Integer.valueOf(len);
//		System.out.println("l: " + l);


		String[] vals = arrs.split(" ");
		
//		System.out.print("vals: ");
//		for(String s : vals)
//			System.out.print(s + " ");
		
//		System.out.println("");
		
		int[] a = new int[vals.length];
		
		int i = 0;
		for(String e : vals) {
			a[i] = Integer.valueOf(e);
			++i;
		}
		
		return a;
	}

	/*
	 * 
	 * Read the correct test result from the file
	 * 
	 */
	public static int getResult(Path p) {
		
		String result = null;
		int l = 0;
				
		Charset cset = Charset.forName("US-ASCII");
		try(BufferedReader br = Files.newBufferedReader(p, cset)){
			result = br.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		l = Integer.valueOf(result);
		
		return l;
	}
	
/*
 * 
 * Build the tree from the input string and calculate it's height
 * 	
 */
	public class TreeHeight {
		int n;
		int parent[];
		int root;
		int maxOrder;
		
		List<Tnode> tree = new ArrayList<Tnode>();
		
		Queue<Integer> queue = new LinkedList<Integer>();
		
		
		class Tnode {

			public int parent = -1;
			public int order;
			public int tkey;
			public List<Integer> children = new ArrayList<Integer>();
			
			public Tnode() {
				
			}
		}
		
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}
		
		void growTree(int[] prnts) {
			
			tree.clear();
			
			int l = prnts.length;
			
			for(int i = 0; i < l; ++i) {
				tree.add(new Tnode());
			}
			
			Tnode tn = null;

			
			for(int j = 0; j < l; ++j) {
				
				tn = tree.get(j);
				tn.tkey = j;
				
				if(prnts[j] == -1) {
					root = j;
					tn.order = 1;
				} else {
					tree.get(prnts[j]).children.add(j);
					tn.order = -1;						//set node order to default for now
					tn.parent = prnts[j];			
				}
			}
			
//			System.out.println("root: " + root);
//			System.out.println("");
			
//			for(Tnode n : tree) {
//				System.out.println("Key: " + n.tkey);
//				System.out.println("Order: " + n.order);
//				System.out.println("Parent: " + n.parent);
//				System.out.println("");
//			}
			
		}

		int computeHeight() {
 		
			/*
			 * naive algorithm code supplied by Coursera, 
			 * 
			for (int vertex = 0; vertex < n; vertex++) {
				int height = 0;
				for (int i = vertex; i != -1; i = parent[i])
					height++;
				maxHeight = Math.max(maxHeight, height);
			}
			*/
			
			maxOrder = 0;
			queue.clear();
			queue.add(root);
			Tnode n = null;
			
			while(!queue.isEmpty()) {
				n = tree.get(queue.peek());
				if(n.tkey != root) {
					n.order = tree.get(n.parent).order + 1;
					if(n.order > maxOrder)
						maxOrder = n.order;
				}
				queue.addAll(n.children);
				queue.remove();
				
			}
			
			return maxOrder;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		tree.growTree(tree.parent);
		System.out.println(tree.computeHeight());
		
//		String testsDir = "tests-tree";
//    	System.out.println("Test Files: ");
//    	System.out.println("");
//    	printFiles(testsDir, 24, 0, tree);
		
	}
}
