import java.util.*;
import java.io.*;

public class tree_orders {
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

	public class TreeOrders {
		int n;
		int[] key, left, right;
		ArrayList<Node> nodes;
		ArrayList<Integer> result;
		Node root;
		
		public TreeOrders() {
			this.nodes = new ArrayList<Node>();
			this.result = new ArrayList<Integer>();
		}
		
		class Node {
			public Node left,right;
			public int key, parent;
			
			public Node(int key) {
				this.key = key;
				this.parent = -1;
				this.left = null;
				this.right = null;
			}
		}
		
		public void constructTree() {
			
			for(int i = 0; i < n; i++) {
				nodes.add(new Node(key[i]));
			}
			
			for(int i = 0; i < n; ++i) {
				int l = left[i];
				int r = right[i];
				Node nt = nodes.get(i);
				Node nl = l != -1? nodes.get(l): null;
				Node nr = r != -1? nodes.get(r): null;
				
//				System.out.println("i: " + i + " l: " + l + " r: " + r);
				
				nt.left = nl;
				nt.right = nr;
				if(nr != null)
					nr.parent = i;
				if(nl != null)
					nl.parent = i;
			}
			
			for(int i = 0; i < n; ++i) {
				if(nodes.get(i).parent == -1) {
					root = nodes.get(i);
					break;
				}
					
			}
			
		}
		
		private void preOrder(Node n) {
			if(n == null)
				return;
			result.add(n.key);
			preOrder(n.left);
			preOrder(n.right);
		}
		
		private void inOrder(Node n) {
			if(n == null)
				return;
			inOrder(n.left);
			result.add(n.key);
			inOrder(n.right);
		}
		
		private void postOrder(Node n) {
			if(n == null)
				return;
			postOrder(n.left);
			postOrder(n.right);
			result.add(n.key);
		}
		
		
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}
		

		List<Integer> inOrderList() {
			result.clear();
			inOrder(root);
			return result;
		}

		List<Integer> preOrderList() {
			result.clear();
			preOrder(root);
			return result;
		}

		List<Integer> postOrderList() {
			result.clear();
			postOrder(root);
			return result;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		tree.constructTree();
		print(tree.inOrderList());
		print(tree.preOrderList());
		print(tree.postOrderList());
	}
}
