import java.io.*;
import java.util.*;

public class KNearestNeighbour {

    public File trainData;
    public File testData;

    public ArrayList<WineInstance> trainingWines = new ArrayList<>(); //list of wines in training set
    public ArrayList<WineInstance> testWines = new ArrayList<>(); //list of wines in test set

    public ArrayList<Double> ranges = new ArrayList<>(); //list of ranges of each feature in order

    public double accurate = 0; //count of how many accurate predictions are made.

    public int k = 3;

    public KNearestNeighbour(File trainData, File testData){
        this.trainData = trainData;
        this.testData = testData;
        run();
    }

    /**
     * Run the program.
     */
    public void run(){
        sortWineInstanceTraining();
        calculateRanges();
        sortWineInstanceTest();
        for(WineInstance w : this.testWines) {
            calculateEuclideanDistance(w);
        }
        calculateAccuracy();
    }

    /**
     * Goes through training-data and creates a wine instance out of each line of data, adds
     * it to the list of training wines. Finds the maximum and minimum values.
     */
    public void sortWineInstanceTraining(){
        try (FileReader fr = new FileReader(this.trainData); BufferedReader br = new BufferedReader(fr)) {
            String first = br.readLine(); //stores the first line - not needed.
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] wineData = currentLine.split(" ");
                WineInstance wineInstance = new WineInstance(wineData);
                trainingWines.add(wineInstance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets range for each feature in order using training wines array and adds them to
     * an array list field for calculations.
     */
    public void calculateRanges(){
        ArrayList<Double> Alcohol = new ArrayList<>();
        ArrayList<Double> Malic_acid = new ArrayList<>();
        ArrayList<Double> Ash = new ArrayList<>();
        ArrayList<Double> Alcalinity_of_ash = new ArrayList<>();
        ArrayList<Double> Magnesium = new ArrayList<>();
        ArrayList<Double> Total_phenols = new ArrayList<>();
        ArrayList<Double> Flavanoids = new ArrayList<>();
        ArrayList<Double> Nonflavanoid_phenols = new ArrayList<>();
        ArrayList<Double> Proanthocyanins = new ArrayList<>();
        ArrayList<Double> Color_intensity = new ArrayList<>();
        ArrayList<Double> Hue = new ArrayList<>();
        ArrayList<Double> Protein_of_diluted_wines = new ArrayList<>();
        ArrayList<Double> Proline = new ArrayList<>();

        for(WineInstance w : this.trainingWines) {
            Alcohol.add(w.getAlcohol());
            Malic_acid.add(w.getMalic_acid());
            Ash.add(w.getAsh());
            Alcalinity_of_ash.add(w.getAlcalinity_of_ash());
            Magnesium.add(w.getMagnesium());
            Total_phenols.add(w.getTotal_phenols());
            Flavanoids.add(w.getFlavanoids());
            Nonflavanoid_phenols.add(w.getNonflavanoid_phenols());
            Proanthocyanins.add(w.getProanthocyanins());
            Color_intensity.add(w.getColor_intensity());
            Hue.add(w.getHue());
            Protein_of_diluted_wines.add(w.getProtein_of_diluted_wines());
            Proline.add(w.getProline());
        }

        ArrayList<ArrayList<Double>> listOfLists = new ArrayList<ArrayList<Double>>();
        listOfLists.add(Alcohol);
        listOfLists.add(Malic_acid);
        listOfLists.add(Ash);
        listOfLists.add(Alcalinity_of_ash);
        listOfLists.add(Magnesium);
        listOfLists.add(Total_phenols);
        listOfLists.add(Flavanoids);
        listOfLists.add(Nonflavanoid_phenols);
        listOfLists.add(Proanthocyanins);
        listOfLists.add(Color_intensity);
        listOfLists.add(Hue);
        listOfLists.add(Protein_of_diluted_wines);
        listOfLists.add(Proline);

        for(ArrayList<Double> ad : listOfLists) {
            double max = 0;
            double min = Double.MAX_VALUE;
            for (Double d : ad) {
                if (d > max) {
                    max = d;
                }
                if (d < min) {
                    min = d;
                }
            }
            double range = max - min;
            this.ranges.add(range);
        }
    }

    /**
     * Goes through test data and creates wine instances out of every line. Adds them to
     * the field list of test wine instances.
     */
    public void sortWineInstanceTest(){
        try (FileReader fr = new FileReader(this.testData); BufferedReader br = new BufferedReader(fr)) {
            String first = br.readLine(); //stores the first line - not needed.
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] wineData = currentLine.split(" ");
                WineInstance wineTestInstance = new WineInstance(wineData);
                testWines.add(wineTestInstance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the Euclidean distance between a wine instance in the test data and calculates the
     * Euclidean distances for all training wine instances while summing them. Square roots the total
     * and sets each training wines euclidean distance.
     * @param wineTest instance of the wine test set currently being predicted.
     */
    public void calculateEuclideanDistance(WineInstance wineTest){
        for(WineInstance w : this.trainingWines){
            double ed = 0; //euclidean distance of wineTest
            for(int i=0; i<w.getData().size(); i++){
                double a = wineTest.getData().get(i);
                double b = w.getData().get(i);
                ed = ed + ((Math.pow((a-b) , 2)) / (Math.pow(this.ranges.get(i) , 2)));
            }
            ed = Math.sqrt(ed);
            w.setEuclideanDistance(ed);
        }
        findKClosest(wineTest);
    }

    /**
     * Firstly, orders the training wine instances by the smallest euclidean distance
     * to the current test wine instance using a priority queue. Polls K many training wines and
     * gets their classes. Predicts the class of the test wine depending on the k many training
     * wines and if the prediction is accurate, increase the accurate count.
     *
     * @param wineTest the current wine instance from the test set.
     */
    public void findKClosest(WineInstance wineTest){
        PriorityQueue<WineInstance> wineQ = new PriorityQueue<>();
        for(WineInstance w : this.trainingWines){
            wineQ.add(w);
        }
        ArrayList<WineInstance> KClosest = new ArrayList<>();
//        int h=0;
//        while(h<this.trainingWines.size()){
//            System.out.println(wineQ.poll().toString());
//            h++;
//        }
        int i=1;
        while(i <= this.k) {
            WineInstance out = wineQ.poll();
            KClosest.add(out);
            i = i + 1;
        }

        if(this.k == 1){
            double predictedClass = KClosest.get(0).getWineClass();
            System.out.println("Predicted class: " + predictedClass + " Actual class: " + wineTest.getWineClass());
            if(predictedClass == wineTest.getWineClass()){
                this.accurate++;
            }
        } else{
            double predictedClassK = getMajority(KClosest);
            System.out.println("Predicted class: " + predictedClassK + " Actual class: " + wineTest.getWineClass());
            if(predictedClassK == wineTest.getWineClass()){
                this.accurate++;
            }
        }
    }

    /**
     * Finds the majority class in the K closest wine instances. If there is no majority, returns 5.
     *
     * @param kClosest list of the K closest neighbours.
     * @return the majority class in K closest wine instances.
     */
    public double getMajority(ArrayList<WineInstance> kClosest) {
        double one = 0;
        double two = 0;
        double three = 0;
        for(WineInstance w : kClosest){
            if(w.getWineClass() == 1){
                one++;
            } else if(w.getWineClass() ==2){
                two++;
            }else{
                three++;
            }
        }
        if(one > two && one > three) {
            return 1;
        }else if(two > one && two > three) {
            return 2;
        }else if(three > one && three > two) {
            return 3;
        }else {
            return 5;
        }
    }

    /**
     * Calculates % of predictions that were correct using the accurate field.
     */
    public void calculateAccuracy(){
        double accuracy = (this.accurate / this.testWines.size()) * 100;
        System.out.println("Accuracy: " + accuracy + "%");
    }

    public static void main(String[] args) {
        File Train = new File(args[0]);
        File Test = new File(args[1]);
        new KNearestNeighbour(Train, Test);
    }

}
