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
            long key;
            int left;
            int right;
            int parent;

            Node(long key, int left, int right) {
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
        ArrayList<Long> keys;
        long maxKey = Integer.MAX_VALUE;
        long minKey = Integer.MIN_VALUE;
       
        private boolean isBST(int ri, long min, long max) {
        	if(ri == -1)
        		return true;
        	
        	if(tree[ri].key < min || tree[ri].key > max)
        		return false;
        	
        	return (isBST(tree[ri].left, min, tree[ri].key - 1) && isBST(tree[ri].right, tree[ri].key, max));
        }
      
       
        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            leaves = new ArrayList<Integer>();
            keys = new ArrayList<Long>();
            for (int i = 0; i < nodes; i++) {									//build tree
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
                if(tree[i].left == -1 && tree[i].right == -1 && i != 0)
                	leaves.add(i);
            }
            for(int i = 0; i < nodes; i++) {									//find root
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
        	
          if(leaves.isEmpty())
        	  return true;				//case of empty tree or single node
          
          return isBST(root, minKey, maxKey);
          
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
