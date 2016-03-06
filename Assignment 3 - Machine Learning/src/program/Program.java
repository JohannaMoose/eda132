package program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {

        handle183();
        handleWeatherData();
        handleDiabetesData();
    }

    private static void handle183() {
        DecisionTreeParser dtp = null;
        try {
            dtp = new DecisionTreeParser(new File("data/arff18-3.txt"), "yes");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the arff18-3.txt file from the data folder");
            System.exit(1);
        }
        DecisionTreeAlgorithm dta = new DecisionTreeAlgorithm(dtp.getRelation());

        System.out.println(dta.pruning(dta.decsionTreeLearning()).print(0));
    }

    private static void handleWeatherData() {
        DecisionTreeParser dtp = null;
        DecisionTreeAlgorithm dta;
        try {
            dtp = new DecisionTreeParser(new File("data/weather.nominal.arff"), "yes");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the weather.nominal.arff file from the data folder");
            System.exit(1);
        }
        dta = new DecisionTreeAlgorithm(dtp.getRelation());
        System.out.println(dta.pruning(dta.decsionTreeLearning()).print(0));
    }

    private static void handleDiabetesData() {
        DecisionTreeParser dtp = null;
        DecisionTreeAlgorithm dta;
        try {
            dtp = new DecisionTreeParser(new File("data/diabetes.arff"), "tested_positive");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the diabetes.arff file from the data folder");
            System.exit(1);
        }

        Relation rel = dtp.getRelation();
        ArrayList<Double> splitPoints = new ArrayList<>();
        splitPoints.add(3.8);
        splitPoints.add(120.9);
        splitPoints.add(69.1);
        splitPoints.add(20.5);
        splitPoints.add(79.8);
        splitPoints.add(32.0);
        splitPoints.add(0.5);
        splitPoints.add(33.2);
        for (int i = 0; i < rel.getAttributes().size(); i++) {
            rel.getAttributes().get(i).setSplitPoint(splitPoints.get(i));
        }
        dta = new DecisionTreeAlgorithm(rel);
        System.out.println(dta.pruning(dta.decsionTreeLearning()).print(0));
    }

}
