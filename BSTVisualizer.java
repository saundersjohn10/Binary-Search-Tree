

//originally created and run in Eclipse

//author: John Saunders, referenced for printing tree: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BSTVisualizer {
	
	//define components
	private JFrame frame;
	
	private JButton set; private JTextField setRootTxt;
	private JButton add; private JTextField addNodeTxt;
	private JButton remove; private JTextField remTxt;
	
	private JButton preO; private JButton inO; private JButton postO; 
	private JTextField ordertxt;
	
	public JLabel treeLbl; //label where tree text goes
	
	public BinarySTree tree = new BinarySTree(); //binary Tree object used
	
	public BSTVisualizer() {
		initialize();
	}
	
	//starts the gui
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BSTVisualizer theApp = new BSTVisualizer(); 
			}
		});
	}
	
	private void initialize() {
		//set up basics
		frame = new JFrame();
		frame.setTitle("Binary Search Tree Visualizer");
		frame.setSize(525,450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		//add components to frame and create settings
		setRootTxt = new JTextField(3); frame.add(setRootTxt);
		set = new JButton("New Root"); frame.add(set);
		
		addNodeTxt = new JTextField(3); frame.add(addNodeTxt); 
		add = new JButton("Add Node"); frame.add(add);
		
		remTxt = new JTextField(3); frame.add(remTxt);
		remove = new JButton("Remove Node"); frame.add(remove);
		
		//add text to buttons and set their sizes
		preO = new JButton("PreOrder Traversal"); inO = new JButton("InOrder Traversal"); postO = new JButton("PostOrder Traversal");
		frame.add(preO); frame.add(inO); frame.add(postO); 
		ordertxt = new JTextField(40); ordertxt.setVisible(false); ordertxt.setEditable(false);
		frame.add(ordertxt); 
		
		//error labels
		JLabel enterNumLabel = new JLabel("Enter a number"); enterNumLabel.setForeground(Color.RED); enterNumLabel.setVisible(false);
		frame.add(enterNumLabel);
		
		treeLbl = new JLabel();
		frame.add(treeLbl);
		
		//if preorder button triggered, root, left, right
		preO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {  //thread showing the traversal
				Thread t = new Thread() {
					public void run() {
						setButtons(false);
						ordertxt.setVisible(true);
						
						
	    					tree.showPreOrder(); //method showing traversal
	    					
	    					setButtons(true);
					}
				};
				t.start();
			}
		});
		
		//if preorder button triggered, left, root, right
		inO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //thread showing the traversal
				Thread t = new Thread() {
					public void run() {
						
						setButtons(false);
						ordertxt.setVisible(true);
	    					
	    					tree.showInOrder();
	    					
	    					setButtons(true);
					}
				};
				t.start();
			}
		});
		
		//if preorder button triggered, left, right, root
		postO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {  //thread showing the traversal
				
				Thread t = new Thread() {
					public void run() {
						setButtons(false);
						
						
						ordertxt.setVisible(true);
	    					
	    					tree.showPostOrder();
	    					
	    					setButtons(true);
					}
				};
				t.start();
			}
		});
		
		//if remove button triggered
		remove.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {
	        		try { //in case the user enters a string
	        			int data = Integer.parseInt(remTxt.getText());
		        		enterNumLabel.setVisible(false);
	        			Thread t = new Thread() { //thread that visualizes 
	        				public void run() {
	        					
	        					
	        					setButtons(false);
	        					
	        					tree.deleteKey(data);
	        					treeLbl.setText(tree.printTree()); //shows the finished tree
	        					
	        					setButtons(true);
	        				}
	        			};
	        			t.start();
	        		}
	        		catch(java.lang.NumberFormatException e) {
	        			enterNumLabel.setVisible(true);
	        		}
	        		
	        	}
		});
		
		//if set root button triggered
		set.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {
		        	try { //in case the user enters a string
	        			int root = Integer.parseInt(setRootTxt.getText());
	        			enterNumLabel.setVisible(false);
	        			tree.setRoot(root); 
	        			treeLbl.setText(tree.printTree());
	        		}
	        		catch(java.lang.NumberFormatException e) {
	        			enterNumLabel.setVisible(true);
	        		}
	        	}
		});
		
		
		//if new Node button triggered
		add.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {
	        		try { //in case the user enters a string
	        			int data = Integer.parseInt(addNodeTxt.getText());
	        			enterNumLabel.setVisible(false);
	        			Thread t = new Thread() { //thread that visualizes 
	        				public void run() {
	        					setButtons(false);
	        					
	        					tree.add(data); //show the adding
	        					treeLbl.setText(tree.printTree()); //print final tree
	        					
	        					setButtons(true);
	        				}
	        			};
	        			t.start(); 
	        		}
	        		catch(java.lang.NumberFormatException e) {
	        			enterNumLabel.setVisible(true);
	        		}
	        	}
		});
		frame.setVisible(true); 
	}
	
	public void setButtons(Boolean setBut) { //enables and disables buttons as needed
		add.setEnabled(setBut); set.setEnabled(setBut); remove.setEnabled(setBut);
		preO.setEnabled(setBut); inO.setEnabled(setBut); postO.setEnabled(setBut);
	}
	
	//Binary Tree code
	class BinarySTree{
		
		//Node class
		class Node {
		    int data;
		    Node left, right; //left and right default to null
		 
		    public Node(int item) {
		        data = item;
		    }
		}
		
		public Node root;
		private int valuetoAdd;
		private LinkedHashMap<Integer, Node> nodesCycled = new LinkedHashMap<>(); //HashTable searched through
		private StringBuffer treePrint = new StringBuffer();
		private StringBuffer orderNodes = new StringBuffer();
		
		public BinarySTree() {} //constructor
		
		/* ----------------Traversals----------------*/
		//preOrder Recursive
		public void preOrder(Node n) {
			if(n != null) { //root, left, right
				nodesCycled.put(hashFunction(n), n); 
				preOrder(n.left);
				preOrder(n.right);
			}
		}
		
		public void showPreOrder() {
			orderNodes.delete(0, orderNodes.length());
			
			preOrder(root); 
			for(int key : nodesCycled.keySet()) { //cycle through the nodes in the order they were added
				treeLbl.setText(printTravTree(nodesCycled.get(key)));
				orderNodes.append(nodesCycled.get(key).data + ", ");
				ordertxt.setText(orderNodes.toString());
				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();} 
			}
			nodesCycled.clear(); //reset hash table
			treeLbl.setText(printTree());
		}
		
		//inOrder Recursive
		public void inOrder(Node n) {
			if(n != null) { 
				inOrder(n.left);
				nodesCycled.put(hashFunction(n), n);
				inOrder(n.right);
			}
		}
		
		public void showInOrder() {
			orderNodes.delete(0, orderNodes.length());
			
			inOrder(root);
			for(int key : nodesCycled.keySet()) { //cycle through the nodes in the order they were added
				treeLbl.setText(printTravTree(nodesCycled.get(key)));
				orderNodes.append(nodesCycled.get(key).data + ", ");
				ordertxt.setText(orderNodes.toString());
				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
			}
			nodesCycled.clear();//reset hash table
			treeLbl.setText(printTree());
		}
		
		//inOrder Recursive
		public void postOrder(Node n) {
			if(n != null) { 
				postOrder(n.left);
				postOrder(n.right);
				nodesCycled.put(hashFunction(n), n);
			}
		}
				
		public void showPostOrder() {
			orderNodes.delete(0, orderNodes.length());
			postOrder(root);
			for(int key : nodesCycled.keySet()) { //cycle through the nodes in the order they were added
				treeLbl.setText(printTravTree(nodesCycled.get(key)));
				orderNodes.append(nodesCycled.get(key).data + ", ");
				ordertxt.setText(orderNodes.toString());
				try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
			}
			nodesCycled.clear();//reset hash table
			treeLbl.setText(printTree());
		}
		
		//print tree during adding visualization
		private String helpTrav(Node root, int level, Node n ){
			
			if(root==null) return ""; //base case
		         
			helpTrav(root.right, level+1, n);
		    if(level!=0){
		        for(int i=0;i<level-1;i++)
		        		treePrint.append("|       ");
		        		if(n == root) {
		        			treePrint.append("|-------("+ root.data + ")*<br>"); //adds information showing searching through this node
		        		}
		        		else
		        			treePrint.append("|-------("+ root.data + ")<br>");
		    }
		    else
		    		if(n == root) 
		    			treePrint.append("(" + root.data + ")*<br>");
		    		else
		    			treePrint.append("(" + root.data + ")<br>");
		        
		    helpTrav(root.left, level+1, n);
		    return treePrint.toString();
		}
		
		public String printTravTree(Node n) { //given node wanted to star, returns String of tree with it stared
			String text = helpTrav(root, 0, n);
			treePrint.delete(0, treePrint.length());
			return "<html><pre class=\"tab\">" + text + "</pre></html>";
		}
		
		/*--------------Adding Node------------------*/
		Node newParentNode = null; //keep track of the node needed to add
		private void addRecursive(Node current, int value) { //finds the place where a new node will be added
			
			if (current == null) { //base case
		        return;
		    }
			nodesCycled.put(hashFunction(current), current); //add to hash table, doesn't allow duplicate nodes
		    if (value < current.data) { //goes down left side
		    	
		    		if(current.left == null)
		    			newParentNode = current;
		    			
		        addRecursive(current.left, value);
		    } else if (value >= current.data) { //goes down right side
		    		if(current.right == null)
		    			newParentNode = current;
		        addRecursive(current.right, value);
		    } 
		    return;
		}
		
		//utilizes Recursive function to add new Node from a value
		public void add(int value) {
			this.valuetoAdd = value;
			
			//if this is the first node added
			if(root.left == null && value < root.data) {
				root.left = new Node(value);
				return;
			}
			else if(root.right == null && value > root.data) {
				root.right = new Node(value);
				return;
			}
			
			addRecursive(root, value); //retrieves 2 things: new parent node, nodes that were recursed over
			//visualize the adding of the new node
		    for(int n : nodesCycled.keySet()) {
		    		treeLbl.setText(tree.printTempTree(nodesCycled.get(n)));
		    		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		    }
		    nodesCycled.clear();
		    	
		    //adds a new Node, gets parent before when visualizing
		    if(value < newParentNode.data)
		    		newParentNode.left = new Node(value);
		    else
		    		newParentNode.right = new Node(value);
		}

		//print tree during adding visualization
		public String printTree(Node root, int level, Node n ){
			
			if(root==null) //base case
		         return "";
			
		    printTree(root.right, level+1, n);
		    if(level!=0){
		        for(int i=0;i<level-1;i++)
		        		treePrint.append("|       ");
		        		
		        		if(n == root) {
		        			treePrint.append("|-------("+ root.data + ")(" + valuetoAdd + ")<br>"); //adds information showing searching through this node
		        		}
		        		else
		        			treePrint.append("|-------("+ root.data + ")<br>");
		    }
		    else {
		    		if(n == root) 
		    			treePrint.append("(" + root.data + ")(" + valuetoAdd + ")<br>");
		    		else
		    			treePrint.append("(" + root.data + ")<br>");
		    }
		    printTree(root.left, level+1, n);
		    return treePrint.toString();
		}
		
		public String printTempTree(Node n) {
			String text = printTree(root, 0, n);
			treePrint.delete(0, treePrint.length());
			return "<html><pre class=\"tab\">" + text + "</pre></html>";
		}
		
		/*-----------Delete a node---------------------*/
		private Node nodeToDelete; //keep track of the node needed to delete
		
		private void findNodesDel(Node current, int value) {
			if(current != null && current.data == value) //if found the node to delete
				nodeToDelete = current;
			
			if(current == null) return;
			
			nodesCycled.put(hashFunction(current), current); //add to hash table
			
			if(value < current.data) 
				findNodesDel(current.left, value);
			else 
				findNodesDel(current.right, value);
		}
		
	    private Node deleteRec(Node root, int key) {
	    		
	        if (root == null) return root; //base case
	        
	        //left, right
	        if (key < root.data)
	            root.left = deleteRec(root.left, key);
	        else if (key > root.data)
	            root.right = deleteRec(root.right, key);
	 
	        else {
	        		//if found the node to delete
	            if (root.left == null)
	                return root.right;
	            else if (root.right == null)
	                return root.left;
	 
	            root.data = minValue(root.right);
	 
	            root.right = deleteRec(root.right, root.data);
	        }
	 
	        return root;
	    }
	    
	    //finds the node to replace the deleted one
	    private int minValue(Node root) {
	        int minv = root.data;
	        
	        while (root.left != null) {
	            minv = root.left.data;
	            root = root.left;
	        }
	        return minv;
	    }
	    
	    //call this to delete Node
		public void deleteKey(int key) {
	        
			findNodesDel(root, key); //finds nodes that will be recursed through
			
			for(int nodeKey: nodesCycled.keySet()) { //cycle through nodes that were searched through
				treeLbl.setText(printRemoveTree(nodesCycled.get(nodeKey)));
				try {Thread.sleep(750);} catch (InterruptedException e) {e.printStackTrace();}
			}
			nodesCycled.clear();
			deleteRec(root, key); //actually deletes the nodes
	        
	    }
		//print tree during adding visualization
		private String helpRemove(Node root, int level, Node n ){
			
			if(root==null) //base case
		         return "";
			
			helpRemove(root.right, level+1, n);
		    if(level!=0){
		        for(int i=0;i<level-1;i++)
		        		treePrint.append("|       ");
		        		if(n == root) {
	        				if(n == nodeToDelete) { //if found node to delete
	        					treePrint.append("|-------("+ root.data + ")found!<br>");
	        					try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();} //extra sleep time when the match is found
	        				}
	        				else treePrint.append("|-------("+ root.data + ")*<br>");
		        		}
		        		else treePrint.append("|-------("+ root.data + ")<br>");
		    }
		    else treePrint.append("(" + root.data + ")<br>");
		        
		    helpRemove(root.left, level+1, n);
		    return treePrint.toString();
		}
		
		public String printRemoveTree(Node n) {
			String text = helpRemove(root, 0, n);
			treePrint.delete(0, treePrint.length());
			return "<html><pre class=\"tab\">" + text + "</pre></html>";
		}
	    
		public void setRoot(int value) {//set the root
			root = new Node(value);
		}
		
		//general printing of tree
		public String printTree(Node root, int level){
			if(root==null)
		         return "";
		    printTree(root.right, level+1);
		    if(level!=0){
		        for(int i=0;i<level-1;i++)
		        		treePrint.append("|       ");
		        treePrint.append("|-------("+ root.data + ")<br>");
		    }
		    else
		    		treePrint.append("(" + root.data + ")<br>");
		        
		    printTree(root.left, level+1);
		    return treePrint.toString();
		}
		
		public String printTree() {
			String text = printTree(root, 0);
			treePrint.delete(0, treePrint.length());
			return "<html><pre class=\"tab\">" + text + "</pre></html>";
		}
		
		private int hashFunction(Node n) { //hash function for creating the hash table
			return (((n.data * 3) + n.hashCode()) / 169);
		}	
	}
}
