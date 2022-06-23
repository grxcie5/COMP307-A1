import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {

    private final String category;
    private final List<Boolean> vals;

    private String predictedClass;

    public Instance(String cat, Scanner s) {
        category = cat;
        vals = new ArrayList<>();
        while (s.hasNextBoolean()) {
            vals.add(s.nextBoolean());
        }
    }

    public boolean getAtt(int index) {
        return vals.get(index);
    }

    public String getCategory() {
        return category;
    }

    public void setPredictedClass(String predictedClass) {
        this.predictedClass = predictedClass;
    }
    public String getPredictedClass(){
        return predictedClass;
    }

    public String toString() {
        StringBuilder ans = new StringBuilder(category);
        ans.append(" ");
        for (Boolean val : vals) {
            ans.append(val ? "true " : "false ");
        }
        return ans.toString();
    }

}

