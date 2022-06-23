/**
 * Leaf node is an end node = it's name is the resulting category.
 */
public class LeafNode extends Node{
    String className;
    double probability;

    public LeafNode(String className, double probability){
        super(className, null, null);
        this.className = className;
        this.probability = probability;
    }

    public void report(String indent) {
        if (probability == 0) { //Error-checking
            System.out.printf("%sUnknown%n", indent);
        } else {
            System.out.printf("%sClass %s, prob=%.2f%n", indent, className, probability);
        }
    }

    public double getProbability() {
        return probability;
    }

    public String getClassName() {
        return className;
    }
}
