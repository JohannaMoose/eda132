package program;

import java.util.HashMap;

public class LearningExample {
    private HashMap<Attribute, String> values;
    private Classification classification;

    public LearningExample(HashMap<Attribute, String> values, Classification classification) {
        this.values = values;
        this.classification = classification;
    }

    public Classification getClassification() {
        return classification;
    }

    public String getValue(Attribute attr) {
        return values.get(attr);
    }

}
