package program;

public class TerminalNode implements DecisionNode {
    private Classification classification;

    public TerminalNode(Classification classification) {
        this.classification = classification;
    }

    @Override
    public String print(int level) {
        return ": " + classification.toString();
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

}
