
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tree {

    List<Instance> trainingInstances;
    List<Instance> testInstances;
    List<String> categoryNames = new ArrayList<>();
    List<String> attNames;
    String baselineClass;
    double baselineProbabilty;

    Node first;


    public Tree( List<Instance> trainingInstances, List<Instance> testInstances, Set<String> categoryNames,
                 List<String> attNames){
        this.trainingInstances = trainingInstances;
        this.testInstances = testInstances;
        this.categoryNames.addAll(categoryNames);
        this.attNames = attNames;

        baselineClass = getMostProbable(this.trainingInstances);
        baselineProbabilty = getProbability(this.trainingInstances, baselineClass);
        System.out.println("bl class: " + baselineClass);
        System.out.println("bl prob: " + baselineProbabilty);
        first = buildTree(new ArrayList<>(this.trainingInstances), new ArrayList<>(this.attNames));
        first.report("  ");
        predict();
    }

    /**
     * Calculates the probability of a classes occurrence in the set.
     *
     * @param trainingInstances
     * @param baselineClass
     * @return probability of a classes occurrence in the set.
     */
    private double getProbability(List<Instance> trainingInstances, String baselineClass) {
        double count=0;
        for(int i=0; i<trainingInstances.size(); i++){
            if(trainingInstances.get(i).getCategory().equals(baselineClass)){
                count++;
            }
        }
        return (count/trainingInstances.size());
    }

    /**
     * Gets the most common class in the set.
     *
     * @param trainingInstances
     * @return most common class in the set.
     */
    private String getMostProbable(List<Instance> trainingInstances) {
        double first = 0;
        double second = 0;
        for(int i=0; i<trainingInstances.size(); i++){
            if(trainingInstances.get(i).getCategory().equals(this.categoryNames.get(0))){
                first++;
            } else{
                second++;
            }
        }
        if(first>second){
            return this.categoryNames.get(0);
        } else{
            return this.categoryNames.get(1);
        }
    }

    /**
     * Builds the decision tree.
     *
     * @param instances
     * @param attNames
     * @return Node
     */
    private Node buildTree(List<Instance> instances, List<String> attNames) {
        if(instances.isEmpty()){
            return new LeafNode(this.baselineClass, this.baselineProbabilty);
        } else if(completelyPure(instances)){
            return new LeafNode(instances.get(0).getCategory(),1);
        } else if(attNames.isEmpty()){
            String c = getMostProbable(instances);
            double p = getProbability(instances, c);
            return new LeafNode(c, p);
        } else{
            String bestAtt = "";
            List<Instance> bestInstsTrue = new ArrayList<>();
            List<Instance> bestInstsFalse = new ArrayList<>();
            int attIndex = 0;
            double bestWeightedImpurity = 1;
            for(int i=0; i<attNames.size(); i++){
                List<Instance> trueInst = new ArrayList<>();
                List<Instance> falseInst = new ArrayList<>();
                for (Instance instance : instances) {
                    if (instance.getAtt(i)) {
                        trueInst.add(instance);
                    } else {
                        falseInst.add(instance);
                    }
                }
                double trueWI = computeImpurity(trueInst);
                double falseWI = computeImpurity(falseInst);
                double weightTRUE = trueWI * (trueInst.size() / (double) instances.size());
                double weightFALSE = falseWI * (falseInst.size() / (double) instances.size());
                double weighted = weightTRUE + weightFALSE;
                if(weighted < bestWeightedImpurity){
                    bestWeightedImpurity = weighted;
                    bestAtt = attNames.get(i);
                    bestInstsTrue = trueInst;
                    bestInstsFalse = falseInst;
                    attIndex = i;
                }
            }
            attNames.remove(bestAtt);
            List<String> attributes = new ArrayList<>(attNames);
            Node left = buildTree(bestInstsTrue, attNames);
            Node right = buildTree(bestInstsFalse, attributes);
            Node n = new Node(bestAtt, left, right);
            n.setIndex(attIndex);
            return n;
        }
    }

    /**
     * Calculates the purity of a list of instances.
     *
     * @param TorF
     * @return purity of instances.
     */
    private double computeImpurity(List<Instance> TorF) {
        double m = 0;
        double n = 0;

        for(int i=0; i<TorF.size(); i++){
            if(TorF.get(i).getCategory().equals(this.categoryNames.get(0))){
                m++;
            } else{
                n++;
            }
        }
        return (m * n) / Math.pow((m + n), 2);
    }

    /**
     * Checks if a list of instances are completely pure by checking if they're all one class.
     *
     * @param instances
     * @return true if list of instances are all one class
     */
    private boolean completelyPure(List<Instance> instances) {
        double first = 0;
        double second = 0;
        for (Instance instance : instances) {
            if (instance.getCategory().equals(this.categoryNames.get(0))) {
                first++;
            } else {
                second++;
            }
        }

        return first == 0 || second == 0;
    }

    /**
     * Checks if instances are predicting the accurate class or not then prints out the accuracy.
     */
    private void predict(){
        double count = 0;
        for (Instance instance : testInstances){
            instance.setPredictedClass(getClass(instance, this.first));
            if(instance.getPredictedClass().equals(instance.getCategory())){
                count++;
            }
        }
        System.out.println("correct: " + count);
        double accuracy = (count / trainingInstances.size()) * 100;
        System.out.println("accuracy: " + accuracy);
    }

    /**
     * The class of a node (if it's a leaf node). Iterates through nodes from the root
     * (if the roots index at the list of attributes is false go through the left node
     * else go right node - until a leaf node is reached and a class is predicted).
     *
     * @param i
     * @param root
     * @return predicted class.
     */
    private String getClass(Instance i, Node root){
        if (root instanceof LeafNode){
            return ((LeafNode) root).getClassName();
        } else{
            if (i.getAtt(root.getIndex()) == false){
                return getClass(i, root.getRight());
            }
            else{
                return getClass(i, root.getLeft());
            }
        }

    }
}
