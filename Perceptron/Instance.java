import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {

    public final String category;
    public final List<Double> vals;
    public double predicted;
    public double actualClass;

    public Instance(Scanner s) {
        vals = new ArrayList<Double>();
        vals.add(1.0);
        while (s.hasNextDouble()) {
            vals.add(s.nextDouble());
        }
        category = s.next();
        if(category.equals("g")){
            this.actualClass = 1;
        } else{
            this.actualClass = 0;
        }
    }

    public double getAtt(int index) {
        return vals.get(index);
    }

    public String getCategory() {
        return category;
    }

    public List<Double> getVals() {
        return vals;
    }

    public void setPredicted(double predicted) {
        this.predicted = predicted;
    }

    public double getPredicted() {
        return predicted;
    }

    public double getActualClass() {
        return actualClass;
    }

    public String toString() {
        StringBuilder ans = new StringBuilder(category);
        ans.append(" ");
        for (double val : vals) {
            ans.append(val);
        }
        return ans.toString();
    }

}
