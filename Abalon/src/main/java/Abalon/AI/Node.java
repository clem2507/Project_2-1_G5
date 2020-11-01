package Abalon.AI;
import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data = null;

    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent = null;
    private Node<T> root=null;
    private Node left,right;

    public Node(T data) {
        this.data = data;
    }
    public Node(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return this.root;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data=data;
    }

    private void setParent(Node<T> parent) {
        this.parent=parent;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void printCousins(Node root, Node node){
        int level= getLevel(root,node,1);
        printGivenLevel(root,node,level);
    }

    static int getLevel(Node root, Node node, int level) {
        if (root == null)
            return 0;
        if (root == node)
            return level;
        // If node is present in left subtree
        int downLevel = getLevel(root.left, node, level+1);
        if (downLevel != 0)
            return downLevel;
        // If node is not present in left subtree
        return getLevel(root.right, node, level+1);
    }

    static void printGivenLevel(Node root, Node node, int level) {
        if (root == null || level < 2)
            return;

        if (level == 2)
        {
            if (root.left == node || root.right == node)
                return;
            if (root.left != null)
                System.out.print(root.left.data + " ");
            if (root.right != null)
                System.out.print(root.right.data + " ");
        }

        else if (level > 2)
        {
            printGivenLevel(root.left, node, level-1);
            printGivenLevel(root.right, node, level-1);
        }
    }

}
