import java.util.*;



import java.io.*;

public class is_bst_hard {
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
            int parent;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
                this.parent = -1;
            }
        }

        int nodes;
        int root;
        Node[] tree;
        ArrayList<Integer> leaves;
        ArrayList<Integer> keys;
        int maxKey = Integer.MIN_VALUE;
        int minKey = Integer.MAX_VALUE;

        
        private void inOrder(int r, ArrayList<Integer> l) {
        	if(r == -1)
        		return;
        	inOrder(tree[r].left, l);
        	l.add(tree[r].key);
        	maxKey = Math.max(maxKey, tree[r].key);
        	minKey = Math.min(minKey, tree[r].key);
        	inOrder(tree[r].right, l);
        }
        
        
        
        
        private int findLeave(int ri, int key) {
        	int result = -1;
        	
        	if(ri == -1)										//null does not hold key
        		return result;
        	
        	if(tree[ri].key == key && tree[ri].left == -1 && tree[ri].right == -1)	//found the leave
        		return ri;
        	
        	if(tree[ri].left == -1 && tree[ri].right == -1)		//if a leave is reached and the key not found the tree is bad
        		return result;
        	
        	if(key >= tree[ri].key) {
        		if(tree[ri].right != -1) {	//if the subtree is null key not found
        			if(tree[tree[ri].right].key < tree[ri].key ||  (tree[ri].left != -1? tree[tree[ri].left].key >= tree[ri].key: false))		
        															//or if the child keys are invalid
            			return result;
            		else
            			result = findLeave(tree[ri].right, key);			//if subtree valid search for the key
        		} else
        			return result;
        		
        	}
        	
        	if(key < tree[ri].key) {
        		if(tree[ri].left != -1) {
        			if(tree[tree[ri].left].key >= tree[ri].key ||  (tree[ri].right != -1? tree[tree[ri].right].key < tree[ri].key: false))
            			return result;
            		else
            			result = findLeave(tree[ri].left, key);
        		}
        		
        	}
        	
        	return result;
        }
        
        
        
        
       
        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            leaves = new ArrayList<Integer>();
            keys = new ArrayList<Integer>();
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
                if(tree[i].left == -1 && tree[i].right == -1 && i != 0)
                	leaves.add(i);
            }
            for(int i = 0; i < nodes; i++) {
            	if(tree[i].left != -1)
            		tree[tree[i].left].parent = i;
            	if(tree[i].right != -1)
            		tree[tree[i].right].parent = i;
            }
            for(int i = 0; i < nodes; i++) {
            	if(tree[i].parent == -1) {
            		root = i;
            	}
            }
        }

        boolean isBinarySearchTree() {
        	boolean result = true;
        	int maxLeft = 0;
        	int minRight = 0;
        	
          if(leaves.isEmpty())
        	  return result;				//case of empty tree or single node
          
          for(int l : leaves) {
        	  if(findLeave(root,tree[l].key) == -1) {
        		  result = false;
        		  break;
        	  }
          }
          /*
          if(result = true) {
        	  maxKey = Integer.MIN_VALUE;
        	  minKey = Integer.MAX_VALUE;
        	  int rootKey = tree[root].key;
        	  
        	  keys.clear();
        	  
        	  if(tree[root].left != -1) {
        		  inOrder(tree[0].left, keys);
            	  maxLeft = maxKey;
        	  }
        	  
        	  keys.clear();
        	  
        	  maxKey = Integer.MIN_VALUE;
        	  minKey = Integer.MAX_VALUE;
        	  if(tree[root].right != -1) {
        		  inOrder(tree[0].right, keys);
            	  minRight = minKey;
        	  }
        	  
        	  if(maxLeft >= rootKey || minRight < rootKey)
        		  result = false;
        	  
          }
          */
//          inOrder(root);
//          System.out.println(keys.toString());
         
          return result;
        }
    }


    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
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
