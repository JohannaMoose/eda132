package program;

import java.util.*;
import java.util.Map.Entry;

import umontreal.ssj.probdist.ChiSquareDist;

public class DecisionTreeAlgorithm {
	private Relation relation;

	public DecisionTreeAlgorithm(Relation rel) {
		this.relation = rel;
	}

	public DecisionNode decsionTreeLearning() {
		return decisionTreeLearning(relation.getExamples(), relation.getAttributes(), relation.getExamples());
	}

	private DecisionNode decisionTreeLearning(ArrayList<LearningExample> examples, ArrayList<Attribute> attributes, ArrayList<LearningExample> parentExamples) {
		if (examples.isEmpty())
			return pluralityValue(parentExamples);
		else if (hasSameClassification(examples))
			return new TerminalNode(examples.get(0).getClassification());
		else if (attributes.isEmpty())
			return pluralityValue(examples);
		else
			return createRoot(examples, attributes);
	}

	private DecisionNode pluralityValue(ArrayList<LearningExample> examples) {
		HashMap<Classification, Integer> map = createClassificationMap(examples);
		Classification g = getClassificationForNode(map);
		return new TerminalNode(g);
	}

	private HashMap<Classification, Integer> createClassificationMap(ArrayList<LearningExample> examples) {
		HashMap<Classification, Integer> map = new HashMap<>();

		for (LearningExample ex : examples) {
			int i = 1;
			if (map.containsKey(ex.getClassification())) {
				i = map.get(ex.getClassification());
				i++;
			}
			map.put(ex.getClassification(), i);
		}
		return map;
	}

	private Classification getClassificationForNode(HashMap<Classification, Integer> map) {
		int occurrences = 0;
		Classification g = null;

		for (Entry<Classification, Integer> entry : map.entrySet()) {
			if (entry.getValue() > occurrences) {
				occurrences = entry.getValue();
				g = entry.getKey();
			}
		}

		return g;
	}

	private boolean hasSameClassification(ArrayList<LearningExample> examples) {
		Classification g = examples.get(0).getClassification();
		for (LearningExample ex : examples) {
			if (!g.equals(ex.getClassification()))
				return false;
		}
		return true;
	}

	private DecisionNode createRoot(ArrayList<LearningExample> examples, ArrayList<Attribute> attributes) {
		Attribute attr = mostImportant(attributes, examples);
		AttributeNode root = new AttributeNode(attr, examples);

		for (String value : attr) {
			ArrayList<LearningExample> rootExamples = new ArrayList<>();
			for (LearningExample ex : examples) {
				if (attr.testExample(value, ex)) {
					rootExamples.add(ex);
				}
			}
			ArrayList<Attribute> newAttributes = new ArrayList<>();
			for (Attribute a : attributes) {
				if (!a.equals(attr)) {
					newAttributes.add(a);
				}
			}
			root.addBranch(
					value,
					decisionTreeLearning(rootExamples, newAttributes,
							examples));
		}
		return root;
	}

	private Attribute mostImportant(ArrayList<Attribute> attributes, ArrayList<LearningExample> examples) {
		double max = -1;
		Attribute attribute = null;

		for (Attribute attr : attributes) {
			double temp = calculateGain(attr, examples);
			if (temp > max) {
				attribute = attr;
				max = temp;
			}
		}

		return attribute;
	}

	private double calculateGain(Attribute attr, ArrayList<LearningExample> examples) {
		HashMap<String, Integer> positives = new HashMap<>();
		HashMap<String, Integer> negatives = new HashMap<>();

		int totalPositives = countExamples(attr, examples, positives, negatives);
		double temp = bFunc(totalPositives, examples.size() - totalPositives);
		temp -= remainder(attr, positives, negatives, examples);
		return temp;

	}

	/* Observe, does more tha njust count the exmaples */
	public int countExamples(Attribute attr, ArrayList<LearningExample> examples,
							 HashMap<String, Integer> positives,
							 HashMap<String, Integer> negatives) {
		int totalPositives = 0;

		for (LearningExample ex : examples) {
			int i = 1;
			String value = ex.getValue(attr);
			HashMap<String, Integer> tempMap;

			if (ex.getClassification().getClassification()) {
				tempMap = positives;
				totalPositives++;
			} else {
				tempMap = negatives;
			}

			String key = attr.getKeyIfNumerical(value);

			if (tempMap.containsKey(key)) {
				i = tempMap.get(key);
				i++;
			}

			tempMap.put(key, i);
		}
		return totalPositives;
	}

	private static double bFunc(int p, int n) {
		double q = ((double) (p)) / (p + n);

		if (0 < q && q < 1)
			return -(q * (Math.log(q) / Math.log(2)) + (1 - q) * (Math.log(1 - q) / Math.log(2)));
		else
			return Math.log(1) / Math.log(2);
	}

	private double remainder(Attribute attr,
							 HashMap<String, Integer> positives,
							 HashMap<String, Integer> negatives, ArrayList<LearningExample> examples) {
		Set<String> keys = attr.getValues();
		double sum = 0;

		for (String s : keys) {
			int positive = positives.get(s) == null ? 0 : positives.get(s);
			int negative = negatives.get(s) == null ? 0 : negatives.get(s);

			sum += (((double) (positive + negative)) / examples.size())
					* bFunc(positive, negative);
		}

		return sum;
	}

	private boolean hasOnlyTerminalLeaves(Collection<DecisionNode> branches) {
		for (DecisionNode node : branches) {
			if (!node.isTerminal())
				return false;
		}

		return true;
	}

	public DecisionNode pruning(DecisionNode tree) {
		if (tree.isTerminal())
			return tree;
		else
			return prune((AttributeNode) tree);
	}

	private DecisionNode prune(AttributeNode hypothesis) {
		if (hasOnlyTerminalLeaves(hypothesis.getValueOfBranches()))
			return pruneTerminalBranch(hypothesis);
        else
			return pruneAttributeBranch(hypothesis);

	}

	private DecisionNode pruneTerminalBranch(AttributeNode hypothesis) {
		double deviation = deviation(hypothesis);
		int size = hypothesis.getBranches().size();

		if(size > 1)
            size--;

		double probability = ChiSquareDist.cdf(size, 4, deviation); // From Library

		if (probability >= 0.95)
            return pluralityValue(hypothesis.getExamples());
        else
            return hypothesis;
	}

	private double deviation(AttributeNode node) {
		Attribute attr = node.getAttribute();
		ArrayList<LearningExample> examples = node.getExamples();
		HashMap<String, Integer> positives = new HashMap<>();
		HashMap<String, Integer> negatives = new HashMap<>();

		int totalPositives = countExamples(attr, examples, positives, negatives);
		return calcDeviation(attr, positives, negatives, totalPositives, examples.size());
	}

	private double calcDeviation(Attribute attr,
								 HashMap<String, Integer> positives,
								 HashMap<String, Integer> negatives, int totalPositives, int examplesSize) {
		Set<String> keys = attr.getValues();
		double deviationSum = 0;

		for (String s : keys) {
			deviationSum += updateDeviationSumWith(positives, negatives, totalPositives, examplesSize, deviationSum, s);
		}

		return deviationSum;
	}

	private double updateDeviationSumWith(HashMap<String, Integer> positives, HashMap<String, Integer> negatives, int totalPositives, int examplesSize, double deviationSum, String s) {
		int positive = positives.get(s) == null ? 0 : positives.get(s);
		int negative = negatives.get(s) == null ? 0 : negatives.get(s);

		double estimateP = estimatedValue(positive, negative, totalPositives, examplesSize);
		double estimateN = estimatedValue(positive, negative, examplesSize - totalPositives, examplesSize);

		if(estimateP > 0 || estimateN >0)
            return Math.pow(positive - estimateP, 2) / estimateP + Math.pow(negative - estimateN, 2) / estimateN;

		return 0;
	}

	private double estimatedValue(int p, int n, int totPart, int examplesSize) {
		return totPart * ((double) (p + n)) / examplesSize;
	}

	private DecisionNode pruneAttributeBranch(AttributeNode hypothesis) {
		AttributeNode newTree = new AttributeNode(hypothesis.getAttribute(), hypothesis.getExamples());
		HashMap<String, DecisionNode> branches = hypothesis.getBranches();
		Set<String> keys = branches.keySet();

		for (String key : keys)
			newTree.addBranch(key, pruning(branches.get(key)));

		if (hasOnlyTerminalLeaves(newTree.getValueOfBranches()))
			return pruning(newTree);
		else
			return newTree;
	}
}
