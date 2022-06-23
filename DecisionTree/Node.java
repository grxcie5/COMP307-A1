import java.util.ArrayList;

public class Node {
    String attName;
    Node left;
    Node right;
    int index;

    public Node(String attName, Node left, Node right) {
        this.attName = attName;
        this.left = left;
        this.right = right;
    }

    public void report(String indent){
        System.out.printf("%s%s = True:%n", indent, attName);
        left.report(indent+"\t");
        System.out.printf("%s%s = False:%n", indent, attName);
        right.report(indent+"\t");
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getAttName() {
        return attName;
    }

}
