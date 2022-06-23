import java.util.*;
public class Perceptron {

    //get weights for all features list f1 f2 f3 f4 f5 .....
    public List<Instance> allInstances;
    public List<Double> weights;
    double g = 1;
    double b = 0;

    double bias = 1.1;

    double correct = 0;

    public Perceptron(List<Instance> allInstances){
        this.allInstances = allInstances;
        this.weights = new ArrayList<>();
        this.weights.add(1.1);
        for(int i=0; i<34; i++){
            this.weights.add(0.2);
        }
    }

    public void doPerceptron(List<Instance> instances){
        double count = 0;
        additiveJunction();
        while( (calculateAccuracy() != 100)) {
            changeWeight();
           additiveJunction();

           if(count > 2){
               break;
           }
           count++;
        }
        System.out.println(this.weights);
        System.out.println("Accuracy: " + calculateAccuracy());
    }

    /**
     * Compares the predicted and actual classes of each instance and calculates the accuracy of the
     * predictions.
     *
     * @return accuracy of predictions in perceptron.
     */
    private double calculateAccuracy() {
        double c=0;
        for(Instance in : this.allInstances){
            if(in.getActualClass() == (in.getPredicted())){
                c++;
            }
        }
        double accuracy = ((c/this.allInstances.size()) * 100);
        return accuracy;
    }

    /**
     * Changes the weight and bias accordingly by the difference between the predicted and
     * actual classes.
     */
    private void changeWeight(){
        for(Instance in : this.allInstances) {
            this.bias = this.bias + ((in.getActualClass() - in.getPredicted()) * 1);
            for (int j = 0; j < in.getVals().size(); j++) {
                double weight = this.weights.get(j) + ((in.getActualClass() - in.getPredicted()) * in.getVals().get(j));
                this.weights.set(j, weight);
            }
        }
    }

    /**
     *Calculates the additive junction of each feature. Sums all the values multiplied by their weights.
     * Calls to classify them and sets their predicted classes.
     */
    private void additiveJunction(){
        //calculate wiji sum then get the classifier then get the accuracy.
        for(Instance i1 : this.allInstances) {
            double feature = 0;
            for (int i = 0; i < i1.getVals().size(); i++) {
                double xi = i1.getVals().get(i);
                double wi = this.weights.get(i);
                feature = feature + (xi * wi);
            }
            feature = feature + this.bias;
            classify(feature, i1);
        }
    }

    /**
     * Uses the threshold activation function to predict the instances class.
     *
     * @param feature feature.
     * @param instance instance.
     */
    private void classify(double feature, Instance instance) {
        if(feature > 0){
            instance.setPredicted(1);
        } else {
            instance.setPredicted(0);
        }


    }
}
