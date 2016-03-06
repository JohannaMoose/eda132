package program;

public class Classification {
    private Attribute attribute;
    private String value;
    private boolean classification;

    public Classification(Attribute attribute, String value, boolean classification) {
        this.attribute = attribute;
        this.value = value;
        this.classification = classification;
    }

    public String toString() {
        return value;
    }

    public boolean getClassification() {
        return classification;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attribute == null) ? 0 : attribute.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Classification other = (Classification) obj;
        return other != null &&
                other.attribute.equals(attribute) &&
                other.value.equals(value);
    }

}
