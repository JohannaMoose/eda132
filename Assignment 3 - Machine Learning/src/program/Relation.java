package program;

import java.util.ArrayList;

public class Relation {
    private ArrayList<Attribute> attributes;
    private ArrayList<LearningExample> examples;

    public Relation(ArrayList<Attribute> attributes, ArrayList<LearningExample> examples) {
        this.attributes = attributes;
        this.examples = examples;
    }

    public ArrayList<LearningExample> getExamples() {
        return examples;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

}
