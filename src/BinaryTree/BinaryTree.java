package BinaryTree;

public class BinaryTree {
private Node node;

public BinaryTree() {
	this.node = null;
}

 public void insert(int value) {
	 if(node == null) {
		 node = new Node(value);
	 }
	 else {
		 Node kem = node;
		 while(node != null) {
			 if(kem.getValue() >= value) {
				 if(kem.getLeft() != null) {
					 System.out.println(kem.getValue());
					 kem = kem.getLeft();
				 }
				 else {
					 Node temp = new Node(value);
					 kem.setLeft(temp);
					 break;
				 }
			 }
			 else {
				 if(kem.getRight() != null) {
					 System.out.println(kem.getValue());
					 kem = kem.getRight();
				 }
				 else {
					 Node temp = new Node(value);
					 kem.setRight(temp);
					 break;
				 }
			 }
		 }
	 }
 }
 
 public void printTree(Node node) {
	 if(node.getLeft() != null) {
		 printTree(node.getLeft());
	 }
	 if(node.getRight() != null) {
		 printTree(node.getRight());
	 }
 }
 
 public Node getHead() {
	 return node;
 }
}
