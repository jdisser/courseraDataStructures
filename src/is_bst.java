import java.util.*;
import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;
        ArrayList<Integer> leaves;

        
        private int find(int ri, int key) {
        	int result = -1;
        	
        	if(tree[ri].key == key)
        		return ri;
        	
        	if(tree[ri].left == -1 && tree[ri].right == -1)		//if a leave is reached and the key not found the tree is bad
        		return result;
        	
        	if(key > tree[ri].key) {
        		if(tree[tree[ri].right].key < tree[ri].key)
        			return result;
        		else
        			result = find(tree[ri].right, key);
        	}
        	
        	if(key < tree[ri].key) {
        		if(tree[tree[ri].left].key > tree[ri].key)
        			return result;
        		else
        			result = find(tree[ri].left, key);
        	}
        	
        	return result;
        }
        
        
        
        
        //TODO: assume tree[0] is root???
        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            leaves = new ArrayList<Integer>();
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
                if(tree[i].left == -1 && tree[i].right == -1)
                	leaves.add(i);
            }
        }

        boolean isBinarySearchTree() {
        	boolean result = true;
        	
          if(leaves.isEmpty())
        	  return result;				//case of empty tree or single node
          
          for(int l : leaves) {
        	  if(find(0,tree[l].key) == -1) {
        		  result = false;
        		  break;
        	  }
          }
         
          return result;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
