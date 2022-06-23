
import java.util.ArrayList;
import java.util.Arrays;

public class WineInstance implements Comparable<WineInstance> {
    private double Alcohol;
    private double Malic_acid;
    private double Ash;
    private double Alcalinity_of_ash;
    private double Magnesium;
    private double Total_phenols;
    private double Flavanoids;
    private double Nonflavanoid_phenols;
    private double Proanthocyanins;
    private double Color_intensity;
    private double Hue;
    private double Protein_of_diluted_wines;
    private double Proline;
    private double WineClass;

    private double euclideanDistance; //euclidean distance between this and the test wine currently predicting.

    private String[] wineData = new String[14]; //instance line from file seperated into each feature value

    public double max = 0;
    public double min = Double.MAX_VALUE;

    public WineInstance(String[] wine){
        for(int i=0; i<13; i++){
            wineData[i] = wine[i];
        }
        Alcohol = Double.parseDouble(wine[0]);
        Malic_acid = Double.parseDouble(wine[1]);
        Ash = Double.parseDouble(wine[2]);
        Alcalinity_of_ash = Double.parseDouble(wine[3]);
        Magnesium = Double.parseDouble(wine[4]);
        Total_phenols = Double.parseDouble(wine[5]);
        Flavanoids = Double.parseDouble(wine[6]);
        Nonflavanoid_phenols = Double.parseDouble(wine[7]);
        Proanthocyanins = Double.parseDouble(wine[8]);
        Color_intensity = Double.parseDouble(wine[9]);
        Hue = Double.parseDouble(wine[10]);
        Protein_of_diluted_wines = Double.parseDouble(wine[11]);
        Proline = Double.parseDouble(wine[12]);
        WineClass = Double.parseDouble(wine[13]);
    }

    /**
     *
     * @return list of each feature in this wine instance
     */
    public ArrayList<Double> getData(){
        ArrayList<Double> line = new ArrayList<>();
        for(int i=0; i<13; i++){
            double value = Double.parseDouble(this.wineData[i]);
            line.add(value);
        }
        return line;
    }

    public void setEuclideanDistance(double euclideanDistance) {
        this.euclideanDistance = euclideanDistance;
    }

    public double getEuclideanDistance(){
        return this.euclideanDistance;
    }

    public double getWineClass(){ return this.WineClass; }

    public double getAlcohol(){return this.Alcohol; }
    public double getMalic_acid(){ return this.Malic_acid; }
    public double getAsh(){ return this.Ash; }
    public double getAlcalinity_of_ash(){ return this.Alcalinity_of_ash; }
    public double getMagnesium(){ return this.Magnesium; }
    public double getTotal_phenols(){ return this.Total_phenols; }
    public double getFlavanoids(){ return this.Flavanoids; }
    public double getNonflavanoid_phenols(){ return this.Nonflavanoid_phenols; }
    public double getProanthocyanins(){ return this.Proanthocyanins; }
    public double getColor_intensity(){ return this.Color_intensity; }
    public double getHue(){ return this.Hue; }
    public double getProtein_of_diluted_wines(){ return this.Protein_of_diluted_wines; }
    public double getProline(){ return this.Proline; }

    public String toString(){
        return  "dist: " + this.euclideanDistance + "\n";
    }

    /**
     * Compares two wine instances. Prioritises the one with the smallest euclidean distance.
     * @param wi
     * @return
     */
    public int compareTo(WineInstance wi){
        if(this.euclideanDistance < wi.getEuclideanDistance()){
            return -1;
        } else if(this.euclideanDistance > wi.getEuclideanDistance()){
            return 1;
        } else{
            return 0;
        }
    }

}
