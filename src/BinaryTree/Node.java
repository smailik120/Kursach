package BinaryTree;

public class Node {
private Node left;
private Node right;
private int value;
public Node(int value) {
	this.left = null;
	this.right = null;
	this.value = value;
}
public Node getLeft() {
	return this.left;
}
public Node getRight() {
	return this.right;
}

public int getValue() {
	return value;
}

public void setLeft(Node left) {
	this.left = left;
}

public void setRight(Node right) {
	this.right = right;
}
}
