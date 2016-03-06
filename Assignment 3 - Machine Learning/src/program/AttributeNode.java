package program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class AttributeNode implements DecisionNode {
    private Attribute attribute;
    private HashMap<String, DecisionNode> branches;
    private ArrayList<LearningExample> examples;

    public AttributeNode(Attribute attribute, ArrayList<LearningExample> examples) {
        this.attribute = attribute;
        this.examples = examples;
        branches = new HashMap<>();
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Collection<DecisionNode> getValueOfBranches() {
        return branches.values();
    }

    public HashMap<String, DecisionNode> getBranches() {
        return branches;
    }

    public void addBranch(String value, DecisionNode node) {
        branches.put(value, node);
    }

    public ArrayList<LearningExample> getExamples() {
        return examples;
    }

    @Override
    public String print(int level) {
        StringBuilder printString = new StringBuilder();

        HashSet<String> values = attribute.getValues();
        for (String value : values) {
            for (int i = 0; i < level; i++) {
                printString.append(" ");
            }

            printString.append(attribute.toString()).append(" = ").append(value);
            DecisionNode node = branches.get(value);
            if (node.isTerminal()) {
                printString.append(node.print(level)).append("\n");

            } else {
                printString.append("\n").append(node.print(level + 1));
            }
        }
        return printString.toString();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
