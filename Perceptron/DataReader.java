import java.io.File;
import java.io.IOException;
import java.util.*;

class DataReader {
    // some bits of java code that you may use if you wish.
    int numAtts;
    List<String> attNames;
    List<Instance> allInstances;

    public DataReader(String fname){
        readDataFile(fname);
    }

    private void readDataFile(String fname) {
        /* format of names file:
         * names of attributes (the first one should be the class/category)
         * category followed by true's and false's for each instance
         */
//        System.out.println("Reading data from file " + fname);
        try {
            Scanner din = new Scanner(new File(fname));

            attNames = new ArrayList<>();
            Scanner s = new Scanner(din.nextLine());

            while (s.hasNext()) {
                attNames.add(s.next());
            }

            numAtts = attNames.size();
//            System.out.println(numAtts + " attributes");

            allInstances = readInstances(din);
            din.close();

//            for(Instance i : allInstances){
//                System.out.println("class: " + i.getCategory());
//                System.out.println("vals size: " + i.getVals().size());
//            }

        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    public List<Instance> getAllInstances() {
        return allInstances;
    }

    private List<Instance> readInstances(Scanner din) {
        /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<>();
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(line));
        }
       // System.out.println("Read " + instances.size() + " instances");
        return instances;
    }
    public static void main(String[] args){
        DataReader train = new DataReader(args[0]);
        Perceptron p = new Perceptron(train.getAllInstances());
        p.doPerceptron(train.getAllInstances());
    }
}