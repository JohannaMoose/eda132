package program;

import java.util.HashSet;
import java.util.Iterator;

public class Attribute implements Iterable<String> {
    private String name;
    private HashSet<String> values;
    boolean numeric = false;
    private double splitPoint;

    private static final String GreaterThan = ">";
    private static final String LessOrEqualThan = "<=";

    public Attribute(String name, HashSet<String> values) {
        this.name = name;
        this.values = values;
    }

    public boolean valueIsValid(String value) {
        if (values.contains("%numeric"))
            return helpers.Math.isDouble(value);
        else
            return values.contains(value);
    }

    public HashSet<String> getValues() {
        return values;
    }

    public boolean testExample(String attributeValue, LearningExample example) {
        if (numeric)
            return belongsToCorrectValueBranch(attributeValue, example.getValue(this));
        else
            return attributeValue.equals(example.getValue(this));
    }

    private boolean belongsToCorrectValueBranch(String attributeValue, String ex) {
        double d = Double.parseDouble(ex);

        return attributeValue.equals(GreaterThan + splitPoint) &&
                d > splitPoint ||
                attributeValue.equals(LessOrEqualThan + splitPoint) &&
                d <= splitPoint;
    }

    public void setSplitPoint(double splitPoint) {
        resetValues(splitPoint);
        this.splitPoint = splitPoint;
        numeric = true;
    }

    private void resetValues(double splitPoint) {
        values.clear();
        values.add(GreaterThan + splitPoint);
        values.add(LessOrEqualThan + splitPoint);
    }

    public String getKeyIfNumerical(String value) {
        if (numeric)
            return getNumerical(value);
        else
            return value;
    }

    private String getNumerical(String value) {
        if (Double.parseDouble(value) > splitPoint)
            return GreaterThan + splitPoint;
        else
            return LessOrEqualThan + splitPoint;
    }

    @Override
    public int hashCode() {
        final int prime = 13;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attribute other = (Attribute) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

}
