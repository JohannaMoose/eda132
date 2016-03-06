package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class DecisionTreeParser {
    private Relation rel;

    public DecisionTreeParser(File file, String positiveClass) throws FileNotFoundException {
        LinkedList<String> lines = readInLinesInFile(file);

        ArrayList<Attribute> attributes = new ArrayList<>();
        ArrayList<LearningExample> examples = new ArrayList<>();

        while (!lines.isEmpty()) {
            String line = lines.removeFirst();
            String[] elements = line.split(" ");

            if (lineIsRelation(elements)) {
                attributes = new ArrayList<>();
                examples = new ArrayList<>();
            } else if (lineIsAttribute(elements))
                addNewAttribute(attributes, line, elements);
            else if (lineIsData(elements))
                createExamples(positiveClass, lines, attributes, examples);
        }

        attributes.remove(attributes.size() - 1);
        rel = new Relation(attributes, examples);
    }

    private boolean lineIsRelation(String[] elements){
        return lineIsType(elements, "@relation", 0);
    }

    private boolean lineIsType(String[] element, String type, int index) {
        return element[index].equals(type);
    }

    private boolean lineIsAttribute(String[] element) {
        return lineIsType(element, "@attribute", 0);
    }

    /* Handle Attribute */
    private void addNewAttribute(ArrayList<Attribute> attributes, String line, String[] elements) {
        if (lineIsType(elements, "real", 2) || lineIsType(elements, "numeric", 2) || lineIsType(elements, "integer", 2))
            addNumericAttribute(attributes, elements[1]);
        else if (elements[2].startsWith("{"))
            addStringAttribute(attributes, line, elements[1]);
        else
            handleInvalidLine(elements);
    }

    private void addNumericAttribute(ArrayList<Attribute> attributes, String element) {
        HashSet<String> values = new HashSet<>();
        values.add("%numeric");
        attributes.add(new Attribute(element, values));
    }

    private void addStringAttribute(ArrayList<Attribute> attributes, String line, String element) {
        HashSet<String> values = new HashSet<>();
        String[] stringValues = line.substring(
                line.indexOf('{') + 1, line.indexOf('}'))
                .split(",");
        for (String value : stringValues) {
            values.add(value.trim());
        }
        attributes.add(new Attribute(element, values));
    }

    private void handleInvalidLine(String[] elements) {
        if (lineIsType(elements, "string", 2))
            handleInvalidValue("String");
        else if (lineIsType(elements, "date", 2))
            handleInvalidValue("Date");
        else
            handleInvalidValue("Type");
    }

    /* End handle Attribute */

    private boolean lineIsData(String[] element) {
        return lineIsType(element, "@data", 0);
    }

    /* Handle Data */
    private void createExamples(String positiveClass, LinkedList<String> lines, ArrayList<Attribute> attributes, ArrayList<LearningExample> examples) {
        while (!lines.isEmpty() && nextLineInstRelation(lines) && nextLineIsntData(lines)) {
            createExample(positiveClass, lines, attributes, examples);
        }
    }

    private void createExample(String positiveClass, LinkedList<String> lines, ArrayList<Attribute> attributes, ArrayList<LearningExample> examples) {
        String[] example = lines.removeFirst().split(",");
        HashMap<Attribute, String> ex = createExampleValues(attributes, example);
        Classification classification = createClassification(positiveClass, attributes, example);
        examples.add(new LearningExample(ex, classification));
    }

    private HashMap<Attribute, String> createExampleValues(ArrayList<Attribute> attributes, String[] example) {
        HashMap<Attribute, String> ex = new HashMap<>();

        for (int i = 0; i < example.length - 1; i++) {
            Attribute attribute = attributes.get(i);
            String value = example[i];
            if (attribute.valueIsValid(value))
                ex.put(attribute, value);
            else
                handleInvalidValue("Value");
        }
        return ex;
    }

    private void handleInvalidValue(String error) {
        System.err.println(error + " not supported!");
        System.exit(1);
    }

    private Classification createClassification(String positiveClass, ArrayList<Attribute> attributes, String[] example) {
        Attribute attribute = attributes.get(example.length - 1);
        String value = example[example.length - 1];
        if (!attribute.valueIsValid(value)) {
            handleInvalidValue("Value");
        }
        return new Classification(attribute, value, lineIsType(example, positiveClass, example.length - 1));
    }

    /* End handle data */

    private boolean nextLineIsntData(LinkedList<String> lines) {
        return !lines.peekFirst().startsWith("@data");
    }

    private boolean nextLineInstRelation(LinkedList<String> lines) {
        return !lines.peekFirst().startsWith("@relation");
    }

    /* Move to top */
    private LinkedList<String> readInLinesInFile(File file) throws FileNotFoundException {
        Scanner scan = getScanner(file);
        LinkedList<String> lines = new LinkedList<>();

        while (scan.hasNextLine()) {
            addNextLine(scan.nextLine(), lines);
        }

        return lines;
    }

    private void addNextLine(String line, LinkedList<String> lines) {
        if (!line.startsWith("%") && !line.isEmpty())
            lines.addLast(line.toLowerCase());

    }

    private Scanner getScanner(File file) throws FileNotFoundException {
        try {
            return new Scanner(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Relation getRelation() {
        return rel;
    }
}
